package cn.gpnusz.ucloudteach.service.user;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.gpnusz.ucloudteach.common.CommonResp;
import cn.gpnusz.ucloudteach.entity.StudentInfo;
import cn.gpnusz.ucloudteach.entity.StudentInfoExample;
import cn.gpnusz.ucloudteach.exception.BusinessException;
import cn.gpnusz.ucloudteach.exception.BusinessExceptionCode;
import cn.gpnusz.ucloudteach.mapper.StudentInfoMapper;
import cn.gpnusz.ucloudteach.req.UserLoginReq;
import cn.gpnusz.ucloudteach.req.UserRegisterReq;
import cn.gpnusz.ucloudteach.resp.UserLoginResp;
import cn.gpnusz.ucloudteach.util.CheckCodeUtils;
import cn.gpnusz.ucloudteach.util.RandomKeyUtil;
import cn.gpnusz.ucloudteach.util.SendMsgUtil;
import cn.gpnusz.ucloudteach.util.SnowFlake;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author h0ss
 * @description 用户登录业务层
 * @date 2021/11/26 1:52
 */
@Service
public class UserLoginService {
    @Resource
    private StudentInfoMapper studentInfoMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RandomKeyUtil randomKeyUtil;

    @Resource
    private SendMsgUtil sendMsgUtil;

    @Resource
    private SnowFlake snowFlake;

    @Resource
    private CheckCodeUtils checkCodeUtils;

    private static final Logger LOG = LoggerFactory.getLogger(UserLoginService.class);


    /**
     * 用户登录的业务方法
     *
     * @param userLoginReq : 用户登录信息
     * @return : cn.gpnusz.ucloudteach.resp.UserLoginResp
     * @author h0ss
     */
    public UserLoginResp userLogin(UserLoginReq userLoginReq) {
        Boolean codeCheck = checkCodeUtils.checkRes(userLoginReq.getTicket(), userLoginReq.getRandStr());
        if (codeCheck && !ObjectUtils.isEmpty(userLoginReq)) {
            // 获取手机号 去数据库中查找相应记录
            String phone = userLoginReq.getPhone();
            StudentInfoExample studentInfoExample = new StudentInfoExample();
            StudentInfoExample.Criteria criteria = studentInfoExample.createCriteria();
            criteria.andPhoneEqualTo(phone);
            List<StudentInfo> res = studentInfoMapper.selectByExample(studentInfoExample);
            if (!ObjectUtils.isEmpty(res)) {
                // 判断用户是否处于被封禁状态
                if (res.get(0).getDisableFlag()) {
                    throw new BusinessException(BusinessExceptionCode.BE_BAN_FAIL);
                }
                // 获取记录之后将用户传入的密码+盐之后md5与数据库的数据作对比
                String salt = res.get(0).getSalt();
                String passwdSalt = userLoginReq.getPassword() + salt;
                String inputPassword = DigestUtils.md5DigestAsHex(passwdSalt.getBytes(StandardCharsets.UTF_8));
                // 对比正确之后获取登录后的token返回给前端
                if (res.get(0).getPassword().equals(inputPassword)) {
                    StpUtil.login(res.get(0).getId());
                    SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
                    System.out.println(tokenInfo);
                    UserLoginResp resp = new UserLoginResp();
                    BeanUtils.copyProperties(res.get(0), resp);
                    resp.setToken(tokenInfo.getTokenValue());
                    return resp;
                }
            }
        }
        // 返回帐号/密码错误的业务异常
        throw new BusinessException(BusinessExceptionCode.LOGIN_FAIL);
    }

    /**
     * 检查登录状态的业务方法 如果未登录则抛NotLoginException异常
     *
     * @author h0ss
     */
    public void checkLogin() {
        StpUtil.checkLogin();
    }

    /**
     * 用户退出登陆的业务方法
     *
     * @author h0ss
     */
    public void logout() {
        StpUtil.logout();
    }


    /**
     * 注册用户的业务方法
     *
     * @param userRegisterReq : 用户注册信息实体类
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<java.lang.Object>
     * @author h0ss
     */
    public CommonResp<Object> userRegister(UserRegisterReq userRegisterReq) {
        // 检查手机号是否已存在
        if (checkUser(userRegisterReq.getPhone())) {
            throw new BusinessException(BusinessExceptionCode.PHONE_NUMBER_EXIST);
        }

        // 返回的对象
        CommonResp<Object> resp = new CommonResp<>();
        resp.setSuccess(false);

        // 传入信息对象不为空
        if (!ObjectUtils.isEmpty(userRegisterReq)) {
            Boolean codeCheck = checkCodeUtils.checkRes(userRegisterReq.getTicket(), userRegisterReq.getRandStr());
            // 根据phone从redis中取出验证码数据
            String res = stringRedisTemplate.opsForValue().get(userRegisterReq.getPhone());
            // 如果取出数据为空则说明验证码已失效或未发送验证码
            if (codeCheck && res != null) {
                JSONObject jsonObject = JSON.parseObject(res);
                // 取出验证码数据
                String code = jsonObject.getString("code");
                // 对比验证码正确性
                if (userRegisterReq.getAuthCode().equals(code)) {
                    // 设置盐值长度
                    int saltLen = 6;
                    // 验证通过执行注册功能
                    StudentInfo studentInfo = new StudentInfo();
                    BeanUtils.copyProperties(userRegisterReq, studentInfo);
                    // 获取随机盐值拼接密码进行加密
                    String salt = this.randomKeyUtil.getRandomSalt(saltLen);
                    studentInfo.setSalt(salt);
                    String password = userRegisterReq.getPassword() + salt;
                    studentInfo.setPassword(DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)));
                    // 默认项设置
                    studentInfo.setId(snowFlake.nextId());
                    studentInfo.setName("新用户" + randomKeyUtil.getRandomCode(8));
                    studentInfo.setGender(Boolean.TRUE);
                    studentInfo.setHeadPic("https://ucloudteach-user.obs.cn-north-4.myhuaweicloud.com/initialHeadPic.jpg");
                    // 写入数据库
                    studentInfoMapper.insertSelective(studentInfo);
                    resp.setSuccess(true);
                    resp.setMessage("注册成功");
                } else {
                    resp.setMessage("验证码有误");
                }
            } else {
                resp.setMessage("验证码无效，请重新发送");
            }
            if (!codeCheck) {
                resp.setMessage("滑块校验不通过");
            }
            return resp;
        }
        resp.setMessage("注册失败，信息不完整");
        return resp;
    }

    /**
     * 用户找回密码的业务方法
     *
     * @param userRegisterReq : 请求信息
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<java.lang.Object>
     * @author h0ss
     */
    public CommonResp<Object> resetPasswd(UserRegisterReq userRegisterReq) {
        // 返回的对象
        CommonResp<Object> resp = new CommonResp<>();
        resp.setSuccess(false);

        // 检查手机号是否已存在
        if (!checkUser(userRegisterReq.getPhone())) {
            resp.setMessage("该手机号暂未注册");
            return resp;
        }

        // 传入信息对象不为空
        if (!ObjectUtils.isEmpty(userRegisterReq)) {
            Boolean codeCheck = checkCodeUtils.checkRes(userRegisterReq.getTicket(), userRegisterReq.getRandStr());
            // 根据phone从redis中取出验证码数据
            String res = stringRedisTemplate.opsForValue().get(userRegisterReq.getPhone());
            // 如果取出数据为空则说明验证码已失效或未发送验证码
            if (codeCheck && res != null) {
                // 取出验证码数据
                String code = JSON.parseObject(res).getString("code");
                // 对比验证码正确性
                if (!ObjectUtils.isEmpty(userRegisterReq.getAuthCode()) && userRegisterReq.getAuthCode().equals(code)) {
                    // 设置盐值长度
                    int saltLen = 6;
                    // 验证通过则开始找回密码操作
                    StudentInfo studentInfo = new StudentInfo();
                    // 获取随机盐值拼接密码进行加密
                    String salt = randomKeyUtil.getRandomSalt(saltLen);
                    studentInfo.setSalt(salt);
                    String password = userRegisterReq.getPassword() + salt;
                    studentInfo.setPassword(DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)));
                    // 定位到对应记录
                    StudentInfoExample example = new StudentInfoExample();
                    StudentInfoExample.Criteria criteria = example.createCriteria();
                    criteria.andPhoneEqualTo(userRegisterReq.getPhone());
                    // 更新写入数据库
                    studentInfoMapper.updateByExampleSelective(studentInfo, example);
                    resp.setSuccess(true);
                    resp.setMessage("密码重置成功");
                } else {
                    resp.setMessage("验证码有误");
                }
            } else {
                resp.setMessage("验证码无效，请重新发送");
            }
            if (!codeCheck) {
                resp.setMessage("滑块校验不通过");
            }
            return resp;
        }
        resp.setMessage("找回密码失败");
        return resp;
    }

    /**
     * 请求获取验证码的业务方法
     *
     * @param phone : 手机号码
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<java.lang.Object>
     * @author h0ss
     */
    public CommonResp<Object> requestSend(String phone) {
        // 手机号合法性校验
        final String regex = "^[1][3-9][0-9]{9}$";
        CommonResp<Object> resp = new CommonResp<>();
        if (phone == null || !phone.matches(regex)) {
            resp.setSuccess(false);
            resp.setMessage("手机号码不合法");
            return resp;
        }

        // 设置两次发送间隔为60s
        final long minute = 60;
        // 从redis中根据phone去取验证码数据
        String res = stringRedisTemplate.opsForValue().get(phone);
        // 如果距离上次发送间隔未达60s则不发送短信
        if (res != null) {
            JSONObject jsonObject = JSON.parseObject(res);
            // 上次请求时间
            Long lastTime = jsonObject.getLong("time");
            // 当前时间
            Long curTime = System.currentTimeMillis() / 1000;
            if (curTime - lastTime < minute) {
                resp.setSuccess(false);
                resp.setMessage("短信下发失败");
                return resp;
            }
        }

        // 调用工具类下发短信
        JSONObject msg = null;
        try {
            // 获取随机验证码
            String code = randomKeyUtil.getRandomCode(6);
            // 调用发送短信工具类向手机发送验证码 msg为结果信息
            msg = sendMsgUtil.sendMsg(phone, code);
            // errno为0代表成功 其他为失败
            int errno = (int) msg.get("errno");
            if (errno == 0) {
                // 成功下发后将验证码信息写入redis 有效期设置为300s
                JSONObject json = new JSONObject();
                json.put("code", code);
                json.put("time", System.currentTimeMillis() / 1000);
                stringRedisTemplate.opsForValue().set(phone, JSON.toJSONString(json), 300, TimeUnit.SECONDS);
                resp.setSuccess(true);
                resp.setMessage("短信下发成功，请注意查收");
            } else {
                resp.setSuccess(false);
                resp.setMessage("短信下发失败");
                LOG.info("{} 短信下发失败：{}", phone, msg.get("errmsg"));
            }
        } catch (Exception e) {
            throw new BusinessException(BusinessExceptionCode.SEND_CODE_FAIL);
        }
        return resp;
    }

    /**
     * 检查注册手机号是否已存在
     *
     * @param phone :  手机号
     * @return : java.lang.Boolean
     * @author h0ss
     */
    public Boolean checkUser(String phone) {
        StudentInfoExample studentInfoExample = new StudentInfoExample();
        StudentInfoExample.Criteria criteria = studentInfoExample.createCriteria();
        criteria.andPhoneEqualTo(phone);
        if (!studentInfoMapper.selectByExample(studentInfoExample).isEmpty()) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }
}

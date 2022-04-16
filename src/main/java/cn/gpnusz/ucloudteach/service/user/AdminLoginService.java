package cn.gpnusz.ucloudteach.service.user;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.gpnusz.ucloudteach.entity.Admin;
import cn.gpnusz.ucloudteach.entity.AdminExample;
import cn.gpnusz.ucloudteach.exception.BusinessException;
import cn.gpnusz.ucloudteach.exception.BusinessExceptionCode;
import cn.gpnusz.ucloudteach.mapper.AdminMapper;
import cn.gpnusz.ucloudteach.req.AdminLoginReq;
import cn.gpnusz.ucloudteach.resp.AdminLoginResp;
import cn.gpnusz.ucloudteach.util.CheckCodeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author h0ss
 * @description 管理员登录的业务层
 * @date 2021/11/23 - 3:15
 */
@Service
public class AdminLoginService {
    @Resource
    private AdminMapper adminMapper;

    @Resource
    private CheckCodeUtils checkCodeUtils;

    /**
     * 管理员登录业务方法
     * @param adminLoginReq : 登录信息
     * @return : cn.gpnusz.ucloudteach.resp.AdminLoginResp
     * @author h0ss
     */
    public AdminLoginResp adminLogin(AdminLoginReq adminLoginReq) {
        // 验证码后端校验
        Boolean codeCheck = checkCodeUtils.checkRes(adminLoginReq.getTicket(), adminLoginReq.getRandStr());
        if (codeCheck && !ObjectUtils.isEmpty(adminLoginReq)) {
            Admin admin = new Admin();
            BeanUtils.copyProperties(adminLoginReq, admin);
            AdminExample adminExample = new AdminExample();
            AdminExample.Criteria criteria = adminExample.createCriteria();
            criteria.andUsernameEqualTo(admin.getUsername());
            List<Admin> res = adminMapper.selectByExample(adminExample);
            if (!ObjectUtils.isEmpty(res)) {
                String salt = res.get(0).getSalt();
                String passwdSalt = admin.getPassword() + salt;
                String inputPassword = DigestUtils.md5DigestAsHex(passwdSalt.getBytes(StandardCharsets.UTF_8));
                if (res.get(0).getPassword().equals(inputPassword)) {
                    StpUtil.login(res.get(0).getId());
                    SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
                    System.out.println(tokenInfo);
                    AdminLoginResp resp = new AdminLoginResp();
                    resp.setToken(tokenInfo.getTokenValue());
                    resp.setUsername(res.get(0).getUsername());
                    resp.setSuperFlag(res.get(0).getSuperFlag());
                    return resp;
                }
            }
        }
        throw new BusinessException(BusinessExceptionCode.LOGIN_FAIL);
    }


    /**
     * 管理员检查登录状态的业务方法 如果没有登录会直接抛出NotLoginException异常
     * @author h0ss
     */
    public void checkLogin() {
        StpUtil.checkLogin();
    }

    /**
     * 管理员退出登陆的业务方法
     * @author h0ss
     */
    public void logout(){
        StpUtil.logout();
    }

}

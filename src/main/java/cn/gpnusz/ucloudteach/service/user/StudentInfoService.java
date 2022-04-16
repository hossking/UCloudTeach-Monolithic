package cn.gpnusz.ucloudteach.service.user;


import cn.gpnusz.ucloudteach.common.PageResp;
import cn.gpnusz.ucloudteach.entity.StudentInfo;
import cn.gpnusz.ucloudteach.entity.StudentInfoExample;
import cn.gpnusz.ucloudteach.exception.BusinessException;
import cn.gpnusz.ucloudteach.exception.BusinessExceptionCode;
import cn.gpnusz.ucloudteach.mapper.StudentInfoMapper;
import cn.gpnusz.ucloudteach.req.StuResetPasswordReq;
import cn.gpnusz.ucloudteach.req.StudentInfoQueryReq;
import cn.gpnusz.ucloudteach.req.StudentInfoSaveReq;
import cn.gpnusz.ucloudteach.util.RandomKeyUtil;
import cn.gpnusz.ucloudteach.util.SnowFlake;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author h0ss
 * @description 学员信息系统业务层
 * @date 2021/11/12 - 20:57
 */
@Service
public class StudentInfoService {
    @Resource
    private StudentInfoMapper studentInfoMapper;

    @Resource
    private RandomKeyUtil randomKeyUtil;

    @Resource
    private SnowFlake snowFlake;

    private static final Logger LOG = LoggerFactory.getLogger(StudentInfoService.class);

    /**
     * 按传入条件查询学员信息的业务方法
     *
     * @param studentInfoQueryReq : 查询条件参数
     * @return : cn.gpnusz.ucloudteach.common.PageResp<cn.gpnusz.ucloudteach.resp.StudentInfo>
     * @author h0ss
     */
    public PageResp<StudentInfo> getAllByCondition(StudentInfoQueryReq studentInfoQueryReq) {
        StudentInfoExample studentInfoExample = new StudentInfoExample();
        StudentInfoExample.Criteria criteria = studentInfoExample.createCriteria();
        if (!ObjectUtils.isEmpty(studentInfoQueryReq.getName())) {
            criteria.andNameLike("%" + studentInfoQueryReq.getName() + "%");
        }
        if (!ObjectUtils.isEmpty(studentInfoQueryReq.getDisableFlag())) {
            criteria.andDisableFlagEqualTo(studentInfoQueryReq.getDisableFlag());
        }
        if (!ObjectUtils.isEmpty(studentInfoQueryReq.getGender())) {
            criteria.andGenderEqualTo(studentInfoQueryReq.getGender());
        }
        if (studentInfoQueryReq.getPage() != null && studentInfoQueryReq.getSize() != null) {
            PageHelper.startPage(studentInfoQueryReq.getPage(), studentInfoQueryReq.getSize());
        } else {
            PageHelper.startPage(1, 10);
        }
        List<StudentInfo> studentInfoList = studentInfoMapper.selectByExample(studentInfoExample);
        return getStudentInfoResp(studentInfoList);
    }

    /**
     * 查询全部学员信息数据的业务方法
     *
     * @param studentInfoQueryReq : 查询（分页）参数
     * @return : cn.gpnusz.ucloudteach.common.PageResp<cn.gpnusz.ucloudteach.resp.StudentInfo>
     * @author h0ss
     */
    public PageResp<StudentInfo> getAll(StudentInfoQueryReq studentInfoQueryReq) {
        // 获取全部学员信息每次最多显示100条
        if (studentInfoQueryReq.getPage() != null && studentInfoQueryReq.getSize() != null) {
            PageHelper.startPage(studentInfoQueryReq.getPage(), studentInfoQueryReq.getSize());
        } else {
            PageHelper.startPage(1, 100);
        }
        List<StudentInfo> studentInfoList = studentInfoMapper.selectByExample(null);
        return getStudentInfoResp(studentInfoList);
    }

    /**
     * 新增或编辑学员信息的业务方法
     *
     * @param studentInfoSaveReq : 保存的学员信息数据
     * @author h0ss
     */
    public void save(StudentInfoSaveReq studentInfoSaveReq) {
        // 获取随机盐值
        String salt = randomKeyUtil.getRandomSalt(6);
        // 密码+盐 MD5加密处理
        String passwd = studentInfoSaveReq.getPassword() + salt;
        studentInfoSaveReq.setPassword(DigestUtils.md5DigestAsHex(passwd.getBytes(StandardCharsets.UTF_8)));
        // 创建一个新对象
        StudentInfo studentInfo = new StudentInfo();
        BeanUtils.copyProperties(studentInfoSaveReq, studentInfo);
        studentInfo.setSalt(salt);
        // 判断是新增还是编辑
        if (studentInfo.getId() != null) {
            // 编辑时字段置为null可避免对特殊字段的修改
            studentInfo.setPassword(null);
            studentInfo.setPhone(null);
            studentInfo.setSalt(null);
            StudentInfoExample studentInfoExample = new StudentInfoExample();
            StudentInfoExample.Criteria criteria = studentInfoExample.createCriteria();
            criteria.andIdEqualTo(studentInfo.getId());
            studentInfoMapper.updateByExampleSelective(studentInfo, studentInfoExample);
        } else {
            // 新增时先判断手机号是否存在 不存在全量写入即可
            StudentInfo studentInfoByDb = selectByPhone(studentInfoSaveReq.getPhone());
            if (ObjectUtils.isEmpty(studentInfoByDb)) {
                // 雪花算法生成id
                studentInfo.setId(snowFlake.nextId());
                // 初始化状态
                studentInfo.setDisableFlag(Boolean.FALSE);
                studentInfoMapper.insert(studentInfo);
            } else {
                // 手机号已存在 抛出自定义的业务异常
                throw new BusinessException(BusinessExceptionCode.PHONE_NUMBER_EXIST);
            }
        }
    }


    /**
     * 重置学员密码的操作
     *
     * @param stuResetPasswordReq : 重置密码对象
     * @author h0ss
     */
    public void resetPassword(StuResetPasswordReq stuResetPasswordReq) {
        StudentInfoExample studentInfoExample = new StudentInfoExample();
        StudentInfoExample.Criteria criteria = studentInfoExample.createCriteria();
        // 根据id匹配
        criteria.andIdEqualTo(stuResetPasswordReq.getId());
        // 新建学员信息对象
        StudentInfo studentInfo = new StudentInfo();
        // 从数据库中查出该id对应的用户信息
        List<StudentInfo> stuInfoByDb = studentInfoMapper.selectByExample(studentInfoExample);
        if (!stuInfoByDb.isEmpty()) {
            BeanUtils.copyProperties(stuInfoByDb.get(0), studentInfo);
        } else {
            throw new BusinessException(BusinessExceptionCode.STUDENT_NOT_EXIST);
        }
        // 获取随机盐值
        String salt = randomKeyUtil.getRandomSalt(6);
        // 密码MD5+盐加密处理
        String passwd = stuResetPasswordReq.getPassword() + salt;
        stuResetPasswordReq.setPassword(DigestUtils.md5DigestAsHex(passwd.getBytes(StandardCharsets.UTF_8)));
        // 只更改密码与盐值
        studentInfo.setPassword(stuResetPasswordReq.getPassword());
        studentInfo.setSalt(salt);
        studentInfoMapper.updateByExampleSelective(studentInfo, studentInfoExample);
    }

    /**
     * 封禁/解封学员的业务方法
     *
     * @param id   : 学员id
     * @param flag : 封禁为1 解封为0
     * @author h0ss
     */
    public void banStudent(Long id, Boolean flag) {
        StudentInfoExample studentInfoExample = new StudentInfoExample();
        StudentInfoExample.Criteria criteria = studentInfoExample.createCriteria();
        criteria.andIdEqualTo(id);
        List<StudentInfo> infoList = studentInfoMapper.selectByExample(studentInfoExample);
        if (!infoList.isEmpty()) {
            StudentInfo studentInfo = infoList.get(0);
            studentInfo.setDisableFlag(flag);
            studentInfoMapper.updateByExampleSelective(studentInfo, studentInfoExample);
        } else {
            throw new BusinessException(BusinessExceptionCode.STUDENT_NOT_EXIST);
        }
    }

    /**
     * 删除学员信息的业务方法
     *
     * @param id : 要删除的学员信息id
     * @author h0ss
     */
    public void delete(Long id) {
        StudentInfoExample studentInfoExample = new StudentInfoExample();
        StudentInfoExample.Criteria criteria = studentInfoExample.createCriteria();
        criteria.andIdEqualTo(id);
        studentInfoMapper.deleteByExample(studentInfoExample);
    }

    /**
     * 按传入的手机号查找对应的学员信息
     *
     * @param phone : 手机号
     * @return : cn.gpnusz.ucloudteach.entity.StudentInfo
     * @author h0ss
     */
    public StudentInfo selectByPhone(String phone) {
        StudentInfoExample studentInfoExample = new StudentInfoExample();
        StudentInfoExample.Criteria criteria = studentInfoExample.createCriteria();
        criteria.andPhoneEqualTo(phone);
        List<StudentInfo> studentInfoList = studentInfoMapper.selectByExample(studentInfoExample);
        if (!studentInfoList.isEmpty()) {
            return studentInfoList.get(0);
        } else {
            return null;
        }
    }

    /**
     * 将学员信息查询结果封装成PageResp对象的业务方法
     *
     * @param studentInfoList : 学员信息查询结果
     * @return : cn.gpnusz.ucloudteach.common.PageResp<cn.gpnusz.ucloudteach.resp.StudentInfo>
     * @author h0ss
     */
    private PageResp<StudentInfo> getStudentInfoResp(List<StudentInfo> studentInfoList) {
        List<StudentInfo> respList = new ArrayList<>(studentInfoList.size());
        // 将结果从studentInfoList 深拷贝到 StudentInfo
        for (StudentInfo studentInfo : studentInfoList) {
            StudentInfo StudentInfo = new StudentInfo();
            BeanUtils.copyProperties(studentInfo, StudentInfo);
            respList.add(StudentInfo);
        }
        // 创建分页信息对象
        PageInfo<StudentInfo> pageInfo = new PageInfo<>(studentInfoList);
        LOG.info("总行数:{}", pageInfo.getTotal());
        LOG.info("总页数:{}", pageInfo.getPages());

        // 返回分页结果对象
        PageResp<StudentInfo> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);
        return pageResp;
    }
}

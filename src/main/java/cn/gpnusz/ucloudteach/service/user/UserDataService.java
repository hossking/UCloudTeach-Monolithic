package cn.gpnusz.ucloudteach.service.user;

import cn.dev33.satoken.stp.StpUtil;
import cn.gpnusz.ucloudteach.entity.StudentInfo;
import cn.gpnusz.ucloudteach.entity.StudentInfoExample;
import cn.gpnusz.ucloudteach.mapper.StudentInfoMapper;
import cn.gpnusz.ucloudteach.req.UserDataSaveReq;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author h0ss
 * @description 用户修改个人信息的业务层
 * @date 2021/11/27 14:24
 */
@Service
public class UserDataService {
    @Resource
    private StudentInfoMapper studentInfoMapper;

    /**
     * 用户编辑个人信息的业务方法
     *
     * @param userDataSaveReq : 更新的信息
     * @author h0ss
     */
    public void updateInfo(UserDataSaveReq userDataSaveReq) {
        // 拿到当前用户的id，如果未登录会抛出异常，因此后续无需对id判空
        Long id = StpUtil.getLoginIdAsLong();
        // 转换为studentInfo对象
        StudentInfo studentInfo = new StudentInfo();
        BeanUtils.copyProperties(userDataSaveReq, studentInfo);
        if ("".equals(studentInfo.getName())) {
            studentInfo.setName(null);
        }
        // 更新对应id的信息
        StudentInfoExample example = new StudentInfoExample();
        StudentInfoExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        studentInfoMapper.updateByExampleSelective(studentInfo, example);
    }
}

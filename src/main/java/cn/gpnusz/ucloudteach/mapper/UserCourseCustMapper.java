package cn.gpnusz.ucloudteach.mapper;

import cn.gpnusz.ucloudteach.resp.UserCourseResp;

import java.util.List;

/**
 * @author h0ss
 * @description 学员获取学习记录的mapper
 * @date 2021/11/30 - 12:32
 */
public interface UserCourseCustMapper {

    /**
     * 获取自定义的课程-学员信息
     *
     * @param studentId: 学员id
     * @return : java.util.List<cn.gpnusz.ucloudteach.resp.CourseMemberCustResp>
     * @author h0ss
     */
    List<UserCourseResp> selectCourseAndMember(Long studentId);
}

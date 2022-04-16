package cn.gpnusz.ucloudteach.controller.student;

import cn.gpnusz.ucloudteach.common.CommonResp;
import cn.gpnusz.ucloudteach.entity.CoursePeriod;
import cn.gpnusz.ucloudteach.req.CourseCommentSaveReq;
import cn.gpnusz.ucloudteach.resp.UserCourseResp;
import cn.gpnusz.ucloudteach.service.course.CourseMemberService;
import cn.gpnusz.ucloudteach.service.course.UserCourseService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author h0ss
 * @description 用户课程相关接口
 * @date 2021/11/28 1:07
 */
@RestController
@RequestMapping("/api/user/course")
public class UserCourseController {

    @Resource
    private UserCourseService userCourseService;

    @Resource
    private CourseMemberService courseMemberService;

    /**
     * 用户获取学习记录信息的接口
     *
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<java.util.List<cn.gpnusz.ucloudteach.resp.CourseMemberCustResp>>
     * @author h0ss
     */
    @GetMapping("/get")
    public CommonResp<List<UserCourseResp>> selectCourse() {
        CommonResp<List<UserCourseResp>> resp = new CommonResp<>();
        resp.setContent(userCourseService.selectUserCourse());
        resp.setMessage("数据获取成功");
        return resp;
    }

    /**
     * 根据课时id获取课时详情的接口
     *
     * @param periodId : 课时id
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<cn.gpnusz.ucloudteach.entity.CoursePeriod>
     * @author h0ss
     */
    @GetMapping("/period/content")
    public CommonResp<CoursePeriod> getPeriodContent(@NotNull Long periodId) {
        return userCourseService.getContent(periodId);
    }

    /**
     * 检查用户是否购买课程
     *
     * @param courseId : 课程id
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<java.lang.Boolean>
     * @author h0ss
     */
    @GetMapping("/check")
    public CommonResp<Boolean> checkStatus(@NotNull Long courseId) {
        CommonResp<Boolean> resp = new CommonResp<>();
        Boolean status = courseMemberService.checkMember(courseId);
        resp.setMessage("数据获取成功");
        resp.setContent(status);
        return resp;
    }

    /**
     * 用户保存评论信息的接口
     *
     * @param courseCommentSaveReq : 保存信息
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<java.lang.Object>
     * @author h0ss
     */
    @PostMapping("/comment/save")
    public CommonResp<Object> saveComment(@Valid @RequestBody CourseCommentSaveReq courseCommentSaveReq) {
        CommonResp<Object> resp = new CommonResp<>();
        userCourseService.saveComment(courseCommentSaveReq);
        resp.setMessage("评论发表成功");
        return resp;
    }

    /**
     * 用户删除评论的业务方法
     *
     * @param id : 评论id
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<java.lang.Object>
     * @author h0ss
     */
    @DeleteMapping("/comment/delete/{id}")
    public CommonResp<Object> deleteComment(@PathVariable Long id) {
        return userCourseService.deleteComment(id);
    }

    /**
     * 用户获取证书的接口
     *
     * @param courseId : 课程id
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<java.lang.Object>
     * @author h0ss
     */
    @GetMapping("/certificate/get")
    public CommonResp<Object> getCertificate(Long courseId) {
        return userCourseService.getCertificate(courseId);
    }
}

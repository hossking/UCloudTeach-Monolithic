package cn.gpnusz.ucloudteach.controller.common;

import cn.gpnusz.ucloudteach.common.CommonResp;
import cn.gpnusz.ucloudteach.common.PageResp;
import cn.gpnusz.ucloudteach.entity.CourseSection;
import cn.gpnusz.ucloudteach.req.CourseCommentQueryReq;
import cn.gpnusz.ucloudteach.req.CourseCommonReq;
import cn.gpnusz.ucloudteach.req.CourseSectionQueryReq;
import cn.gpnusz.ucloudteach.resp.CommonCommentResp;
import cn.gpnusz.ucloudteach.resp.CommonCoursePeriodResp;
import cn.gpnusz.ucloudteach.resp.CourseCustResp;
import cn.gpnusz.ucloudteach.service.course.CommonCommentService;
import cn.gpnusz.ucloudteach.service.course.CoursePeriodService;
import cn.gpnusz.ucloudteach.service.course.CourseSectionService;
import cn.gpnusz.ucloudteach.service.course.CourseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author h0ss
 * @description 供所有用户访问的公共课程接口
 * @date 2021/11/22 - 17:42
 */
@RestController
@RequestMapping("/api/common/course")
public class CourseCommonController {

    @Resource
    private CourseService courseService;

    @Resource
    private CourseSectionService courseSectionService;

    @Resource
    private CoursePeriodService coursePeriodService;

    @Resource
    private CommonCommentService commonCommentService;

    /**
     * 首页获取热门数据的公共接口
     *
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<java.util.List<cn.gpnusz.ucloudteach.resp.CourseCustResp>>
     * @author h0ss
     */
    @GetMapping("/hot")
    public CommonResp<List<CourseCustResp>> getHot() {
        CommonResp<List<CourseCustResp>> resp = new CommonResp<>();
        resp.setMessage("数据获取成功!");
        resp.setContent(courseService.getHot());
        return resp;
    }

    /**
     * 课程页中根据年级信息获取课程数据的接口
     *
     * @param gradeId   : 年级id
     * @param sortField : 排序字段
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<java.util.List<cn.gpnusz.ucloudteach.resp.CourseCustResp>>
     * @author h0ss
     */
    @GetMapping("/grade/course")
    public CommonResp<List<CourseCustResp>> getByGrade(@NotNull Long gradeId, String sortField) {
        CommonResp<List<CourseCustResp>> resp = new CommonResp<>();
        CourseCommonReq req = new CourseCommonReq();
        req.setGradeId(gradeId);
        req.setSortField(sortField);
        resp.setContent(courseService.getContent(req));
        resp.setMessage("数据获取成功!");
        return resp;
    }

    /**
     * 根据课程id获取课程章节数据的接口
     *
     * @param courseId : 课程id
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<cn.gpnusz.ucloudteach.common.PageResp<cn.gpnusz.ucloudteach.entity.CourseSection>>
     * @author h0ss
     */
    @GetMapping("/section")
    public CommonResp<PageResp<CourseSection>> getSection(@NotNull Long courseId) {
        CourseSectionQueryReq req = new CourseSectionQueryReq();
        req.setCourseId(courseId);
        CommonResp<PageResp<CourseSection>> resp = new CommonResp<>();
        resp.setContent(courseSectionService.getAll(req));
        resp.setMessage("数据获取成功!");
        return resp;
    }

    /**
     * 根据课程id获取课时公共信息的接口
     *
     * @param courseId : 课程id
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<java.util.List<cn.gpnusz.ucloudteach.resp.CommonCoursePeriodResp>>
     * @author h0ss
     */
    @GetMapping("/period")
    public CommonResp<List<CommonCoursePeriodResp>> getPeriod(@NotNull Long courseId) {
        CommonResp<List<CommonCoursePeriodResp>> resp = new CommonResp<>();
        resp.setContent(coursePeriodService.getAllCommon(courseId));
        resp.setMessage("数据获取成功!");
        return resp;
    }

    /**
     * 根据课程id获取课程信息的公共接口
     *
     * @param courseId : 课程id
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<cn.gpnusz.ucloudteach.resp.CourseCustResp>
     * @author h0ss
     */
    @GetMapping("/content")
    public CommonResp<CourseCustResp> getDetail(@NotNull Long courseId) {
        CommonResp<CourseCustResp> resp = new CommonResp<>();
        CourseCommonReq req = new CourseCommonReq();
        req.setCourseId(courseId);
        resp.setContent(courseService.getContent(req).get(0));
        resp.setMessage("数据获取成功!");
        return resp;
    }

    @GetMapping("/comment/get")
    public CommonResp<List<CommonCommentResp>> getComment(CourseCommentQueryReq courseCommentQueryReq) {
        CommonResp<List<CommonCommentResp>> resp = new CommonResp<>();
        resp.setContent(commonCommentService.getComments(courseCommentQueryReq));
        resp.setMessage("数据获取成功!");
        return resp;
    }
}

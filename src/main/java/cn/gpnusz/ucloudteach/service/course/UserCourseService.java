package cn.gpnusz.ucloudteach.service.course;

import cn.dev33.satoken.stp.StpUtil;
import cn.gpnusz.ucloudteach.common.CommonResp;
import cn.gpnusz.ucloudteach.entity.*;
import cn.gpnusz.ucloudteach.mapper.*;
import cn.gpnusz.ucloudteach.req.CourseCommentSaveReq;
import cn.gpnusz.ucloudteach.resp.UserCourseResp;
import cn.gpnusz.ucloudteach.service.AsyncService;
import cn.gpnusz.ucloudteach.service.course.CourseCommentService;
import cn.gpnusz.ucloudteach.service.course.CourseMemberService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @author h0ss
 * @description 用户课程相关业务层
 * @date 2021/11/28 1:07
 */
@Service
public class UserCourseService {
    @Resource
    private UserCourseCustMapper userCourseCustMapper;

    @Resource
    private CoursePeriodMapper coursePeriodMapper;

    @Resource
    private CourseMemberService courseMemberService;

    @Resource
    private CourseCommentService courseCommentService;

    @Resource
    private AsyncService asyncService;

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private CourseMemberMapper courseMemberMapper;


    /**
     * 用户查询已学习课程的业务方法
     *
     * @return : java.util.List<cn.gpnusz.ucloudteach.resp.CourseMemberCustResp>
     * @author h0ss
     */
    public List<UserCourseResp> selectUserCourse() {
        // 获取当前会话的id
        Long userId = StpUtil.getLoginIdAsLong();
        // 查询当前用户学习的课程记录
        return userCourseCustMapper.selectCourseAndMember(userId);
    }

    /**
     * 查询课时详情的业务方法[需要先判断用户是否已经参与课程学习]
     *
     * @param periodId : 课时id
     * @return : cn.gpnusz.ucloudteach.entity.CoursePeriod
     * @author h0ss
     */
    public CommonResp<CoursePeriod> getContent(Long periodId) {
        // 创建返回对象
        CommonResp<CoursePeriod> resp = new CommonResp<>();
        // 判断是否传入课时id
        if (!ObjectUtils.isEmpty(periodId)) {
            CoursePeriodExample coursePeriodExample = new CoursePeriodExample();
            CoursePeriodExample.Criteria criteria = coursePeriodExample.createCriteria();
            criteria.andIdEqualTo(periodId);
            List<CoursePeriod> periodContent = coursePeriodMapper.selectByExampleWithBLOBs(coursePeriodExample);
            if (!ObjectUtils.isEmpty(periodContent)) {
                Long courseId = periodContent.get(0).getCourseId();
                // 学员已参加课程情况
                if (courseMemberService.checkMember(courseId)) {
                    asyncService.writeRecord(StpUtil.getLoginIdAsLong(), courseId, periodId);
                    CoursePeriod content = periodContent.get(0);
                    resp.setContent(content);
                    resp.setMessage("数据获取成功");
                } else {
                    // 学员未参加课程情况
                    resp.setSuccess(false);
                    resp.setMessage("您暂未参与本课程学习，无法查看课程内容");
                }
                return resp;
            }
        }
        resp.setSuccess(false);
        return resp;
    }

    /**
     * 用户保存发表评论的业务方法
     *
     * @param courseCommentSaveReq : 评论相关信息
     * @author h0ss
     */
    public void saveComment(CourseCommentSaveReq courseCommentSaveReq) {
        Long userId = StpUtil.getLoginIdAsLong();
        if (courseMemberService.checkMember(courseCommentSaveReq.getCourseId())) {
            courseCommentSaveReq.setStudentId(userId);
            courseCommentService.save(courseCommentSaveReq);
        }
    }

    /**
     * 删除评论的业务方法
     *
     * @param id : 评论id
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<java.lang.Object>
     * @author h0ss
     */
    public CommonResp<Object> deleteComment(Long id) {
        CommonResp<Object> resp = new CommonResp<>();
        Long userId = StpUtil.getLoginIdAsLong();
        if (courseCommentService.checkPair(userId, id)) {
            courseCommentService.delete(id);
            resp.setMessage("删除成功");
        } else {
            resp.setSuccess(false);
            resp.setMessage("删除失败");
        }
        return resp;
    }

    /**
     * 用户获取证书的业务方法
     *
     * @param courseId : 课程id
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<java.lang.Object>
     * @author h0ss
     */
    public CommonResp<Object> getCertificate(Long courseId) {
        CommonResp<Object> resp = new CommonResp<>();
        resp.setSuccess(false);
        // 查询课程的总课时
        CourseExample courseExample = new CourseExample();
        CourseExample.Criteria courseExampleCriteria = courseExample.createCriteria();
        courseExampleCriteria.andIdEqualTo(courseId);
        List<Course> courses = courseMapper.selectByExample(courseExample);
        if (!courses.isEmpty()) {
            Integer totalPeriod = courses.get(0).getTotalPeriod();
            CourseMemberExample courseMemberExample = new CourseMemberExample();
            CourseMemberExample.Criteria courseMemberExampleCriteria = courseMemberExample.createCriteria();
            courseMemberExampleCriteria.andStudentIdEqualTo(StpUtil.getLoginIdAsLong());
            courseMemberExampleCriteria.andCourseIdEqualTo(courseId);
            List<CourseMember> record = courseMemberMapper.selectByExample(courseMemberExample);
            if (!record.isEmpty() && totalPeriod.equals(record.get(0).getFinishCourse())) {
                resp.setSuccess(true);
                HashMap<String, String> res = new HashMap<>(2);
                res.put("certificate", courses.get(0).getCertificate());
                res.put("sendWord", courses.get(0).getSendWord());
                resp.setContent(res);
                resp.setMessage("证书获取成功");
                return resp;
            }
        }
        resp.setMessage("证书获取失败,请在学习完课程后领取");
        return resp;
    }
}

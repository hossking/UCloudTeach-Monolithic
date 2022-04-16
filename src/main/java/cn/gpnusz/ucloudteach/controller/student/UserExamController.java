package cn.gpnusz.ucloudteach.controller.student;

import cn.gpnusz.ucloudteach.common.CommonResp;
import cn.gpnusz.ucloudteach.req.UserExamSaveReq;
import cn.gpnusz.ucloudteach.resp.UserExamDetailResp;
import cn.gpnusz.ucloudteach.resp.UserExamResp;
import cn.gpnusz.ucloudteach.service.exam.UserExamService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author h0ss
 * @description 用户考试相关接口
 * @date 2021/11/28 3:38
 */
@RestController
@RequestMapping("/api/user/exam")
public class UserExamController {

    @Resource
    private UserExamService userExamService;


    /**
     * 用户获取考试记录信息的接口
     *
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<java.util.List<cn.gpnusz.ucloudteach.resp.UserExamResp>>
     * @author h0ss
     */
    @GetMapping("/get")
    public CommonResp<List<UserExamResp>> selectExam() {
        CommonResp<List<UserExamResp>> resp = new CommonResp<>();
        resp.setContent(userExamService.selectUserExam());
        resp.setMessage("数据获取成功");
        return resp;
    }


    /**
     * 获取试卷的试题信息接口
     * @param id : 试卷id
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<java.util.List<cn.gpnusz.ucloudteach.resp.UserExamDetailResp>>
     * @author h0ss
     */
    @GetMapping("/question/get")
    public CommonResp<List<UserExamDetailResp>> getExamDetail(@Valid Long id) {
        CommonResp<List<UserExamDetailResp>> resp = new CommonResp<>();
        resp.setContent(userExamService.getExamDetail(id));
        return resp;
    }

    /**
     * 获取练习题目数据的接口【带答案】
     * @param id : 试卷id
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<java.util.List<cn.gpnusz.ucloudteach.resp.UserExamDetailResp>>
     * @author h0ss
     */
    @GetMapping("/exercise/get")
    public CommonResp<List<UserExamDetailResp>> getExerciseDetail(@Valid Long id) {
        CommonResp<List<UserExamDetailResp>> resp = new CommonResp<>();
        resp.setContent(userExamService.getExerciseDetail(id));
        return resp;
    }

    /**
     * 考试参加状态检测接口
     * @param id : 试卷id
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<java.lang.Object>
     * @author h0ss
     */
    @GetMapping("/check")
    public CommonResp<Object> checkStatus(Long id) {
        CommonResp<Object> resp = new CommonResp<>();
        if (userExamService.checkExamJoinStatus(id)) {
            resp.setSuccess(false);
            resp.setMessage("您已参加过本次考试，请前往个人中心查看");
        }
        return resp;
    }

    /**
     * 学员获取考试结果信息的接口
     * @param paperId :
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<cn.gpnusz.ucloudteach.resp.UserExamResp>
     * @author h0ss
     */
    @GetMapping("/res/get")
    public CommonResp<UserExamResp> getExamRes(Long paperId) {
        CommonResp<UserExamResp> resp = new CommonResp<>();
        resp.setContent(userExamService.getExamRes(paperId));
        resp.setMessage("数据获取成功");
        return resp;
    }

    /**
     * 学员参与考试的接口
     *
     * @param paperInfo : 试卷信息 解析出其中的id
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<java.lang.Object>
     * @author h0ss
     */
    @PostMapping("/create")
    public CommonResp<Object> joinExam(@RequestBody Map<String, Long> paperInfo) {
        return userExamService.joinExam(paperInfo.get("paperId"));
    }

    /**
     * 提交考试的接口
     * @param userExamSaveReq : 提交信息
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<java.lang.Object>
     * @author h0ss
     */
    @PostMapping("/submit")
    public CommonResp<Object> submitExam(@RequestBody @Valid UserExamSaveReq userExamSaveReq) {
        return userExamService.submitExam(userExamSaveReq);
    }

    /**
     * 考试提交状态检测的接口
     * @param paperId : 试卷id
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<java.lang.Object>
     * @author h0ss
     */
    @GetMapping("/submit/check")
    public CommonResp<Object> checkSubmit(Long paperId) {
        CommonResp<Object> resp = new CommonResp<>();
        resp.setSuccess(!userExamService.checkSubmit(paperId));
        return resp;
    }

    /**
     * 获取考试剩余时间
     * @param paperId : 试卷id
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<java.lang.Integer>
     * @author h0ss
     */
    @GetMapping("/time")
    public CommonResp<Integer> getSurplus(Long paperId) {
        CommonResp<Integer> resp = new CommonResp<>();
        resp.setContent(userExamService.getSurplusTime(paperId));
        resp.setMessage("数据获取成功");
        return resp;
    }
}

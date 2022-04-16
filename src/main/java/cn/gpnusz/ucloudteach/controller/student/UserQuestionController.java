package cn.gpnusz.ucloudteach.controller.student;

import cn.gpnusz.ucloudteach.common.CommonResp;
import cn.gpnusz.ucloudteach.resp.QuestionAnswerResp;
import cn.gpnusz.ucloudteach.service.exam.QuestionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author h0ss
 * @description 学员获取习题答案的接口
 * @date 2021/12/2 - 1:11
 */
@RestController
@RequestMapping("/api/user/question")
public class UserQuestionController {

    @Resource
    private QuestionService questionService;

    @GetMapping("/answer/get")
    public CommonResp<QuestionAnswerResp> getAnswer(Long id) {
        CommonResp<QuestionAnswerResp> resp = new CommonResp<>();
        resp.setContent(questionService.getAnswer(id));
        resp.setMessage("数据获取成功");
        return resp;
    }
}

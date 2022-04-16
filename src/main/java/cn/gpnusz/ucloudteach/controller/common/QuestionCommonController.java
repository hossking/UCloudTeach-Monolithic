package cn.gpnusz.ucloudteach.controller.common;

import cn.gpnusz.ucloudteach.common.CommonResp;
import cn.gpnusz.ucloudteach.common.PageResp;
import cn.gpnusz.ucloudteach.req.QuestionQueryReq;
import cn.gpnusz.ucloudteach.resp.CommonQuestionResp;
import cn.gpnusz.ucloudteach.service.exam.CommonQuestionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author h0ss
 * @description 公共接口用于获取题目信息
 * @date 2021/12/1 16:58
 */
@RestController
@RequestMapping("/api/common/question")
public class QuestionCommonController {

    @Resource
    private CommonQuestionService commonQuestionService;


    /**
     * 获取题目公共信息
     *
     * @param questionQueryReq :
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<cn.gpnusz.ucloudteach.common.PageResp<cn.gpnusz.ucloudteach.resp.CommonQuestionResp>>
     * @author h0ss
     */
    @GetMapping("/get")
    public CommonResp<PageResp<CommonQuestionResp>> get(QuestionQueryReq questionQueryReq) {
        CommonResp<PageResp<CommonQuestionResp>> resp = new CommonResp<>();
        resp.setMessage("数据获取成功!");
        resp.setContent(commonQuestionService.getQuestion(questionQueryReq));
        return resp;
    }
}

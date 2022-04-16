package cn.gpnusz.ucloudteach.service.exam;

import cn.gpnusz.ucloudteach.common.PageResp;
import cn.gpnusz.ucloudteach.entity.Question;
import cn.gpnusz.ucloudteach.req.QuestionQueryReq;
import cn.gpnusz.ucloudteach.resp.CommonQuestionResp;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @author h0ss
 * @description 公共的题目相关业务层
 * @date 2021/12/1 - 23:03
 */
@Service
public class CommonQuestionService {

    @Resource
    private QuestionService questionService;

    /**
     * 通用的获取题目的方法【屏蔽答案字段】
     *
     * @param questionQueryReq : 查询参数封装
     * @return : cn.gpnusz.ucloudteach.common.PageResp<cn.gpnusz.ucloudteach.resp.CommonQuestionResp>
     * @author h0ss
     */
    public PageResp<CommonQuestionResp> getQuestion(QuestionQueryReq questionQueryReq) {
        PageResp<CommonQuestionResp> pageResp = new PageResp<>();
        PageResp<Question> list = questionService.getAllByCondition(questionQueryReq);
        if (list.getTotal() > 0) {
            ArrayList<CommonQuestionResp> respList = new ArrayList<>(Math.toIntExact(list.getTotal()));
            for (Question question : list.getList()) {
                CommonQuestionResp resp = new CommonQuestionResp();
                BeanUtils.copyProperties(question, resp);
                respList.add(resp);
            }
            pageResp.setList(respList);
            pageResp.setTotal(list.getTotal());
        }
        return pageResp;
    }
}

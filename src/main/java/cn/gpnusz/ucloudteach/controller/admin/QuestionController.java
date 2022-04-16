package cn.gpnusz.ucloudteach.controller.admin;

import cn.gpnusz.ucloudteach.common.CommonResp;
import cn.gpnusz.ucloudteach.common.PageResp;
import cn.gpnusz.ucloudteach.entity.Question;
import cn.gpnusz.ucloudteach.req.QuestionQueryReq;
import cn.gpnusz.ucloudteach.req.QuestionSaveReq;
import cn.gpnusz.ucloudteach.service.exam.QuestionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @description 操作题目信息的接口
 * @author h0ss
 * @date 2021/11/18 17:32
 */

@RestController
@RequestMapping("/api/admin/question")
public class QuestionController {

    @Resource
    private QuestionService questionService;


    /**
     * 按传入条件查询题目信息
     * @param question : 查询条件对象
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<cn.gpnusz.ucloudteach.common.PageResp<cn.gpnusz.ucloudteach.entity.Question>>
     * @author h0ss
     */
    @GetMapping("/search")
    public CommonResp<PageResp<Question>> search(@Valid QuestionQueryReq question) {
        CommonResp<PageResp<Question>> resp = new CommonResp<>();
        resp.setMessage("数据获取成功!");
        resp.setContent(questionService.getAllByCondition(question));
        return resp;
    }

    /**
     * 新增/编辑题目信息
     *
     * @param questionSaveReq : 保存的题目信息
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<java.lang.Object>
     * @author h0ss
     */
    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody QuestionSaveReq questionSaveReq) {
        questionService.save(questionSaveReq);
        return new CommonResp<>();
    }

    /**
     * 根据id删除题目信息
     *
     * @param id : 题目id
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<java.lang.Object>
     * @author h0ss
     */
    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id) {
        questionService.delete(id);
        return new CommonResp<>();
    }
}

package cn.gpnusz.ucloudteach.controller.common;

import cn.gpnusz.ucloudteach.common.CommonResp;
import cn.gpnusz.ucloudteach.common.PageResp;
import cn.gpnusz.ucloudteach.entity.ExamPaper;
import cn.gpnusz.ucloudteach.req.ExamPaperQueryReq;
import cn.gpnusz.ucloudteach.service.exam.CommonExamService;
import cn.gpnusz.ucloudteach.service.exam.ExamPaperService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author h0ss
 * @description 公共接口用于获取试卷信息
 * @date 2021/11/30 23:29
 */
@RestController
@RequestMapping("/api/common/exam")
public class ExamCommonController {

    @Resource
    private ExamPaperService examPaperService;

    @Resource
    private CommonExamService commonExamService;

    /**
     * 分页获取所有试卷信息接口
     *
     * @param examPaper : 分页信息
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<cn.gpnusz.ucloudteach.common.PageResp<cn.gpnusz.ucloudteach.entity.ExamPaper>>
     * @author h0ss
     */
    @GetMapping("/get")
    public CommonResp<PageResp<ExamPaper>> get(@Valid ExamPaperQueryReq examPaper) {
        // 只获取已发布的试卷
        examPaper.setStatus(Boolean.TRUE);
        CommonResp<PageResp<ExamPaper>> resp = new CommonResp<>();
        resp.setMessage("数据获取成功!");
        resp.setContent(commonExamService.getPaperCommon(examPaper));
        return resp;
    }

    /**
     * 按照年级id筛选试卷信息接口
     *
     * @param gradeId : 年级id
     * @param size    : 每页数目
     * @param page    : 页码
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<cn.gpnusz.ucloudteach.common.PageResp<cn.gpnusz.ucloudteach.entity.ExamPaper>>
     * @author h0ss
     */
    @GetMapping("/get/grade")
    public CommonResp<PageResp<ExamPaper>> getByGrade(Long gradeId, Integer size, Integer page) {
        CommonResp<PageResp<ExamPaper>> resp = new CommonResp<>();
        resp.setMessage("数据获取成功!");
        resp.setContent(commonExamService.getPaperByGrade(gradeId, size, page));
        return resp;
    }

}

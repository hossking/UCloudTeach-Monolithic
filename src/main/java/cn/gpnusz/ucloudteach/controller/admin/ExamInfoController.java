package cn.gpnusz.ucloudteach.controller.admin;

import cn.gpnusz.ucloudteach.common.CommonResp;
import cn.gpnusz.ucloudteach.common.PageReq;
import cn.gpnusz.ucloudteach.common.PageResp;
import cn.gpnusz.ucloudteach.resp.ExamCust;
import cn.gpnusz.ucloudteach.service.exam.ExamInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @description 操作考试信息的接口
 * @author h0ss
 * @date 2021/11/20 17:58
 */

@RestController
@RequestMapping("/api/admin/exam")
public class ExamInfoController {

    @Resource
    private ExamInfoService examInfoService;

    @GetMapping("/list")
    public CommonResp<PageResp<ExamCust>> search(@Valid PageReq pageReq) {
        CommonResp<PageResp<ExamCust>> resp = new CommonResp<>();
        resp.setMessage("数据获取成功!");
        resp.setContent(examInfoService.getAll(pageReq));
        return resp;
    }
}

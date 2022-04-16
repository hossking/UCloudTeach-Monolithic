package cn.gpnusz.ucloudteach.controller.common;

import cn.gpnusz.ucloudteach.common.CommonResp;
import cn.gpnusz.ucloudteach.entity.Subject;
import cn.gpnusz.ucloudteach.service.course.SubjectService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author h0ss
 * @description
 * @date 2021/11/24 - 8:41
 */
@RestController
@RequestMapping("/api/common/subject")
public class SubjectCommonController {

    @Resource
    private SubjectService subjectService;

    /**
     * 根据年级id获取科目信息
     *
     * @param gradeId : 年级id
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<java.util.List<cn.gpnusz.ucloudteach.entity.Subject>>
     * @author h0ss
     */
    @GetMapping("/get")
    public CommonResp<List<Subject>> getSubject(@NotNull Long gradeId) {
        CommonResp<List<Subject>> resp = new CommonResp<>();
        resp.setContent(subjectService.getByGrade(gradeId));
        resp.setMessage("数据获取成功!");
        return resp;
    }

    @GetMapping("/list")
    public CommonResp<List<Subject>> list() {
        CommonResp<List<Subject>> resp = new CommonResp<>();
        resp.setContent(subjectService.getAll());
        resp.setMessage("数据获取成功!");
        return resp;
    }
}

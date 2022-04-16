package cn.gpnusz.ucloudteach.controller.admin;

import cn.gpnusz.ucloudteach.common.CommonResp;
import cn.gpnusz.ucloudteach.entity.Grade;
import cn.gpnusz.ucloudteach.req.GradeSaveReq;
import cn.gpnusz.ucloudteach.service.course.GradeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author h0ss
 * @description 年级信息接口
 * @date 2021/11/13 - 13:49
 */
@RestController
@RequestMapping("/api/admin/grade")
public class GradeController {

    @Resource
    private GradeService gradeService;

    /**
     * 获取年级信息的接口
     *
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<java.util.List<cn.gpnusz.ucloudteach.entity.Grade>>
     * @author h0ss
     */
    @GetMapping("/list")
    public CommonResp<List<Grade>> list() {
        CommonResp<List<Grade>> resp = new CommonResp<>();
        resp.setMessage("数据获取成功!");
        resp.setContent(gradeService.getAll());
        return resp;
    }

    /**
     * 保存年级信息的接口
     *
     * @param gradeSaveReq : 保存的年级信息
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<java.lang.Object>
     * @author h0ss
     */
    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody GradeSaveReq gradeSaveReq) {
        gradeService.save(gradeSaveReq);
        return new CommonResp<>();
    }

    /**
     * 删除年级信息的接口
     *
     * @param id : 年级id
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<java.lang.Object>
     * @author h0ss
     */
    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id) {
        gradeService.delete(id);
        return new CommonResp<>();
    }

}

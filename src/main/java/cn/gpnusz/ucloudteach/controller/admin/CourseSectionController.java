package cn.gpnusz.ucloudteach.controller.admin;

import cn.gpnusz.ucloudteach.common.CommonResp;
import cn.gpnusz.ucloudteach.common.PageResp;
import cn.gpnusz.ucloudteach.entity.CourseSection;
import cn.gpnusz.ucloudteach.req.CourseSectionQueryReq;
import cn.gpnusz.ucloudteach.req.CourseSectionSaveReq;
import cn.gpnusz.ucloudteach.service.course.CourseSectionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author h0ss
 * @description 课程章节信息接口
 * @date 2021/11/14 23:47
 */

@RestController
@RequestMapping("/api/admin/course-section")
public class CourseSectionController {

    @Resource
    private CourseSectionService courseSectionService;

    /**
     * 展示指定课程下所有章节信息数据
     *
     * @param courseSectionQueryReq : 请求对象
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<cn.gpnusz.ucloudteach.common.PageResp<cn.gpnusz.ucloudteach.entity.CourseSection>>
     * @author h0ss
     */
    @GetMapping("/list")
    public CommonResp<PageResp<CourseSection>> list(@Valid CourseSectionQueryReq courseSectionQueryReq) {
        CommonResp<PageResp<CourseSection>> resp = new CommonResp<>();
        resp.setMessage("数据获取成功!");
        resp.setContent(courseSectionService.getAll(courseSectionQueryReq));
        return resp;
    }

    /**
     * 新增/编辑章节信息
     *
     * @param courseSectionSaveReq : 保存的章节信息
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<java.lang.Object>
     * @author h0ss
     */
    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody CourseSectionSaveReq courseSectionSaveReq) {
        courseSectionService.save(courseSectionSaveReq);
        return new CommonResp<>();
    }

    /**
     * 根据id删除章节信息
     *
     * @param id : 章节id
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<java.lang.Object>
     * @author h0ss
     */
    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id) {
        courseSectionService.delete(id);
        return new CommonResp<>();
    }
}

package cn.gpnusz.ucloudteach.controller.admin;

import cn.gpnusz.ucloudteach.common.CommonResp;
import cn.gpnusz.ucloudteach.common.PageResp;
import cn.gpnusz.ucloudteach.entity.CourseMember;
import cn.gpnusz.ucloudteach.req.CourseMemberQueryReq;
import cn.gpnusz.ucloudteach.req.CourseMemberSaveReq;
import cn.gpnusz.ucloudteach.service.course.CourseMemberService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author h0ss
 * @description 操作课程成员信息的接口
 * @date 2021/11/17 2:44
 */

@RestController
@RequestMapping("/api/admin/course-member")
public class CourseMemberController {

    @Resource
    private CourseMemberService courseMemberService;

    /**
     * 展示所有课程成员信息数据
     *
     * @param courseMember : 请求参数（分页参数）
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<cn.gpnusz.ucloudteach.common.PageResp<cn.gpnusz.ucloudteach.entity.CourseMember>>
     * @author h0ss
     */
    @GetMapping("/list")
    public CommonResp<PageResp<CourseMember>> list(@Valid CourseMemberQueryReq courseMember) {
        CommonResp<PageResp<CourseMember>> resp = new CommonResp<>();
        resp.setMessage("数据获取成功!");
        resp.setContent(courseMemberService.getAll(courseMember));
        return resp;
    }

    /**
     * 按传入条件查询课程成员信息
     *
     * @param courseMember : 课程成员信息查询参数
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<cn.gpnusz.ucloudteach.common.PageResp<cn.gpnusz.ucloudteach.entity.CourseMember>>
     * @author h0ss
     */
    @GetMapping("/search")
    public CommonResp<PageResp<CourseMember>> search(@Valid CourseMemberQueryReq courseMember) {
        CommonResp<PageResp<CourseMember>> resp = new CommonResp<>();
        resp.setMessage("数据获取成功!");
        resp.setContent(courseMemberService.getAllByCondition(courseMember));
        return resp;
    }

    /**
     * 新增/编辑课程成员信息
     *
     * @param courseMemberSaveReq : 保存的课程成员信息
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<java.lang.Object>
     * @author h0ss
     */
    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody CourseMemberSaveReq courseMemberSaveReq) {
        courseMemberService.save(courseMemberSaveReq, false);
        return new CommonResp<>();
    }

    /**
     * 根据id删除课程成员信息
     *
     * @param id : 课程成员信息id
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<java.lang.Object>
     * @author h0ss
     */
    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id) {
        courseMemberService.delete(id);
        return new CommonResp<>();
    }
}

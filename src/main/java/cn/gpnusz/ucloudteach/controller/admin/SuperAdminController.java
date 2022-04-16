package cn.gpnusz.ucloudteach.controller.admin;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.gpnusz.ucloudteach.common.CommonResp;
import cn.gpnusz.ucloudteach.common.PageReq;
import cn.gpnusz.ucloudteach.common.PageResp;
import cn.gpnusz.ucloudteach.entity.Admin;
import cn.gpnusz.ucloudteach.req.AdminSaveReq;
import cn.gpnusz.ucloudteach.service.user.SuperAdminService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author h0ss
 * @description 操作管理员的接口
 * @date 2021/11/23 - 21:26
 */
@RestController
@SaCheckRole("superAdmin")
@RequestMapping("/api/admin/super")
public class SuperAdminController {

    @Resource
    private SuperAdminService superAdminService;


    @GetMapping("/list")
    public CommonResp<PageResp<Admin>> list(@Valid PageReq pageReq) {
        CommonResp<PageResp<Admin>> resp = new CommonResp<>();
        resp.setMessage("数据获取成功!");
        resp.setContent(superAdminService.getAll(pageReq));
        return resp;
    }


    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody AdminSaveReq adminSaveReq) {
        superAdminService.save(adminSaveReq);
        return new CommonResp<>();
    }


    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id) {
        superAdminService.delete(id);
        return new CommonResp<>();
    }
}

package cn.gpnusz.ucloudteach.controller.admin;

import cn.gpnusz.ucloudteach.common.CommonResp;
import cn.gpnusz.ucloudteach.req.AdminLoginReq;
import cn.gpnusz.ucloudteach.resp.AdminLoginResp;
import cn.gpnusz.ucloudteach.service.user.AdminLoginService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author h0ss
 * @description 管理员登录接口
 * @date 2021/11/23 - 3:01
 */
@RestController
@RequestMapping("/api/admin")
public class LoginController {

    @Resource
    private AdminLoginService adminLoginService;

    @PostMapping("/login")
    public CommonResp<AdminLoginResp> login(@RequestBody @Valid AdminLoginReq adminLoginReq) {
        CommonResp<AdminLoginResp> resp = new CommonResp<>();
        resp.setContent(adminLoginService.adminLogin(adminLoginReq));
        return resp;
    }

    @GetMapping("/check-login")
    public CommonResp<Object> checkLogin() {
        adminLoginService.checkLogin();
        return new CommonResp<>();
    }

    @GetMapping("/logout")
    public CommonResp<Object> logout() {
        adminLoginService.logout();
        return new CommonResp<>();
    }
}

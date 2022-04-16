package cn.gpnusz.ucloudteach.controller.student;

import cn.gpnusz.ucloudteach.common.CommonResp;
import cn.gpnusz.ucloudteach.req.UserLoginReq;
import cn.gpnusz.ucloudteach.req.UserRegisterReq;
import cn.gpnusz.ucloudteach.resp.UserLoginResp;
import cn.gpnusz.ucloudteach.service.user.UserLoginService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 用户登录信息接口
 *
 * @author h0ss
 * @date 2021/11/26 1:50
 */

@RestController
@RequestMapping("/api/user/auth")
public class UserLoginController {

    @Resource
    private UserLoginService userLoginService;

    /**
     * 登录接口
     *
     * @param userLoginReq : 用户登录信息
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<cn.gpnusz.ucloudteach.resp.UserLoginResp>
     * @author h0ss
     */
    @PostMapping("/login")
    public CommonResp<UserLoginResp> login(@RequestBody @Valid UserLoginReq userLoginReq) {
        CommonResp<UserLoginResp> resp = new CommonResp<>();
        resp.setContent(userLoginService.userLogin(userLoginReq));
        return resp;
    }

    /**
     * 用户检查登录的接口
     *
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<java.lang.Object>
     * @author h0ss
     */
    @GetMapping("/login/check")
    public CommonResp<Object> checkLogin() {
        userLoginService.checkLogin();
        return new CommonResp<>();
    }

    /**
     * 用户退登接口
     *
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<java.lang.Object>
     * @author h0ss
     */
    @GetMapping("/logout")
    public CommonResp<Object> logout() {
        userLoginService.logout();
        return new CommonResp<>();
    }

    /**
     * 用户注册的接口
     *
     * @param userRegisterReq : 用户注册的信息
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<java.lang.Object>
     * @author h0ss
     */
    @PostMapping("/register")
    public CommonResp<Object> register(@RequestBody @Valid UserRegisterReq userRegisterReq) {
        return userLoginService.userRegister(userRegisterReq);
    }

    /**
     * 用户请求发送验证码的接口
     *
     * @param phone : 手机号
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<java.lang.Object>
     * @author h0ss
     */
    @GetMapping("/code/request")
    public CommonResp<Object> reqCode(String phone) {
        return userLoginService.requestSend(phone);
    }

    /**
     * 用户找回密码的接口
     *
     * @param userRegisterReq : 请求信息
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<java.lang.Object>
     * @author h0ss
     */
    @PostMapping("/reset")
    public CommonResp<Object> resetPasswd(@RequestBody @Valid UserRegisterReq userRegisterReq) {
        return userLoginService.resetPasswd(userRegisterReq);
    }
}

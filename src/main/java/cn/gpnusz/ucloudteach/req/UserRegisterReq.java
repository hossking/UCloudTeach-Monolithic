package cn.gpnusz.ucloudteach.req;

import javax.validation.constraints.NotBlank;

/**
 * @author h0ss
 * @description 用户注册信息的实体类
 * @date 2021/11/26 14:04
 */
public class UserRegisterReq extends UserLoginReq {
    @NotBlank(message = "【验证码】不能为空")
    private String authCode;

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    @Override
    public String toString() {
        return "UserRegisterReq{" +
                "authCode='" + authCode + '\'' +
                "} " + super.toString();
    }
}
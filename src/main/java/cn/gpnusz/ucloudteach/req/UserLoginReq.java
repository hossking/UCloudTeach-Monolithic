package cn.gpnusz.ucloudteach.req;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
/**
 * @description 用户登录信息的实体类
 * @author h0ss
 * @date 2021/11/26 11:03
 */
public class UserLoginReq extends CheckCodeReq{
    @NotBlank(message = "【手机号】不能为空")
    @Pattern(regexp = "^[1][3-9][0-9]{9}$", message = "【手机号】不合法")
    private String phone;

    @NotBlank(message = "密码不能为空")
    private String password;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserLoginReq{" +
                "phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
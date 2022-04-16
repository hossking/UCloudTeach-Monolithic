package cn.gpnusz.ucloudteach.req;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import javax.validation.constraints.NotBlank;

/**
 * @author h0ss
 * @description 重置密码请求封装
 * @date 2021/11/12 15:04
 */

public class StuResetPasswordReq {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @NotBlank(message = "【密码】不能为空")
    private String password;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "StuResetPasswordReq{" +
                "id=" + id +
                ", password='" + password + '\'' +
                '}';
    }
}
package cn.gpnusz.ucloudteach.req;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author h0ss
 * @description 封装管理员的保存信息
 * @date 2021/11/23 21:36
 */

public class AdminSaveReq {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @NotBlank(message = "【密码】不能为空")
    private String password;

    @NotBlank(message = "【用户名】不能为空")
    private String username;

    @NotNull(message = "【超管标志】不能为空")
    private Boolean superFlag;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getSuperFlag() {
        return superFlag;
    }

    public void setSuperFlag(Boolean superFlag) {
        this.superFlag = superFlag;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "AdminSaveReq{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", superFlag=" + superFlag +
                '}';
    }
}
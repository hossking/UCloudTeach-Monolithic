package cn.gpnusz.ucloudteach.resp;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
/**
 * @description 管理员登录成功的返回实体类
 * @author h0ss
 * @date 2021/11/23 3:10
 */
public class AdminLoginResp {
    private String token;

    private String username;

    private Boolean superFlag;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getSuperFlag() {
        return superFlag;
    }

    public void setSuperFlag(Boolean superFlag) {
        this.superFlag = superFlag;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "AdminLoginResp{" +
                "token='" + token + '\'' +
                ", username='" + username + '\'' +
                ", superFlag=" + superFlag +
                '}';
    }
}
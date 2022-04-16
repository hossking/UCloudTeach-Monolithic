package cn.gpnusz.ucloudteach.req;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author h0ss
 * @description 保存/更新学员信息请求参数封装
 * @date 2021/11/12 14:19
 */


public class StudentInfoSaveReq {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @NotBlank(message = "【手机号】不能为空")
    @Pattern(regexp = "^[1][3-9][0-9]{9}$", message = "【手机号】不合法")
    private String phone;

    @NotBlank(message = "【姓名】不能为空")
    private String name;

    @NotBlank(message = "【密码】不能为空")
    private String password;

    private Boolean gender;

    private Boolean disableFlag;

    private String headPic;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Boolean getDisableFlag() {
        return disableFlag;
    }

    public void setDisableFlag(Boolean disableFlag) {
        this.disableFlag = disableFlag;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    @Override
    public String toString() {
        return "StudentInfoSaveReq{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", gender=" + gender +
                ", disableFlag=" + disableFlag +
                ", headPic='" + headPic + '\'' +
                '}';
    }
}
package cn.gpnusz.ucloudteach.req;

import cn.gpnusz.ucloudteach.common.PageReq;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * @author h0ss
 * @description 查询学员信息时请求参数的封装
 * @date 2021/11/12 14:12
 */
public class StudentInfoQueryReq extends PageReq {
    /**
     * 指定保留精度 防止因Java与JS中long类型精度不同导致的数据不一致问题
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String name;

    private Boolean gender;

    private Boolean disableFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "StudentInfoQueryReq{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", disableFlag=" + disableFlag +
                "} " + super.toString();
    }
}

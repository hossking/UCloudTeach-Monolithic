package cn.gpnusz.ucloudteach.req;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import javax.validation.constraints.NotNull;

/**
 * @author h0ss
 * @description 封装轮播图配置的保存信息
 * @date 2021/11/21 14:44
 */

public class SwipeConfigSaveReq {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @NotNull(message = "【图片地址】不能为空")
    private String picUrl;

    private String url;

    private Boolean type;

    @NotNull(message = "【顺序】不能为空")
    private Integer sort;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "SwipeConfigSaveReq{" +
                "id=" + id +
                ", picUrl='" + picUrl + '\'' +
                ", url='" + url + '\'' +
                ", type=" + type +
                ", sort=" + sort +
                '}';
    }
}
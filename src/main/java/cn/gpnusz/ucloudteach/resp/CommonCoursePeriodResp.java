package cn.gpnusz.ucloudteach.resp;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * @author h0ss
 * @description 课时信息公共实体类
 * @date 2021/11/28 15:52
 */
public class CommonCoursePeriodResp {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String title;

    private Integer period;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long sectionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    @Override
    public String toString() {
        return "CommonCoursePeriodResp{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", period=" + period +
                ", sectionId=" + sectionId +
                '}';
    }
}
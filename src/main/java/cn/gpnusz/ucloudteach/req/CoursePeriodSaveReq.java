package cn.gpnusz.ucloudteach.req;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author h0ss
 * @description 封装课程下课时保存信息
 * @date 2021/11/14 22:59
 */
public class CoursePeriodSaveReq {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @NotNull(message = "【课时所属课程】不能为空")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long courseId;

    @NotNull(message = "【课时所属章节】不能为空")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long sectionId;

    @NotBlank(message = "【课时标题】不能为空")
    private String title;

    @NotNull(message = "【课时数】不能为空")
    private Integer period;

    @Min(value = 0, message = "【课时价格】不能为负数")
    @NotNull(message = "【课时价格】不能为空")
    private Double price;

    @NotBlank(message = "【课时简介】不能为空")
    private String description;

    private String video;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    @Override
    public String toString() {
        return "CoursePeriodSaveReq{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", sectionId=" + sectionId +
                ", title='" + title + '\'' +
                ", period=" + period +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", video='" + video + '\'' +
                '}';
    }
}
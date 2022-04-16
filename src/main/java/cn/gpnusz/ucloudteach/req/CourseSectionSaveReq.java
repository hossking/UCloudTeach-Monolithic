package cn.gpnusz.ucloudteach.req;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author h0ss
 * @description 封装课程章节保存信息
 * @date 2021/11/14 22:55
 */

public class CourseSectionSaveReq {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @NotNull(message = "【章节所属课程】不能为空")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long courseId;

    @NotNull(message = "【章节数】不能为空")
    private Integer section;

    @NotBlank(message = "【章节标题】不能为空")
    private String title;

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

    public Integer getSection() {
        return section;
    }

    public void setSection(Integer section) {
        this.section = section;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "CourseSectionSaveReq{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", section=" + section +
                ", title='" + title + '\'' +
                '}';
    }
}
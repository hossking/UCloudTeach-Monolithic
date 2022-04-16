package cn.gpnusz.ucloudteach.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

public class CourseSection {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private Integer section;

    private String title;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long courseId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", section=").append(section);
        sb.append(", title=").append(title);
        sb.append(", courseId=").append(courseId);
        sb.append("]");
        return sb.toString();
    }
}
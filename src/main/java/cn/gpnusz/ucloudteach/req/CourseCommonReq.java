package cn.gpnusz.ucloudteach.req;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * @author h0ss
 * @description 封装公共接口查询课程信息的参数
 * @date 2021/11/25 - 18:16
 */
public class CourseCommonReq {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long gradeId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long courseId;

    private String sortField;

    public Long getGradeId() {
        return gradeId;
    }

    public void setGradeId(Long gradeId) {
        this.gradeId = gradeId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    @Override
    public String toString() {
        return "CourseCommonReq{" +
                "gradeId=" + gradeId +
                ", courseId=" + courseId +
                ", sortField='" + sortField + '\'' +
                '}';
    }
}

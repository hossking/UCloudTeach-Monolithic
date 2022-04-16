package cn.gpnusz.ucloudteach.req;

import cn.gpnusz.ucloudteach.common.PageReq;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.util.Date;

/**
 * @author h0ss
 * @description 封装查询课程学员的信息
 * @date 2021/11/17 2:01
 */
public class CourseMemberQueryReq extends PageReq {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long studentId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long courseId;

    private String joinDate;

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

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    @Override
    public String toString() {
        return "CourseMemberQueryReq{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", courseId=" + courseId +
                ", joinDate='" + joinDate + '\'' +
                "} " + super.toString();
    }
}
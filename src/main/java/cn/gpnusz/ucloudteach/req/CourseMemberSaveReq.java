package cn.gpnusz.ucloudteach.req;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author h0ss
 * @description 封装保存课程成员的信息
 * @date 2021/11/17 2:08
 */

public class CourseMemberSaveReq {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @JsonSerialize(using = ToStringSerializer.class)
    @NotNull(message = "【课程ID】不能为空")
    private Long courseId;

    @JsonSerialize(using = ToStringSerializer.class)
    @NotNull(message = "【学员ID】不能为空")
    private Long studentId;

    private Date joinDate;

    private Date joinTime;

    private Integer finishCourse;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Date getJoinTime() {
        Date ret = new Date();
        if (!ObjectUtils.isEmpty(joinTime)) {
            BeanUtils.copyProperties(joinTime, ret);
        }
        return ret;
    }

    public void setJoinTime(Date joinTime) {
        Date setValue = new Date();
        if (!ObjectUtils.isEmpty(joinTime)) {
            BeanUtils.copyProperties(joinTime,setValue);
        }
        this.joinTime = setValue;
    }

    public Integer getFinishCourse() {
        return finishCourse;
    }

    public void setFinishCourse(Integer finishCourse) {
        this.finishCourse = finishCourse;
    }

    public Date getJoinDate() {
        Date ret = new Date();
        if (!ObjectUtils.isEmpty(joinDate)) {
            BeanUtils.copyProperties(joinDate, ret);
        }
        return ret;
    }

    public void setJoinDate(Date joinDate) {
        Date setValue = new Date();
        if (!ObjectUtils.isEmpty(joinDate)) {
            BeanUtils.copyProperties(joinDate,setValue);
        }
        this.joinDate = setValue;
    }

    @Override
    public String toString() {
        return "CourseMemberSaveReq{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", studentId=" + studentId +
                ", joinDate=" + joinDate +
                ", joinTime=" + joinTime +
                ", finishCourse=" + finishCourse +
                '}';
    }
}
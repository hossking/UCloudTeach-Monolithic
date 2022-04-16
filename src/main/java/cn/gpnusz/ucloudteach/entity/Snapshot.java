package cn.gpnusz.ucloudteach.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;

import java.util.Date;

public class Snapshot {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createDate;

    private Integer courseCount;

    private Integer studentCount;

    private Integer paperCount;

    private Integer questionCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        Date date = new Date();
        if (!ObjectUtils.isEmpty(createDate)) {
            BeanUtils.copyProperties(createDate, date);
        }
        return date;
    }

    public void setCreateDate(Date createDate) {
        Date date = new Date();
        if (!ObjectUtils.isEmpty(createDate)) {
            BeanUtils.copyProperties(createDate, date);
        }
        this.createDate = date;
    }

    public Integer getCourseCount() {
        return courseCount;
    }

    public void setCourseCount(Integer courseCount) {
        this.courseCount = courseCount;
    }

    public Integer getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(Integer studentCount) {
        this.studentCount = studentCount;
    }

    public Integer getPaperCount() {
        return paperCount;
    }

    public void setPaperCount(Integer paperCount) {
        this.paperCount = paperCount;
    }

    public Integer getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(Integer questionCount) {
        this.questionCount = questionCount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", createDate=").append(createDate);
        sb.append(", courseCount=").append(courseCount);
        sb.append(", studentCount=").append(studentCount);
        sb.append(", paperCount=").append(paperCount);
        sb.append(", questionCount=").append(questionCount);
        sb.append("]");
        return sb.toString();
    }
}
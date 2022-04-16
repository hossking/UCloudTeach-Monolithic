package cn.gpnusz.ucloudteach.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author h0ss
 * @description 封装试卷的保存信息
 * @date 2021/11/19 18:21
 */

public class ExamPaperSaveReq {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @NotBlank(message = "【试卷名称】不能为空")
    private String name;

    private Integer totalScore;

    private Integer passScore;

    @JsonSerialize(using = ToStringSerializer.class)
    @NotNull(message = "【科目ID】不能为空")
    private Long subjectId;

    @JsonSerialize(using = ToStringSerializer.class)
    @NotNull(message = "【课程ID】不能为空")
    private Long courseId;

    private Integer sort;

    private Integer joinCount;

    @NotNull(message = "【考试时长】不能为空")
    private Integer examTime;

    private Integer checkCount;

    private Boolean status;

    private Integer questionCount;

    private Boolean autoCheck;

    private String remark;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getJoinCount() {
        return joinCount;
    }

    public void setJoinCount(Integer joinCount) {
        this.joinCount = joinCount;
    }

    public Integer getCheckCount() {
        return checkCount;
    }

    public void setCheckCount(Integer checkCount) {
        this.checkCount = checkCount;
    }

    public Integer getExamTime() {
        return examTime;
    }

    public void setExamTime(Integer examTime) {
        this.examTime = examTime;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getStartDate() {
        Date date = new Date();
        if (!ObjectUtils.isEmpty(startDate)) {
            BeanUtils.copyProperties(startDate, date);
        }
        return date;
    }

    public void setStartDate(Date startDate) {
        Date date = new Date();
        if (!ObjectUtils.isEmpty(startDate)) {
            BeanUtils.copyProperties(startDate,date);
        }
        this.startDate = date;
    }

    public Integer getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(Integer questionCount) {
        this.questionCount = questionCount;
    }

    public Boolean getAutoCheck() {
        return autoCheck;
    }

    public void setAutoCheck(Boolean autoCheck) {
        this.autoCheck = autoCheck;
    }

    public Date getEndDate() {
        Date date = new Date();
        if (!ObjectUtils.isEmpty(endDate)) {
            BeanUtils.copyProperties(endDate, date);
        }
        return date;
    }

    public void setEndDate(Date endDate) {
        Date date = new Date();
        if (!ObjectUtils.isEmpty(endDate)) {
            BeanUtils.copyProperties(endDate,date);
        }
        this.endDate = date;
    }

    public Integer getPassScore() {
        return passScore;
    }

    public void setPassScore(Integer passScore) {
        this.passScore = passScore;
    }

    @Override
    public String toString() {
        return "ExamPaperSaveReq{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", totalScore=" + totalScore +
                ", passScore=" + passScore +
                ", subjectId=" + subjectId +
                ", courseId=" + courseId +
                ", sort=" + sort +
                ", joinCount=" + joinCount +
                ", examTime=" + examTime +
                ", checkCount=" + checkCount +
                ", status=" + status +
                ", questionCount=" + questionCount +
                ", autoCheck=" + autoCheck +
                ", remark='" + remark + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
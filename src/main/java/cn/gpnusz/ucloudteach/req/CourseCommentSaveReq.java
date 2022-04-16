package cn.gpnusz.ucloudteach.req;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author h0ss
 * @description 封装课程评论的保存信息
 * @date 2021/11/17 22:08
 */

public class CourseCommentSaveReq {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @NotBlank(message = "【评论内容】不能为空")
    private String content;

    @JsonSerialize(using = ToStringSerializer.class)
    @NotNull(message = "【课程id】不能为空")
    private Long courseId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long studentId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long replyId;

    private Boolean eliteFlag;

    private Boolean topFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getReplyId() {
        return replyId;
    }

    public void setReplyId(Long replyId) {
        this.replyId = replyId;
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

    public Boolean getTopFlag() {
        return topFlag;
    }

    public void setTopFlag(Boolean topFlag) {
        this.topFlag = topFlag;
    }

    public Boolean getEliteFlag() {
        return eliteFlag;
    }

    public void setEliteFlag(Boolean eliteFlag) {
        this.eliteFlag = eliteFlag;
    }

    @Override
    public String toString() {
        return "CourseCommentSaveReq{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", courseId=" + courseId +
                ", studentId=" + studentId +
                ", replyId=" + replyId +
                ", eliteFlag=" + eliteFlag +
                ", topFlag=" + topFlag +
                '}';
    }
}
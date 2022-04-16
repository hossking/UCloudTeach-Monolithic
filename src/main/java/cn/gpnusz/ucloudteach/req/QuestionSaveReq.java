package cn.gpnusz.ucloudteach.req;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import javax.validation.constraints.NotNull;

/**
 * @author h0ss
 * @description 封装保存的题目信息
 * @date 2021/11/18 16:37
 */

public class QuestionSaveReq {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @JsonSerialize(using = ToStringSerializer.class)
    @NotNull(message = "【课程ID】不能为空")
    private Long courseId;

    @JsonSerialize(using = ToStringSerializer.class)
    @NotNull(message = "【科目ID】不能为空")
    private Long subjectId;

    @JsonSerialize(using = ToStringSerializer.class)
    @NotNull(message = "【章节ID】不能为空")
    private Long sectionId;

    @NotNull(message = "【题目类型】不能为空")
    private Byte type;

    @NotNull(message = "【题干内容】不能为空")
    private String content;

    private String answerText;

    private String analysis;

    private String options;

    private String answerOption;

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

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getAnswerOption() {
        return answerOption;
    }

    public void setAnswerOption(String answerOption) {
        this.answerOption = answerOption;
    }

    @Override
    public String toString() {
        return "QuestionSaveReq{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", subjectId=" + subjectId +
                ", sectionId=" + sectionId +
                ", type=" + type +
                ", content='" + content + '\'' +
                ", answerText='" + answerText + '\'' +
                ", analysis='" + analysis + '\'' +
                ", options='" + options + '\'' +
                ", answerOption='" + answerOption + '\'' +
                '}';
    }
}
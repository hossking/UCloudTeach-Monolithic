package cn.gpnusz.ucloudteach.resp;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;


/**
 * @author h0ss
 * @description 用户获取考试试卷信息的实体
 * @date 2021/12/2 - 2:15
 */
public class UserExamDetailResp {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long questionId;

    private Byte type;

    private String content;

    private String options;

    private String answerText;

    private String answerOption;

    private String analysis;

    private Integer sort;

    private Integer score;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
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

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public String getAnswerOption() {
        return answerOption;
    }

    public void setAnswerOption(String answerOption) {
        this.answerOption = answerOption;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "UserExamDetailResp{" +
                "id=" + id +
                ", questionId=" + questionId +
                ", type=" + type +
                ", content='" + content + '\'' +
                ", options='" + options + '\'' +
                ", answerText='" + answerText + '\'' +
                ", answerOption='" + answerOption + '\'' +
                ", analysis='" + analysis + '\'' +
                ", sort=" + sort +
                ", score=" + score +
                '}';
    }
}

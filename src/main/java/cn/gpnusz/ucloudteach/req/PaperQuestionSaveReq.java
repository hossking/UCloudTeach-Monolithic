package cn.gpnusz.ucloudteach.req;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * @description 封装题目试卷关联的保存信息
 * @author h0ss
 * @date 2021/11/19 20:14
 */

public class PaperQuestionSaveReq {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long paperId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long questionId;

    private Integer sort;

    private Integer score;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
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
        return "PaperQuestionSaveReq{" +
                "id=" + id +
                ", paperId=" + paperId +
                ", questionId=" + questionId +
                ", sort=" + sort +
                ", score=" + score +
                '}';
    }
}
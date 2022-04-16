package cn.gpnusz.ucloudteach.resp;

import cn.gpnusz.ucloudteach.entity.Question;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;

import java.util.Date;

/**
 * @author h0ss
 * @description 封装试卷对应试题信息的类
 * @date 2021/11/20 - 14:50
 */
public class QuestionCust  extends Question {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long questionId;

    private Integer sort;

    private Integer score;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date addTime;

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Date getAddTime() {
        Date date = new Date();
        if (!ObjectUtils.isEmpty(addTime)) {
            BeanUtils.copyProperties(addTime, date);
        }
        return date;
    }

    public void setAddTime(Date addTime) {
        Date date = new Date();
        if (!ObjectUtils.isEmpty(addTime)) {
            BeanUtils.copyProperties(addTime, date);
        }
        this.addTime = date;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "QuestionCust{" +
                "questionId=" + questionId +
                ", sort=" + sort +
                ", score=" + score +
                ", addTime=" + addTime +
                "} " + super.toString();
    }
}

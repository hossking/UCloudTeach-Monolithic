package cn.gpnusz.ucloudteach.resp;

import cn.gpnusz.ucloudteach.entity.ExamInfo;

/**
 * @author h0ss
 * @description 用户考试信息响应实体
 * @date 2021/11/30 - 12:56
 */
public class UserExamResp extends ExamInfo {
    private String name;

    private Integer totalScore;

    private Integer questionCount;

    private Integer passScore;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }


    public Integer getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(Integer questionCount) {
        this.questionCount = questionCount;
    }

    public Integer getPassScore() {
        return passScore;
    }

    public void setPassScore(Integer passScore) {
        this.passScore = passScore;
    }

    @Override
    public String toString() {
        return "UserExamResp{" +
                "name='" + name + '\'' +
                ", totalScore=" + totalScore +
                ", questionCount=" + questionCount +
                ", passScore=" + passScore +
                "} " + super.toString();
    }
}

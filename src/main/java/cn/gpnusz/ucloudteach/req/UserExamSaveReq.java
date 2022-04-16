package cn.gpnusz.ucloudteach.req;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import javax.validation.constraints.NotNull;

/**
 * @author h0ss
 * @description 学员提交保存考试作答信息
 * @date 2021/12/2 - 20:39
 */
public class UserExamSaveReq {

    @NotNull(message = "【试卷id】不能为空")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long paperId;

    @NotNull(message = "【作答信息】不能为空")
    private String studentAnswer;


    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }

    public String getStudentAnswer() {
        return studentAnswer;
    }

    public void setStudentAnswer(String studentAnswer) {
        this.studentAnswer = studentAnswer;
    }

    @Override
    public String toString() {
        return "UserExamSaveReq{" +
                "paperId=" + paperId +
                ", studentAnswer='" + studentAnswer + '\'' +
                '}';
    }
}

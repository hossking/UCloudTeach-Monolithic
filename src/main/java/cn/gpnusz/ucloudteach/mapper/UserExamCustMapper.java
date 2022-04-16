package cn.gpnusz.ucloudteach.mapper;

import cn.gpnusz.ucloudteach.resp.UserExamDetailResp;
import cn.gpnusz.ucloudteach.resp.UserExamResp;

import java.util.List;

/**
 * @author h0ss
 * @description 用户获取考试记录的mapper
 * @date 2021/11/30 - 12:56
 */
public interface UserExamCustMapper {
    /**
     * 根据学员id获取考试记录
     *
     * @param studentId : 学员id
     * @return : java.util.List<cn.gpnusz.ucloudteach.resp.UserExamCustResp>
     * @author h0ss
     */
    List<UserExamResp> selectExamInfo(Long studentId);

    /**
     * 用户获取考试试卷的题目信息
     *
     * @param id : 试卷id
     * @return : java.util.List<cn.gpnusz.ucloudteach.resp.UserExamDetailResp>
     * @author h0ss
     */
    List<UserExamDetailResp> getExamDetail(Long id);

    /**
     * 用户获取练习试卷的题目信息
     *
     * @param id : 试卷id
     * @return : java.util.List<cn.gpnusz.ucloudteach.resp.UserExamDetailResp>
     * @author h0ss
     */
    List<UserExamDetailResp> getExerciseDetail(Long id);

    /**
     * 学员查询考试结果
     * @param studentId : 学员id
     * @param paperId : 试卷id
     * @return : cn.gpnusz.ucloudteach.resp.UserExamResp
     * @author h0ss
     */
    UserExamResp getExamRes(Long studentId, Long paperId);
}

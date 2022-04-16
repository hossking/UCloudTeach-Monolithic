package cn.gpnusz.ucloudteach.mapper;

import cn.gpnusz.ucloudteach.resp.ExamCust;

import java.util.List;

/**
 * @description 自定义的考试信息mapper
 * @author h0ss
 * @date 2021/11/20 17:12
 */
public interface ExamCustMapper {
    /**
     * 查询考试完整信息的mapper接口
     * @return : java.util.List<cn.gpnusz.ucloudteach.resp.ExamCust>
     * @author h0ss
     */
    List<ExamCust> selectExamInfos();
}
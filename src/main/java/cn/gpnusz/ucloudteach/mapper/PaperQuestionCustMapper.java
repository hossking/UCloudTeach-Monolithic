package cn.gpnusz.ucloudteach.mapper;

import cn.gpnusz.ucloudteach.resp.QuestionCust;

import java.util.List;

/**
 * @author h0ss
 * @description 关于试卷试题信息的自定义查询接口
 * @date 2021/11/19 23:23
 */
public interface PaperQuestionCustMapper {

    /**
     * 根据传入的试卷id查询试题集合
     *
     * @param id : 试卷id
     * @return : java.util.List<cn.gpnusz.ucloudteach.resp.QuestionCust>
     * @author h0ss
     */
    List<QuestionCust> selectQuestionByPaperId(Long id);
}
package cn.gpnusz.ucloudteach.mapper;

import cn.gpnusz.ucloudteach.entity.ExamPaper;
import cn.gpnusz.ucloudteach.entity.ExamPaperExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ExamPaperMapper {
    long countByExample(ExamPaperExample example);

    int deleteByExample(ExamPaperExample example);

    int insert(ExamPaper record);

    int insertSelective(ExamPaper record);

    List<ExamPaper> selectByExample(ExamPaperExample example);

    int updateByExampleSelective(@Param("record") ExamPaper record, @Param("example") ExamPaperExample example);

    int updateByExample(@Param("record") ExamPaper record, @Param("example") ExamPaperExample example);
}
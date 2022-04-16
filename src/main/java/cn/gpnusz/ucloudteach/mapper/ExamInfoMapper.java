package cn.gpnusz.ucloudteach.mapper;

import cn.gpnusz.ucloudteach.entity.ExamInfo;
import cn.gpnusz.ucloudteach.entity.ExamInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ExamInfoMapper {
    long countByExample(ExamInfoExample example);

    int deleteByExample(ExamInfoExample example);

    int insert(ExamInfo record);

    int insertSelective(ExamInfo record);

    List<ExamInfo> selectByExampleWithBLOBs(ExamInfoExample example);

    List<ExamInfo> selectByExample(ExamInfoExample example);

    int updateByExampleSelective(@Param("record") ExamInfo record, @Param("example") ExamInfoExample example);

    int updateByExampleWithBLOBs(@Param("record") ExamInfo record, @Param("example") ExamInfoExample example);

    int updateByExample(@Param("record") ExamInfo record, @Param("example") ExamInfoExample example);
}
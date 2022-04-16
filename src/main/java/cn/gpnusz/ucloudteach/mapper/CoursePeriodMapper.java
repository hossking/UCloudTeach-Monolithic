package cn.gpnusz.ucloudteach.mapper;

import cn.gpnusz.ucloudteach.entity.CoursePeriod;
import cn.gpnusz.ucloudteach.entity.CoursePeriodExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CoursePeriodMapper {
    long countByExample(CoursePeriodExample example);

    int deleteByExample(CoursePeriodExample example);

    int insert(CoursePeriod record);

    int insertSelective(CoursePeriod record);

    List<CoursePeriod> selectByExampleWithBLOBs(CoursePeriodExample example);

    List<CoursePeriod> selectByExample(CoursePeriodExample example);

    int updateByExampleSelective(@Param("record") CoursePeriod record, @Param("example") CoursePeriodExample example);

    int updateByExampleWithBLOBs(@Param("record") CoursePeriod record, @Param("example") CoursePeriodExample example);

    int updateByExample(@Param("record") CoursePeriod record, @Param("example") CoursePeriodExample example);
}
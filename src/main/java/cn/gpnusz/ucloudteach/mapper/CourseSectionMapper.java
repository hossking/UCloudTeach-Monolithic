package cn.gpnusz.ucloudteach.mapper;

import cn.gpnusz.ucloudteach.entity.CourseSection;
import cn.gpnusz.ucloudteach.entity.CourseSectionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CourseSectionMapper {
    long countByExample(CourseSectionExample example);

    int deleteByExample(CourseSectionExample example);

    int insert(CourseSection record);

    int insertSelective(CourseSection record);

    List<CourseSection> selectByExample(CourseSectionExample example);

    int updateByExampleSelective(@Param("record") CourseSection record, @Param("example") CourseSectionExample example);

    int updateByExample(@Param("record") CourseSection record, @Param("example") CourseSectionExample example);
}
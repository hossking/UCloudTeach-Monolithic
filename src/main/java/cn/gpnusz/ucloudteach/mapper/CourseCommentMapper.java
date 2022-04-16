package cn.gpnusz.ucloudteach.mapper;

import cn.gpnusz.ucloudteach.entity.CourseComment;
import cn.gpnusz.ucloudteach.entity.CourseCommentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CourseCommentMapper {
    long countByExample(CourseCommentExample example);

    int deleteByExample(CourseCommentExample example);

    int insert(CourseComment record);

    int insertSelective(CourseComment record);

    List<CourseComment> selectByExampleWithBLOBs(CourseCommentExample example);

    List<CourseComment> selectByExample(CourseCommentExample example);

    int updateByExampleSelective(@Param("record") CourseComment record, @Param("example") CourseCommentExample example);

    int updateByExampleWithBLOBs(@Param("record") CourseComment record, @Param("example") CourseCommentExample example);

    int updateByExample(@Param("record") CourseComment record, @Param("example") CourseCommentExample example);
}
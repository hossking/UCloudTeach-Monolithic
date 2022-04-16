package cn.gpnusz.ucloudteach.mapper;

import cn.gpnusz.ucloudteach.entity.CourseMember;
import cn.gpnusz.ucloudteach.entity.CourseMemberExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CourseMemberMapper {
    long countByExample(CourseMemberExample example);

    int deleteByExample(CourseMemberExample example);

    int insert(CourseMember record);

    int insertSelective(CourseMember record);

    List<CourseMember> selectByExample(CourseMemberExample example);

    int updateByExampleSelective(@Param("record") CourseMember record, @Param("example") CourseMemberExample example);

    int updateByExample(@Param("record") CourseMember record, @Param("example") CourseMemberExample example);
}
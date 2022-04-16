package cn.gpnusz.ucloudteach.mapper;

import cn.gpnusz.ucloudteach.entity.StudyRecord;
import cn.gpnusz.ucloudteach.entity.StudyRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StudyRecordMapper {
    long countByExample(StudyRecordExample example);

    int deleteByExample(StudyRecordExample example);

    int insert(StudyRecord record);

    int insertSelective(StudyRecord record);

    List<StudyRecord> selectByExample(StudyRecordExample example);

    int updateByExampleSelective(@Param("record") StudyRecord record, @Param("example") StudyRecordExample example);

    int updateByExample(@Param("record") StudyRecord record, @Param("example") StudyRecordExample example);
}
package cn.gpnusz.ucloudteach.mapper;

import cn.gpnusz.ucloudteach.entity.Snapshot;
import cn.gpnusz.ucloudteach.entity.SnapshotExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SnapshotMapper {
    long countByExample(SnapshotExample example);

    int deleteByExample(SnapshotExample example);

    int insert(Snapshot record);

    int insertSelective(Snapshot record);

    List<Snapshot> selectByExample(SnapshotExample example);

    int updateByExampleSelective(@Param("record") Snapshot record, @Param("example") SnapshotExample example);

    int updateByExample(@Param("record") Snapshot record, @Param("example") SnapshotExample example);
}
package cn.gpnusz.ucloudteach.mapper;

import cn.gpnusz.ucloudteach.entity.GridConfig;
import cn.gpnusz.ucloudteach.entity.GridConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GridConfigMapper {
    long countByExample(GridConfigExample example);

    int deleteByExample(GridConfigExample example);

    int insert(GridConfig record);

    int insertSelective(GridConfig record);

    List<GridConfig> selectByExample(GridConfigExample example);

    int updateByExampleSelective(@Param("record") GridConfig record, @Param("example") GridConfigExample example);

    int updateByExample(@Param("record") GridConfig record, @Param("example") GridConfigExample example);
}
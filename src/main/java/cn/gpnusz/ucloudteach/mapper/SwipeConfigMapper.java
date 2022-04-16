package cn.gpnusz.ucloudteach.mapper;

import cn.gpnusz.ucloudteach.entity.SwipeConfig;
import cn.gpnusz.ucloudteach.entity.SwipeConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SwipeConfigMapper {
    long countByExample(SwipeConfigExample example);

    int deleteByExample(SwipeConfigExample example);

    int insert(SwipeConfig record);

    int insertSelective(SwipeConfig record);

    List<SwipeConfig> selectByExample(SwipeConfigExample example);

    int updateByExampleSelective(@Param("record") SwipeConfig record, @Param("example") SwipeConfigExample example);

    int updateByExample(@Param("record") SwipeConfig record, @Param("example") SwipeConfigExample example);
}
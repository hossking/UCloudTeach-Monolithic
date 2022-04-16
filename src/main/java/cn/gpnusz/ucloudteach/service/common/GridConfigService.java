package cn.gpnusz.ucloudteach.service.common;

import cn.gpnusz.ucloudteach.entity.GridConfig;
import cn.gpnusz.ucloudteach.entity.GridConfigExample;
import cn.gpnusz.ucloudteach.mapper.GridConfigMapper;
import cn.gpnusz.ucloudteach.req.GridConfigSaveReq;
import cn.gpnusz.ucloudteach.util.SnowFlake;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author h0ss
 * @description 操作菜单项的业务层
 * @date 2021/11/21 14:57
 */
@Service
public class GridConfigService {
    @Resource
    private GridConfigMapper gridConfigMapper;

    @Resource
    private SnowFlake snowFlake;


    /**
     * 获取全部菜单项配置的业务方法
     *
     * @return : java.util.List<cn.gpnusz.ucloudteach.entity.GridConfig>
     * @author h0ss
     */
    public List<GridConfig> getAll() {
        // 按顺序输出
        GridConfigExample gridConfigExample = new GridConfigExample();
        gridConfigExample.setOrderByClause("sort");
        return gridConfigMapper.selectByExample(gridConfigExample);
    }

    /**
     * 保存菜单项配置的业务方法
     *
     * @param gridConfigSaveReq : 保存的对象
     * @author h0ss
     */
    public void save(GridConfigSaveReq gridConfigSaveReq) {
        GridConfig gridConfig = new GridConfig();
        BeanUtils.copyProperties(gridConfigSaveReq, gridConfig);
        if (gridConfig.getId() != null) {
            GridConfigExample gridConfigExample = new GridConfigExample();
            GridConfigExample.Criteria criteria = gridConfigExample.createCriteria();
            criteria.andIdEqualTo(gridConfig.getId());
            gridConfigMapper.updateByExampleSelective(gridConfig, gridConfigExample);
        } else {
            // 雪花算法生成id
            gridConfig.setId(snowFlake.nextId());
            gridConfigMapper.insert(gridConfig);
        }
    }

    /**
     * 根据id删除菜单项配置信息的业务方法
     *
     * @param id : 菜单项配置id
     * @author h0ss
     */
    public void delete(Long id) {
        if (id != null) {
            GridConfigExample gridConfigExample = new GridConfigExample();
            GridConfigExample.Criteria criteria = gridConfigExample.createCriteria();
            criteria.andIdEqualTo(id);
            gridConfigMapper.deleteByExample(gridConfigExample);
        }
    }
}
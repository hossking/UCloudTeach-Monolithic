package cn.gpnusz.ucloudteach.service.common;

import cn.gpnusz.ucloudteach.entity.SwipeConfig;
import cn.gpnusz.ucloudteach.entity.SwipeConfigExample;
import cn.gpnusz.ucloudteach.mapper.SwipeConfigMapper;
import cn.gpnusz.ucloudteach.req.SwipeConfigSaveReq;
import cn.gpnusz.ucloudteach.util.SnowFlake;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author h0ss
 * @description 操作轮播图配置的业务层
 * @date 2021/11/21 14:55
 */
@Service
public class SwipeConfigService {
    @Resource
    private SwipeConfigMapper swipeConfigMapper;

    @Resource
    private SnowFlake snowFlake;


    /**
     * 获取全部轮播图配置的业务方法
     *
     * @return : java.util.List<cn.gpnusz.ucloudteach.entity.SwipeConfig>
     * @author h0ss
     */
    public List<SwipeConfig> getAll() {
        // 按顺序输出
        SwipeConfigExample swipeConfigExample = new SwipeConfigExample();
        swipeConfigExample.setOrderByClause("sort");
        return swipeConfigMapper.selectByExample(swipeConfigExample);
    }

    /**
     * 保存轮播图配置的业务方法
     *
     * @param swipeConfigSaveReq : 保存的对象
     * @author h0ss
     */
    public void save(SwipeConfigSaveReq swipeConfigSaveReq) {
        SwipeConfig swipeConfig = new SwipeConfig();
        BeanUtils.copyProperties(swipeConfigSaveReq, swipeConfig);
        if (swipeConfig.getId() != null) {
            SwipeConfigExample swipeConfigExample = new SwipeConfigExample();
            SwipeConfigExample.Criteria criteria = swipeConfigExample.createCriteria();
            criteria.andIdEqualTo(swipeConfig.getId());
            swipeConfigMapper.updateByExampleSelective(swipeConfig, swipeConfigExample);
        } else {
            // 雪花算法生成id
            swipeConfig.setId(snowFlake.nextId());
            swipeConfigMapper.insert(swipeConfig);
        }
    }

    /**
     * 根据id删除轮播图配置信息的业务方法
     *
     * @param id : 轮播图配置id
     * @author h0ss
     */
    public void delete(Long id) {
        if (id != null) {
            SwipeConfigExample swipeConfigExample = new SwipeConfigExample();
            SwipeConfigExample.Criteria criteria = swipeConfigExample.createCriteria();
            criteria.andIdEqualTo(id);
            swipeConfigMapper.deleteByExample(swipeConfigExample);
        }
    }
}
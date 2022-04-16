package cn.gpnusz.ucloudteach.controller.common;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.gpnusz.ucloudteach.common.CommonResp;
import cn.gpnusz.ucloudteach.entity.SwipeConfig;
import cn.gpnusz.ucloudteach.req.SwipeConfigSaveReq;
import cn.gpnusz.ucloudteach.service.common.SwipeConfigService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author h0ss
 * @description 操作轮播图配置的接口
 * @date 2021/11/21 - 15:07
 */

@RestController
@RequestMapping("/api/common/swipe")
public class SwipeConfigController {
    @Resource
    private SwipeConfigService swipeConfigService;

    /**
     * 获取轮播图配置信息的接口
     *
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<java.util.List<cn.gpnusz.ucloudteach.entity.SwipeConfig>>
     * @author h0ss
     */
    @GetMapping("/list")
    public CommonResp<List<SwipeConfig>> getAll() {
        CommonResp<List<SwipeConfig>> resp = new CommonResp<>();
        resp.setMessage("数据获取成功!");
        resp.setContent(swipeConfigService.getAll());
        return resp;
    }

    /**
     * 保存轮播图配置信息的接口
     *
     * @param swipeConfigSaveReq : 保存的对象
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<java.lang.Object>
     * @author h0ss
     */
    @SaCheckRole("admin")
    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody SwipeConfigSaveReq swipeConfigSaveReq) {
        swipeConfigService.save(swipeConfigSaveReq);
        return new CommonResp<>();
    }

    /**
     * 根据id删除轮播图的接口
     *
     * @param id : 轮播图id
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<java.lang.Object>
     * @author h0ss
     */
    @SaCheckRole("admin")
    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id) {
        swipeConfigService.delete(id);
        return new CommonResp<>();
    }
}
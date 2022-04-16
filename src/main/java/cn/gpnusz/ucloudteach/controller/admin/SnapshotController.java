package cn.gpnusz.ucloudteach.controller.admin;

import cn.gpnusz.ucloudteach.common.CommonResp;
import cn.gpnusz.ucloudteach.entity.Snapshot;
import cn.gpnusz.ucloudteach.service.common.SnapshotService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author h0ss
 * @description
 * @date 2021/12/4 - 18:57
 */
@RestController
@RequestMapping("/api/admin/snapshot")
public class SnapshotController {

    @Resource
    private SnapshotService snapshotService;

    @GetMapping("/get")
    public CommonResp<Snapshot> get() {
        CommonResp<Snapshot> resp = new CommonResp<>();
        resp.setContent(snapshotService.getData());
        resp.setMessage("数据获取成功");
        return resp;
    }

    @GetMapping("/before/get")
    public CommonResp<List<Snapshot>> getBeforeData() {
        CommonResp<List<Snapshot>> resp = new CommonResp<>();
        resp.setContent(snapshotService.getBeforeData());
        resp.setMessage("数据获取成功");
        return resp;
    }
}

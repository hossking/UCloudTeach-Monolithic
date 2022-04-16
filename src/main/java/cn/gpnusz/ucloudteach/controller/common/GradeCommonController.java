package cn.gpnusz.ucloudteach.controller.common;

import cn.gpnusz.ucloudteach.common.CommonResp;
import cn.gpnusz.ucloudteach.entity.Grade;
import cn.gpnusz.ucloudteach.service.course.GradeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author h0ss
 * @description 公共接口获取年级信息
 * @date 2021/11/24 - 8:44
 */
@RestController
@RequestMapping("/api/common/grade")
public class GradeCommonController {

    @Resource
    private GradeService gradeService;

    /**
     * 获取年级信息的接口
     *
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<java.util.List<cn.gpnusz.ucloudteach.entity.Grade>>
     * @author h0ss
     */
    @GetMapping("/list")
    public CommonResp<List<Grade>> list() {
        CommonResp<List<Grade>> resp = new CommonResp<>();
        resp.setMessage("数据获取成功!");
        resp.setContent(gradeService.getAll());
        return resp;
    }

}

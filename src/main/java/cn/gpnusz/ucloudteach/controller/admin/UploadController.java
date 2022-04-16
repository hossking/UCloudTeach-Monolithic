package cn.gpnusz.ucloudteach.controller.admin;

import cn.gpnusz.ucloudteach.common.CommonResp;
import cn.gpnusz.ucloudteach.service.common.UploadService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author h0ss
 * @description 上传接口
 * @date 2021/11/15 14:57
 */

@RestController
@RequestMapping("/api/admin/upload")
public class UploadController {

    @Resource
    private UploadService uploadService;

    /**
     * 上传图片的接口
     * @param file : 上传的图片文件
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<java.lang.Object>
     * @author h0ss
     */
    @PostMapping(value = "/upload-pic")
    public CommonResp<Object> fileUpload(@RequestParam(value = "file") MultipartFile file) {
        CommonResp<Object> resp = new CommonResp<>();
        resp.setContent(uploadService.uploadPic(file));
        return resp;
    }

    @PostMapping(value = "/upload-video")
    public CommonResp<Object> uploadVideo(@RequestParam(value = "file") MultipartFile file) {
        CommonResp<Object> resp = new CommonResp<>();
        resp.setContent(uploadService.uploadVideo(file));
        return resp;
    }
}

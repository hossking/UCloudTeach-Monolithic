package cn.gpnusz.ucloudteach.service.common;

import cn.gpnusz.ucloudteach.exception.BusinessException;
import cn.gpnusz.ucloudteach.exception.BusinessExceptionCode;
import cn.gpnusz.ucloudteach.util.UploadUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author h0ss
 * @description 上传文件的业务层
 * @date 2021/11/15 - 14:37
 */
@Service
public class UploadService {
    @Resource
    private UploadUtils uploadUtils;

    /**
     * 魔法值
     */
    private final static String[] TYPEVIDEO = {"0000002066747970","52494646d07d6007","464c560105000000"};
    private final String[] TYPEPIC = {"89504e470d0a1a0a0000","ffd8ffe000104a464946"};
    /**
     * 用于上传图片的业务方法
     *
     * @param file : 需要上传的图片文件
     * @return : java.lang.String
     * @author h0ss
     */
    public String uploadPic(MultipartFile file) {
        return uploadService(file,TYPEPIC);
    }

    /**
     * 用于上传视频的业务方法
     *
     * @param file : 需要上传的视频文件
     * @return : java.lang.String
     * @author h0ss
     */
    public String uploadVideo(MultipartFile file) {
        return uploadService(file, TYPEVIDEO);
    }

    /**
     * 根据传入的文件和魔法值去执行上传方法
     * @param file : 文件
     * @param types : 类型魔法数列表
     * @return : java.lang.String
     * @author h0ss
     */
    public String uploadService(MultipartFile file, String[] types){
        try {
            String res = uploadUtils.upload(file,types);
            if (res != null) {
                return res;
            } else {
                throw new BusinessException(BusinessExceptionCode.UPLOAD_FAIL);
            }
        } catch (IOException e) {
            throw new BusinessException(BusinessExceptionCode.UPLOAD_FAIL);
        }
    }
}

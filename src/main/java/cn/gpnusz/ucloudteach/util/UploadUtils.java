package cn.gpnusz.ucloudteach.util;

import com.obs.services.ObsClient;
import com.obs.services.ObsConfiguration;
import com.obs.services.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * @author h0ss
 * @description
 * @date 2021/11/15 - 13:37
 */

@Component
public class UploadUtils {
    @Value("${obs.endPoint}")
    private String endPoint;

    @Value("${obs.ak}")
    private String ak;

    @Value("${obs.sk}")
    private String sk;

    @Value("${obs.socketTimeout}")
    private Integer socketTimeout;

    @Value("${obs.connectionTimeout}")
    private Integer connectionTimeout;

    /**
     * 上传文件的工具方法
     *
     * @param file  : 文件
     * @param types : 限制的类型
     * @return : java.lang.String
     * @author h0ss
     */
    public String upload(MultipartFile file, String[] types) throws IOException {
        byte[] fileBytes = file.getBytes();
        boolean checkFlag = false;
        // 通过文件头魔法值判断文件类型
        for (String type : types) {
            if (checkType(fileBytes, type, type.length())) {
                checkFlag = true;
                break;
            }
        }
        if (file.isEmpty() || !checkFlag) {
            return null;
        }
        ObsConfiguration config = new ObsConfiguration();
        config.setSocketTimeout(socketTimeout);
        config.setConnectionTimeout(connectionTimeout);
        config.setEndPoint(endPoint);
        // 创建ObsClient实例
        try (ObsClient obsClient = new ObsClient(ak, sk, config)) {
            // 使用访问OBS
            String fileName = file.getOriginalFilename();
            if (fileName != null) {
                String fileMd5 = DigestUtils.md5DigestAsHex(fileBytes);
                String realFileName = fileMd5 + fileName.substring(fileName.lastIndexOf('.'));
                PutObjectResult res = obsClient.putObject("ucloudteach", realFileName, new ByteArrayInputStream(fileBytes));
                return res.getObjectUrl();
            }
        }
        return null;
    }

    /**
     * 根据魔法值检查文件格式
     *
     * @param src  : 文件流
     * @param type : 类型魔法值
     * @param len : 检验长度
     * @return : boolean
     * @author h0ss
     */
    public boolean checkType(byte[] src, String type, Integer len) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return false;
        }
        for (byte b : src) {
            int v = b & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
            if (stringBuilder.length()==len) {
                break;
            }
        }
        return type.equals(stringBuilder.toString());
    }
}

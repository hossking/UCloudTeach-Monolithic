package cn.gpnusz.ucloudteach.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author h0ss
 * @description 支付宝支付功能相关配置
 * @date 2021/11/29 13:28
 */

@Configuration
@PropertySource("classpath:ali-application.yml")
@Component
public class AlipayConfig {

    /**
     * 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
     */
    @Value("${appId}")
    public String appId;

    /**
     * 商户私钥，您的PKCS8格式RSA2私钥
     */
    @Value("${privateKey}")
    public String privateKey;

    /**
     * 支付宝公钥,
     */
    @Value("${publicKey}")
    public String publicKey;

    /**
     * 服务器异步通知页面路径需http://格式的完整路径，不能加?id=123这类自定义参数
     */
    @Value("${notifyUrl}")
    public String notifyUrl;

    /**
     * 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数
     */
    @Value("${returnUrl}")
    public String returnUrl;

    /**
     * 签名方式
     */
    @Value("${signType}")
    public String signType;

    /**
     * 字符编码格式
     */
    @Value("${charset}")
    public String charset;

    /**
     * 支付宝网关
     */
    @Value("${gatewayUrl}")
    public String gatewayUrl;

    /**
     * 日志记录目录
     */
    @Value("${logPath}")
    public String logPath;
    
}

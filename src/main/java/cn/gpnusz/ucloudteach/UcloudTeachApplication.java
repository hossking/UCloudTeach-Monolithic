package cn.gpnusz.ucloudteach;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.TimeZone;

/**
 * @author h0ss
 * @description 启动类
 * @date 2021/11/10 14:42
 */

@SpringBootApplication
@MapperScan("cn.gpnusz.ucloudteach.mapper")
@EnableScheduling
public class UcloudTeachApplication {

    private static final Logger LOG = LoggerFactory.getLogger(UcloudTeachApplication.class);

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        SpringApplication app = new SpringApplication(UcloudTeachApplication.class);
        Environment env = app.run(args).getEnvironment();
        LOG.info("启动成功！！");
        LOG.info("地址: \thttp://127.0.0.1:{}", env.getProperty("server.port"));
    }
}

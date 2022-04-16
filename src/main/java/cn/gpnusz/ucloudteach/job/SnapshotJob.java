package cn.gpnusz.ucloudteach.job;

import cn.gpnusz.ucloudteach.service.common.SnapshotService;
import cn.gpnusz.ucloudteach.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author h0ss
 * @description
 * @date 2021/12/4 - 18:23
 */
@Component
public class SnapshotJob {
    private static final Logger LOG = LoggerFactory.getLogger(SnapshotJob.class);

    @Resource
    private SnapshotService snapshotService;

    @Resource
    private SnowFlake snowFlake;

    /**
     * 生成快照
     */
    @Scheduled(cron = "0 0 4 1/1 * ? ")
    public void createSnapshotJob() {
        // 设置日志流水号
        MDC.put("LOG_ID", String.valueOf(snowFlake.nextId()));
        LOG.info("生成快照定时任务开始");
        long start = System.currentTimeMillis();
        snapshotService.generateSnapshot();
        LOG.info("今日快照生成完毕，耗时 {} 毫秒", System.currentTimeMillis() - start);
    }
}

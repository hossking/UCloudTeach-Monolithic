package cn.gpnusz.ucloudteach.service.common;

import cn.gpnusz.ucloudteach.entity.Snapshot;
import cn.gpnusz.ucloudteach.entity.SnapshotExample;
import cn.gpnusz.ucloudteach.mapper.*;
import cn.gpnusz.ucloudteach.util.SnowFlake;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author h0ss
 * @description 快照数据业务层
 * @date 2021/12/4 - 18:06
 */
@Service
public class SnapshotService {

    @Resource
    private SnapshotMapper snapshotMapper;

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private StudentInfoMapper studentInfoMapper;

    @Resource
    private ExamPaperMapper examPaperMapper;

    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private SnowFlake snowFlake;

    /**
     * 生成平台数据快照
     *
     * @author h0ss
     */
    public void generateSnapshot() {
        Snapshot snapshot = new Snapshot();
        long courseCount = courseMapper.countByExample(null);
        long studentCount = studentInfoMapper.countByExample(null);
        long paperCount = examPaperMapper.countByExample(null);
        long questionCount = questionMapper.countByExample(null);
        snapshot.setCourseCount((int) courseCount);
        snapshot.setStudentCount((int) studentCount);
        snapshot.setPaperCount((int) paperCount);
        snapshot.setQuestionCount((int) questionCount);
        snapshot.setCreateDate(new Date());
        snapshot.setId(snowFlake.nextId());
        snapshotMapper.insert(snapshot);
        stringRedisTemplate.opsForValue().set("snapshotData", JSON.toJSONString(snapshot), 1, TimeUnit.DAYS);
    }

    /**
     * 获取快照数据的业务方法
     *
     * @return : cn.gpnusz.ucloudteach.entity.Snapshot
     * @author h0ss
     */
    public Snapshot getData() {
        String data = stringRedisTemplate.opsForValue().get("snapshotData");
        if (data != null) {
            JSONObject obj = JSON.parseObject(data);
            if (!obj.isEmpty()) {
                Snapshot snapshot = new Snapshot();
                snapshot.setId(obj.getLong("id"));
                snapshot.setQuestionCount(obj.getInteger("questionCount"));
                snapshot.setPaperCount(obj.getInteger("paperCount"));
                snapshot.setStudentCount(obj.getInteger("studentCount"));
                snapshot.setCreateDate(obj.getDate("createDate"));
                snapshot.setCourseCount(obj.getInteger("courseCount"));
                return snapshot;
            }
        }
        return null;
    }

    /**
     * 获取前七天的数据
     *
     * @return : java.util.List<cn.gpnusz.ucloudteach.entity.Snapshot>
     * @author h0ss
     */
    public List<Snapshot> getBeforeData() {
        SnapshotExample example = new SnapshotExample();
        example.setOrderByClause("create_date desc");
        PageHelper.startPage(1, 15);
        return snapshotMapper.selectByExample(example);
    }
}

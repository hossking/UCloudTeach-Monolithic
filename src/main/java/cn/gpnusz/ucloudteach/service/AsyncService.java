package cn.gpnusz.ucloudteach.service;

import cn.gpnusz.ucloudteach.entity.CourseMember;
import cn.gpnusz.ucloudteach.entity.CourseMemberExample;
import cn.gpnusz.ucloudteach.entity.StudyRecord;
import cn.gpnusz.ucloudteach.entity.StudyRecordExample;
import cn.gpnusz.ucloudteach.mapper.CourseMemberMapper;
import cn.gpnusz.ucloudteach.mapper.StudyRecordMapper;
import cn.gpnusz.ucloudteach.service.exam.ExamInfoService;
import cn.gpnusz.ucloudteach.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author h0ss
 * @description 异步执行的业务
 * @date 2021/12/3 - 17:09
 */
@Service
@EnableAsync
public class AsyncService {

    @Resource
    private ExamInfoService examInfoService;

    @Resource
    private StudyRecordMapper studyRecordMapper;

    @Resource
    private CourseMemberMapper courseMemberMapper;

    @Resource
    private SnowFlake snowFlake;

    private static final Logger LOG = LoggerFactory.getLogger(AsyncService.class);

    /**
     * 异步执行批阅试卷
     *
     * @param userId  : 学员id
     * @param paperId : 试卷id
     * @author h0ss
     */
    public void startCheckExam(Long userId, Long paperId) {
        examInfoService.checkExam(userId, paperId);
    }

    /**
     * 异步写入学习记录
     *
     * @param userId   : 学员id
     * @param courseId : 课程id
     * @param periodId : 课时id
     * @author h0ss
     */
    @Async
    public void writeRecord(Long userId, Long courseId, Long periodId) {
        // 先查询是否写入过记录 只记录首次学习
        StudyRecordExample example = new StudyRecordExample();
        StudyRecordExample.Criteria criteria = example.createCriteria();
        criteria.andPeriodIdEqualTo(periodId);
        criteria.andStudentIdEqualTo(userId);
        // 记录数
        long recordCount = studyRecordMapper.countByExample(example);
        if (recordCount == 0) {
            LOG.info("开始写入学员 {} 在课程 {} 的学习记录",userId,courseId);
            // 写入记录表
            StudyRecord record = new StudyRecord();
            record.setId(snowFlake.nextId());
            record.setCourseId(courseId);
            record.setPeriodId(periodId);
            record.setStudyTime(new Date());
            record.setStudentId(userId);
            studyRecordMapper.insert(record);
            // 刷新课程成员表中的学习进度数据
            CourseMember courseMember = new CourseMember();
            CourseMemberExample courseMemberExample = new CourseMemberExample();
            CourseMemberExample.Criteria criteria1 = courseMemberExample.createCriteria();
            criteria1.andCourseIdEqualTo(courseId);
            criteria1.andStudentIdEqualTo(userId);
            // 对学习课程数进行修改
            List<CourseMember> list = courseMemberMapper.selectByExample(courseMemberExample);
            if (!list.isEmpty()) {
                courseMember.setFinishCourse(list.get(0).getFinishCourse() + 1);
                courseMemberMapper.updateByExampleSelective(courseMember, courseMemberExample);
            }
            LOG.info("学员 {} 在课程 {} 的学习记录写入成功",userId,courseId);
        }
    }
}

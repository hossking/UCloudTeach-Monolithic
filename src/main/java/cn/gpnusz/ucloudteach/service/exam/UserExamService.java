package cn.gpnusz.ucloudteach.service.exam;

import cn.dev33.satoken.stp.StpUtil;
import cn.gpnusz.ucloudteach.common.CommonResp;
import cn.gpnusz.ucloudteach.entity.ExamInfo;
import cn.gpnusz.ucloudteach.entity.ExamInfoExample;
import cn.gpnusz.ucloudteach.entity.ExamPaper;
import cn.gpnusz.ucloudteach.entity.ExamPaperExample;
import cn.gpnusz.ucloudteach.mapper.ExamInfoMapper;
import cn.gpnusz.ucloudteach.mapper.ExamPaperMapper;
import cn.gpnusz.ucloudteach.mapper.UserExamCustMapper;
import cn.gpnusz.ucloudteach.req.UserExamSaveReq;
import cn.gpnusz.ucloudteach.resp.UserExamDetailResp;
import cn.gpnusz.ucloudteach.resp.UserExamResp;
import cn.gpnusz.ucloudteach.service.AsyncService;
import cn.gpnusz.ucloudteach.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;

/**
 * @author h0ss
 * @description 用户操作考试信息业务层
 * @date 2021/11/28 3:27
 */
@Service
public class UserExamService {
    @Resource
    private UserExamCustMapper userExamCustMapper;

    @Resource
    private ExamInfoMapper examInfoMapper;

    @Resource
    private ExamPaperMapper examPaperMapper;

    @Resource
    private ExamPaperService examPaperService;

    @Resource
    private SnowFlake snowFlake;

    @Resource
    private AsyncService asyncService;

    private static final Logger LOG = LoggerFactory.getLogger(UserExamService.class);

    /**
     * 用户查询已参加考试信息的业务方法
     *
     * @return : java.util.List<cn.gpnusz.ucloudteach.resp.UserExamResp>
     * @author h0ss
     */
    public List<UserExamResp> selectUserExam() {
        // 获取当前会话的id
        Long userId = StpUtil.getLoginIdAsLong();
        return userExamCustMapper.selectExamInfo(userId);
    }

    /**
     * 用户查询考试结果的业务方法
     *
     * @param paperId : 试卷ID
     * @return : cn.gpnusz.ucloudteach.resp.UserExamResp
     * @author h0ss
     */
    public UserExamResp getExamRes(Long paperId) {
        // 获取当前会话的id
        Long userId = StpUtil.getLoginIdAsLong();
        return userExamCustMapper.getExamRes(userId, paperId);
    }

    /**
     * 获取考试的试题信息【不带答案】
     *
     * @param id : 试卷id
     * @return : java.util.List<cn.gpnusz.ucloudteach.resp.UserExamDetailResp>
     * @author h0ss
     */
    public List<UserExamDetailResp> getExamDetail(Long id) {
        // 检验参与状态以及是否过期
        if (checkExamJoinStatus(id) && checkValid(id)) {
            return userExamCustMapper.getExamDetail(id);
        } else {
            return null;
        }
    }

    /**
     * 获取练习的试题信息【带答案】
     *
     * @param id : 试卷id
     * @return : java.util.List<cn.gpnusz.ucloudteach.resp.UserExamDetailResp>
     * @author h0ss
     */
    public List<UserExamDetailResp> getExerciseDetail(Long id) {
        if (checkValid(id)) {
            return userExamCustMapper.getExerciseDetail(id);
        } else {
            return null;
        }
    }

    /**
     * 检查试卷是否已经过期
     *
     * @param paperId : 试卷id
     * @return : java.lang.Boolean
     * @author h0ss
     */
    public Boolean checkValid(Long paperId) {
        // 判断试卷是否过期
        ExamPaperExample example = new ExamPaperExample();
        ExamPaperExample.Criteria criteria = example.createCriteria();
        Date now = new Date();
        criteria.andIdEqualTo(paperId);
        criteria.andStartDateLessThanOrEqualTo(now);
        criteria.andEndDateGreaterThanOrEqualTo(now);
        return examPaperMapper.countByExample(example) != 0;
    }

    /**
     * 检查是否参加过考试
     *
     * @param paperId : 试卷id
     * @return : java.lang.Boolean
     * @author h0ss
     */
    public Boolean checkExamJoinStatus(Long paperId) {
        Long userId = StpUtil.getLoginIdAsLong();
        ExamInfoExample example = new ExamInfoExample();
        ExamInfoExample.Criteria criteria = example.createCriteria();
        criteria.andPaperIdEqualTo(paperId);
        criteria.andStudentIdEqualTo(userId);
        if (ObjectUtils.isEmpty(examInfoMapper.selectByExample(example))) {
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }
    }

    /**
     * 学员参与考试的业务方法
     *
     * @param paperId : 试卷id
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<java.lang.Object>
     * @author h0ss
     */
    public CommonResp<Object> joinExam(Long paperId) {
        CommonResp<Object> resp = new CommonResp<>();
        Boolean status = checkExamJoinStatus(paperId);
        if (status) {
            resp.setMessage("您已参加过本次考试");
            resp.setSuccess(false);
        } else {
            // 开始插入考试信息表
            Long userId = StpUtil.getLoginIdAsLong();
            ExamInfo examInfo = new ExamInfo();
            examInfo.setPaperId(paperId);
            examInfo.setStudentId(userId);
            examInfo.setCreateTime(new Date());
            examInfo.setId(snowFlake.nextId());
            examInfoMapper.insertSelective(examInfo);
            examPaperService.plusJoinCount(paperId);
            LOG.info("学员 {} 参加了考试", userId);
            resp.setMessage("成功参加考试");
        }
        return resp;
    }

    /**
     * 学员保存考试答案的业务方法
     *
     * @param userExamSaveReq : 作答信息
     * @author h0ss
     */
    public CommonResp<Object> submitExam(UserExamSaveReq userExamSaveReq) {
        CommonResp<Object> resp = new CommonResp<>();
        // 获取学员id
        Long userId = StpUtil.getLoginIdAsLong();
        // 创建一个考试信息对象
        ExamInfo examInfo = new ExamInfo();
        // 填充作答信息
        examInfo.setStudentAnswer(userExamSaveReq.getStudentAnswer());
        examInfo.setStatus(Boolean.TRUE);
        // 更新到数据库中 先查找是否已经提交过
        ExamInfoExample example = new ExamInfoExample();
        ExamInfoExample.Criteria criteria = example.createCriteria();
        // 因有唯一键限制 找到唯一记录
        criteria.andStudentIdEqualTo(userId);
        criteria.andPaperIdEqualTo(userExamSaveReq.getPaperId());
        if (!examInfoMapper.selectByExample(example).isEmpty()) {
            if (!examInfoMapper.selectByExample(example).get(0).getStatus()) {
                // 取出开始时间与当前时间做差值
                Date createTime = examInfoMapper.selectByExample(example).get(0).getCreateTime();
                Integer spendTime = Math.toIntExact(Duration.between(createTime.toInstant(), Instant.now()).getSeconds());
                examInfo.setExamTime(spendTime);
                examInfoMapper.updateByExampleSelective(examInfo, example);
                resp.setMessage("保存成功，考试结束");
                // 异步执行批阅任务
                asyncService.startCheckExam(userId, userExamSaveReq.getPaperId());
            } else {
                resp.setMessage("作答信息已提交");
                resp.setSuccess(false);
            }
        } else {
            resp.setMessage("查找不到对应试卷");
            resp.setSuccess(false);
        }
        return resp;
    }

    /**
     * 检查考试提交状态的业务方法
     *
     * @param paperId : 试卷id
     * @return : java.lang.Boolean
     * @author h0ss
     */
    public Boolean checkSubmit(Long paperId) {
        ExamInfoExample example = new ExamInfoExample();
        ExamInfoExample.Criteria criteria = example.createCriteria();
        criteria.andStudentIdEqualTo(StpUtil.getLoginIdAsLong());
        criteria.andPaperIdEqualTo(paperId);
        if (!examInfoMapper.selectByExample(example).isEmpty()) {
            return examInfoMapper.selectByExample(example).get(0).getStatus();
        } else {
            return Boolean.FALSE;
        }
    }

    /**
     * 获取考试剩余时间
     *
     * @param paperId : 试卷id
     * @return : java.lang.Integer
     * @author h0ss
     */
    public Integer getSurplusTime(Long paperId) {
        // 获取对应考试信息
        ExamInfoExample example = new ExamInfoExample();
        ExamInfoExample.Criteria criteria = example.createCriteria();
        criteria.andStudentIdEqualTo(StpUtil.getLoginIdAsLong());
        criteria.andPaperIdEqualTo(paperId);
        List<ExamInfo> data = examInfoMapper.selectByExample(example);
        // 查找出试题总时长
        ExamPaperExample examPaperExample = new ExamPaperExample();
        ExamPaperExample.Criteria examPaperExampleCriteria = examPaperExample.createCriteria();
        examPaperExampleCriteria.andIdEqualTo(paperId);
        List<ExamPaper> paper = examPaperMapper.selectByExample(examPaperExample);
        if (!data.isEmpty() && !paper.isEmpty()) {
            // 取出开始时间与当前时间做差值
            Date createTime = data.get(0).getCreateTime();
            Integer spendTime = Math.toIntExact(Duration.between(createTime.toInstant(), Instant.now()).getSeconds());
            // 计算剩余时间
            Integer totalTime = paper.get(0).getExamTime();
            int surplus = totalTime - spendTime;
            if (surplus <= 0) {
                // 超时设置为提交状态
                ExamInfo examInfo = new ExamInfo();
                examInfo.setStatus(Boolean.TRUE);
                examInfoMapper.updateByExampleSelective(examInfo, example);
            }
            return surplus;
        } else {
            return 0;
        }
    }
}

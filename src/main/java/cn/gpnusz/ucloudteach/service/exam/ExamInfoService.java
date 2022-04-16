package cn.gpnusz.ucloudteach.service.exam;


import cn.gpnusz.ucloudteach.common.PageReq;
import cn.gpnusz.ucloudteach.common.PageResp;
import cn.gpnusz.ucloudteach.entity.ExamInfo;
import cn.gpnusz.ucloudteach.entity.ExamInfoExample;
import cn.gpnusz.ucloudteach.mapper.ExamCustMapper;
import cn.gpnusz.ucloudteach.mapper.ExamInfoMapper;
import cn.gpnusz.ucloudteach.mapper.PaperQuestionCustMapper;
import cn.gpnusz.ucloudteach.resp.ExamCust;
import cn.gpnusz.ucloudteach.resp.QuestionCust;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author h0ss
 * @description 操作考试信息信息的业务层
 * @date 2021/11/17 22:16
 */

@Service
public class ExamInfoService {
    @Resource
    private ExamCustMapper examCustMapper;

    @Resource
    private ExamInfoMapper examInfoMapper;

    @Resource
    private PaperQuestionCustMapper paperQuestionCustMapper;

    @Resource
    private ExamPaperService examPaperService;

    private static final Logger LOG = LoggerFactory.getLogger(ExamInfoService.class);


    /**
     * 获取考试信息的业务方法
     * @param pageReq : 查询信息
     * @return : cn.gpnusz.ucloudteach.common.PageResp<cn.gpnusz.ucloudteach.resp.ExamCust>
     * @author h0ss
     */
    public PageResp<ExamCust> getAll(PageReq pageReq) {
        // 获取全部考试信息,不设置则每次最多显示100条
        if (pageReq.getPage() != null && pageReq.getSize() != null) {
            PageHelper.startPage(pageReq.getPage(), pageReq.getSize());
        } else {
            PageHelper.startPage(1, 100);
        }
        List<ExamCust> examCustList = examCustMapper.selectExamInfos();
        return getExamInfoResp(examCustList);
    }

    /**
     * 封装考试信息的业务方法
     * @param examCustList :
     * @return : cn.gpnusz.ucloudteach.common.PageResp<cn.gpnusz.ucloudteach.resp.ExamCust>
     * @author h0ss
     */
    private PageResp<ExamCust> getExamInfoResp(List<ExamCust> examCustList) {
        List<ExamCust> respList = new ArrayList<>(examCustList.size());
        // 将examInfoList中的元素深拷贝到ExamCust
        for (ExamCust examCust : examCustList) {
            ExamCust examCustQueryResp = new ExamCust();
            BeanUtils.copyProperties(examCust, examCustQueryResp);
            respList.add(examCustQueryResp);
        }
        // 创建分页信息对象
        PageInfo<ExamCust> pageInfo = new PageInfo<>(examCustList);
        LOG.info("总行数:{}", pageInfo.getTotal());
        LOG.info("总页数:{}", pageInfo.getPages());

        // 返回分页结果对象
        PageResp<ExamCust> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);
        return pageResp;
    }

    /**
     * 异步方法批阅试卷
     *
     * @param userId  : 学员id
     * @param paperId : 试卷id
     * @author h0ss
     */
    @Async
    public void checkExam(Long userId, Long paperId) {
        LOG.info("开始批阅学员 {} 的试卷 {}", userId, paperId);
        // 查询试卷信息
        ExamInfoExample examInfoExample = new ExamInfoExample();
        ExamInfoExample.Criteria criteria = examInfoExample.createCriteria();
        criteria.andPaperIdEqualTo(paperId);
        criteria.andStudentIdEqualTo(userId);
        List<ExamInfo> examInfos = examInfoMapper.selectByExampleWithBLOBs(examInfoExample);
        // 查询试卷中试题对应的答案以及分值
        List<QuestionCust> questions = paperQuestionCustMapper.selectQuestionByPaperId(paperId);
        // 初始化批阅总分 正确题数 错误题数
        Integer totalScore = 0;
        Integer rightCount = 0;
        Integer errorScore = 0;
        // 存放正确答案的map
        HashMap<Long, String> correctMap = new HashMap<>(questions.size());
        // 存放批阅得分细则的map 由于后续需要转成json 故键值为String
        HashMap<String, Integer> checkMap = new HashMap<>(questions.size());
        // 存放题目分值的map
        HashMap<String, Integer> scoreMap = new HashMap<>(questions.size());
        // 取出问题的正确答案 批阅得分细则默认为题目分数
        for (QuestionCust question : questions) {
            if (question.getType() == 2 || question.getType() == 3) {
                correctMap.put(question.getQuestionId(), question.getAnswerText());
            } else {
                correctMap.put(question.getQuestionId(), question.getAnswerOption());
            }
            scoreMap.put(Long.toString(question.getQuestionId()), question.getScore());
        }
        // 开始校验对错
        if (!examInfos.isEmpty()) {
            ExamInfo examInfo = examInfos.get(0);
            String studentAnswer = examInfo.getStudentAnswer();
            JSONObject studentAnswerJson = JSON.parseObject(studentAnswer);
            // 遍历答案去比较对错
            for (Map.Entry<String, Object> set : studentAnswerJson.entrySet()) {
                String val1 = set.getValue().toString();
                String val2 = correctMap.get(Long.valueOf(set.getKey()));
                // 格式化后匹配
                val1 = val1.replaceAll("\\[", "").replaceAll("]", "").replaceAll("\"", "");
                // 正确与否判断
                if (!val1.equals(val2)) {
                    checkMap.put(set.getKey(), 0);
                    errorScore += 1;
                } else {
                    totalScore += scoreMap.get(set.getKey());
                    rightCount += 1;
                    checkMap.put(set.getKey(), scoreMap.get(set.getKey()));
                }
            }
            // 开始写入批阅信息
            examInfo.setCheckFlag(Boolean.TRUE);
            examInfo.setScore(totalScore);
            examInfo.setRightCount(rightCount);
            examInfo.setErrorCount(errorScore);
            examInfo.setCheckList(JSON.toJSONString(checkMap));
            examInfoMapper.updateByExampleSelective(examInfo, examInfoExample);
            // 批阅数调整
            examPaperService.plusCheckCount(paperId);
            LOG.info("学员 {} 的试卷 {} 批阅完成", userId, paperId);
        }
    }
}

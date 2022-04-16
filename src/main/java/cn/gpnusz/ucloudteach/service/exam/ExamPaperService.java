package cn.gpnusz.ucloudteach.service.exam;

import cn.gpnusz.ucloudteach.common.PageResp;
import cn.gpnusz.ucloudteach.entity.*;
import cn.gpnusz.ucloudteach.mapper.ExamPaperMapper;
import cn.gpnusz.ucloudteach.mapper.PaperQuestionCustMapper;
import cn.gpnusz.ucloudteach.mapper.PaperQuestionMapper;
import cn.gpnusz.ucloudteach.req.ExamPaperQueryReq;
import cn.gpnusz.ucloudteach.req.ExamPaperSaveReq;
import cn.gpnusz.ucloudteach.req.PaperQuestionSaveReq;
import cn.gpnusz.ucloudteach.resp.QuestionCust;
import cn.gpnusz.ucloudteach.util.SnowFlake;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author h0ss
 * @description 操作试卷信息的业务层
 * @date 2021/11/19 19:25
 */

@Service
public class ExamPaperService {
    @Resource
    private ExamPaperMapper examPaperMapper;

    @Resource
    private PaperQuestionMapper paperQuestionMapper;

    @Resource
    private PaperQuestionCustMapper paperQuestionCustMapper;

    @Resource
    private SnowFlake snowFlake;

    private static final Logger LOG = LoggerFactory.getLogger(ExamPaperService.class);

    /**
     * 按传入的条件查询试卷
     *
     * @param examPaperQueryReq : 查询对象
     * @return : cn.gpnusz.ucloudteach.common.PageResp<cn.gpnusz.ucloudteach.entity.ExamPaper>
     * @author h0ss
     */
    public PageResp<ExamPaper> getAllByCondition(ExamPaperQueryReq examPaperQueryReq) {
        ExamPaperExample examPaperExample = new ExamPaperExample();
        ExamPaperExample.Criteria criteria = examPaperExample.createCriteria();
        // 查询条件判断
        if (!ObjectUtils.isEmpty(examPaperQueryReq.getId())) {
            criteria.andIdEqualTo(examPaperQueryReq.getId());
        }
        if (!ObjectUtils.isEmpty(examPaperQueryReq.getCourseId())) {
            criteria.andCourseIdEqualTo(examPaperQueryReq.getCourseId());
        }
        if (!ObjectUtils.isEmpty(examPaperQueryReq.getSubjectId())) {
            criteria.andSubjectIdEqualTo(examPaperQueryReq.getSubjectId());
        }
        if (!ObjectUtils.isEmpty(examPaperQueryReq.getName())) {
            criteria.andNameLike("%" + examPaperQueryReq.getName() + "%");
        }
        if (!ObjectUtils.isEmpty(examPaperQueryReq.getStatus())) {
            criteria.andStatusEqualTo(examPaperQueryReq.getStatus());
        }
        // 分页查询
        if (examPaperQueryReq.getPage() != null && examPaperQueryReq.getSize() != null) {
            PageHelper.startPage(examPaperQueryReq.getPage(), examPaperQueryReq.getSize());
        }
        examPaperExample.setOrderByClause("sort desc,create_time desc");
        List<ExamPaper> examPaperList = examPaperMapper.selectByExample(examPaperExample);
        return getExamPaperResp(examPaperList);
    }

    /**
     * 新增或编辑试卷信息的业务方法
     *
     * @param examPaperSaveReq : 保存的试卷信息数据
     * @author h0ss
     */
    public void save(ExamPaperSaveReq examPaperSaveReq) {
        // 创建一个新对象
        ExamPaper examPaper = new ExamPaper();
        BeanUtils.copyProperties(examPaperSaveReq, examPaper);
        // 判断是新增还是编辑
        if (examPaper.getId() != null) {
            ExamPaperExample examPaperExample = new ExamPaperExample();
            ExamPaperExample.Criteria criteria = examPaperExample.createCriteria();
            criteria.andIdEqualTo(examPaper.getId());
            // 设置为发布状态之后自动计算下属题目的总分
            if (examPaper.getStatus()) {
                int totalScore = 0;
                List<QuestionCust> list = paperQuestionCustMapper.selectQuestionByPaperId(examPaper.getId());
                for (QuestionCust questionCust : list) {
                    totalScore += questionCust.getScore();
                }
                examPaper.setTotalScore(totalScore);
            }
            examPaperMapper.updateByExampleSelective(examPaper, examPaperExample);
        } else {
            // 设置创建日期时间
            examPaper.setCreateTime(new Date());
            // 默认值
            examPaper.setJoinCount(0);
            examPaper.setCheckCount(0);
            examPaper.setQuestionCount(0);
            // 雪花算法生成id
            examPaper.setId(snowFlake.nextId());
            examPaperMapper.insertSelective(examPaper);
        }
    }

    /**
     * 删除试卷信息的业务方法
     *
     * @param id : 要删除的试卷信息id
     * @author h0ss
     */
    public void delete(Long id) {
        ExamPaperExample examPaperExample = new ExamPaperExample();
        ExamPaperExample.Criteria criteria = examPaperExample.createCriteria();
        criteria.andIdEqualTo(id);
        examPaperMapper.deleteByExample(examPaperExample);
    }


    /**
     * 考试人数增长的业务方法
     *
     * @param id : 试卷id
     * @author h0ss
     */
    public void plusJoinCount(Long id) {
        ExamPaperExample examPaperExample = new ExamPaperExample();
        ExamPaperExample.Criteria criteria = examPaperExample.createCriteria();
        criteria.andIdEqualTo(id);
        List<ExamPaper> papers = examPaperMapper.selectByExample(examPaperExample);
        if (!ObjectUtils.isEmpty(papers)) {
            ExamPaper paper = papers.get(0);
            // 原子操作实现自增
            AtomicInteger atomicInteger = new AtomicInteger(paper.getJoinCount());
            atomicInteger.getAndIncrement();
            paper.setJoinCount(atomicInteger.get());
            examPaperMapper.updateByExampleSelective(paper, examPaperExample);
        }
    }

    /**
     * 批阅数增长的业务方法
     *
     * @param id : 试卷id
     * @author h0ss
     */
    public void plusCheckCount(Long id) {
        ExamPaperExample examPaperExample = new ExamPaperExample();
        ExamPaperExample.Criteria criteria = examPaperExample.createCriteria();
        criteria.andIdEqualTo(id);
        List<ExamPaper> papers = examPaperMapper.selectByExample(examPaperExample);
        if (!ObjectUtils.isEmpty(papers)) {
            ExamPaper paper = papers.get(0);
            // 原子操作实现自增
            AtomicInteger atomicInteger = new AtomicInteger(paper.getCheckCount());
            atomicInteger.getAndIncrement();
            paper.setCheckCount(atomicInteger.get());
            examPaperMapper.updateByExampleSelective(paper, examPaperExample);
        }
    }

    /**
     * 查询试卷对应的题目的业务方法
     *
     * @param paperId : 试卷id
     * @return : java.util.List<cn.gpnusz.ucloudteach.resp.QuestionCust>
     * @author h0ss
     */
    public List<QuestionCust> getQuestions(Long paperId) {
        return paperQuestionCustMapper.selectQuestionByPaperId(paperId);
    }

    /**
     * 保存试卷-题目关联信息的业务方法
     *
     * @param paperQuestionSaveReq : 试卷-题目关联信息
     * @author h0ss
     */
    public void savePaperAndQuestion(PaperQuestionSaveReq paperQuestionSaveReq) {
        PaperQuestion paperQuestion = new PaperQuestion();
        BeanUtils.copyProperties(paperQuestionSaveReq, paperQuestion);
        PaperQuestionExample paperQuestionExample = new PaperQuestionExample();
        PaperQuestionExample.Criteria criteria = paperQuestionExample.createCriteria();
        // 判断新增还是修改
        if (paperQuestion.getId() != null) {
            criteria.andIdEqualTo(paperQuestion.getId());
            paperQuestionMapper.updateByExampleSelective(paperQuestion, paperQuestionExample);
        } else {
            // 设置创建日期时间
            paperQuestion.setCreateTime(new Date());
            // 自动设置题号
            criteria.andPaperIdEqualTo(paperQuestion.getPaperId());
            paperQuestion.setSort((int) (paperQuestionMapper.countByExample(paperQuestionExample) + 1));
            // 雪花算法生成id
            paperQuestion.setId(snowFlake.nextId());
            paperQuestionMapper.insert(paperQuestion);
        }
        updateQuestionCount(paperQuestion.getPaperId());
    }

    /**
     * 从试卷中删除题目的业务方法
     *
     * @param id : 要删除的试卷-题目关联信息id
     * @author h0ss
     */
    public void deletePaperAndQuestion(Long id) {
        PaperQuestionExample paperQuestionExample = new PaperQuestionExample();
        PaperQuestionExample.Criteria criteria = paperQuestionExample.createCriteria();
        criteria.andIdEqualTo(id);
        // 如果id对应项存在则更新试卷中题目数信息
        List<PaperQuestion> paperQuestions = paperQuestionMapper.selectByExample(paperQuestionExample);
        if (!ObjectUtils.isEmpty(paperQuestions)) {
            paperQuestionMapper.deleteByExample(paperQuestionExample);
            updateQuestionCount(paperQuestions.get(0).getPaperId());
        }
    }

    /**
     * 更新试卷中的试题数
     *
     * @param paperId : 试卷id
     * @author h0ss
     */
    public void updateQuestionCount(Long paperId) {
        // 对试卷的试题数进行更新操作
        ExamPaperExample examPaperExample = new ExamPaperExample();
        ExamPaperExample.Criteria examPaperExampleCriteria = examPaperExample.createCriteria();
        PaperQuestionExample paperQuestionExample = new PaperQuestionExample();
        PaperQuestionExample.Criteria criteria = paperQuestionExample.createCriteria();
        examPaperExampleCriteria.andIdEqualTo(paperId);
        List<ExamPaper> papers = examPaperMapper.selectByExample(examPaperExample);
        if (!ObjectUtils.isEmpty(papers)) {
            criteria.andPaperIdEqualTo(paperId);
            papers.get(0).setQuestionCount((int) paperQuestionMapper.countByExample(paperQuestionExample));
            examPaperMapper.updateByExampleSelective(papers.get(0), examPaperExample);
        }
    }

    /**
     * 将试卷信息查询结果封装成PageResp对象的业务方法
     *
     * @param examPaperList : 试卷信息查询结果
     * @return : cn.gpnusz.ucloudteach.common.PageResp<cn.gpnusz.ucloudteach.entity.ExamPaper>
     * @author h0ss
     */
    public PageResp<ExamPaper> getExamPaperResp(List<ExamPaper> examPaperList) {
        List<ExamPaper> respList = new ArrayList<>(examPaperList.size());
        // 将examPaperList中的元素深拷贝到ExamPaper
        for (ExamPaper examPaper : examPaperList) {
            ExamPaper examPaperQueryResp = new ExamPaper();
            BeanUtils.copyProperties(examPaper, examPaperQueryResp);
            respList.add(examPaperQueryResp);
        }
        // 创建分页信息对象
        PageInfo<ExamPaper> pageInfo = new PageInfo<>(examPaperList);
        LOG.info("总行数:{}", pageInfo.getTotal());
        LOG.info("总页数:{}", pageInfo.getPages());

        // 返回分页结果对象
        PageResp<ExamPaper> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);
        return pageResp;
    }
}

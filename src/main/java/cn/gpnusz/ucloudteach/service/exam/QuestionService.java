package cn.gpnusz.ucloudteach.service.exam;

import cn.gpnusz.ucloudteach.common.PageResp;
import cn.gpnusz.ucloudteach.entity.Question;
import cn.gpnusz.ucloudteach.entity.QuestionExample;
import cn.gpnusz.ucloudteach.mapper.QuestionMapper;
import cn.gpnusz.ucloudteach.req.QuestionQueryReq;
import cn.gpnusz.ucloudteach.req.QuestionSaveReq;
import cn.gpnusz.ucloudteach.resp.QuestionAnswerResp;
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

/**
 * @author h0ss
 * @description 操作题目信息的业务层
 * @date 2021/11/18 17:30
 */

@Service
public class QuestionService {
    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private SnowFlake snowFlake;

    private static final Logger LOG = LoggerFactory.getLogger(QuestionService.class);

    /**
     * 按传入的条件查询题目
     *
     * @param questionQueryReq : 查询对象
     * @return : cn.gpnusz.ucloudteach.common.PageResp<cn.gpnusz.ucloudteach.entity.Question>
     * @author h0ss
     */
    public PageResp<Question> getAllByCondition(QuestionQueryReq questionQueryReq) {
        QuestionExample questionExample = new QuestionExample();
        QuestionExample.Criteria criteria = questionExample.createCriteria();
        // 查询条件判断
        if (!ObjectUtils.isEmpty(questionQueryReq.getId())) {
            criteria.andIdEqualTo(questionQueryReq.getId());
        }
        if (!ObjectUtils.isEmpty(questionQueryReq.getSubjectId())) {
            criteria.andSubjectIdEqualTo(questionQueryReq.getSubjectId());
        }
        if (!ObjectUtils.isEmpty(questionQueryReq.getCourseId())) {
            criteria.andCourseIdEqualTo(questionQueryReq.getCourseId());
        }
        if (!ObjectUtils.isEmpty(questionQueryReq.getSectionId())) {
            criteria.andSectionIdEqualTo(questionQueryReq.getSectionId());
        }
        if (!ObjectUtils.isEmpty(questionQueryReq.getType())) {
            criteria.andTypeEqualTo(questionQueryReq.getType());
        }
        // 获取全部题目信息,不设置则每次只显示前100条
        if (questionQueryReq.getPage() != null && questionQueryReq.getSize() != null) {
            PageHelper.startPage(questionQueryReq.getPage(), questionQueryReq.getSize());
        } else {
            PageHelper.startPage(1, 100);
        }
        questionExample.setOrderByClause("create_time desc");
        List<Question> questionList = questionMapper.selectByExampleWithBLOBs(questionExample);
        return getQuestionResp(questionList);
    }

    /**
     * 新增或编辑题目信息的业务方法
     *
     * @param questionSaveReq : 保存的题目信息数据
     * @author h0ss
     */
    public void save(QuestionSaveReq questionSaveReq) {
        // 创建一个新对象
        Question question = new Question();
        BeanUtils.copyProperties(questionSaveReq, question);
        // 判断是新增还是编辑
        if (question.getId() != null) {
            QuestionExample questionExample = new QuestionExample();
            QuestionExample.Criteria criteria = questionExample.createCriteria();
            criteria.andIdEqualTo(question.getId());
            questionMapper.updateByExampleSelective(question, questionExample);
        } else {
            // 设置创建日期时间
            question.setCreateTime(new Date());
            // 雪花算法生成id
            question.setId(snowFlake.nextId());
            questionMapper.insert(question);
        }
    }

    /**
     * 删除题目信息的业务方法
     *
     * @param id : 要删除的题目信息id
     * @author h0ss
     */
    public void delete(Long id) {
        QuestionExample questionExample = new QuestionExample();
        QuestionExample.Criteria criteria = questionExample.createCriteria();
        criteria.andIdEqualTo(id);
        questionMapper.deleteByExample(questionExample);
    }

    /**
     * 将题目信息查询结果封装成PageResp对象的业务方法
     *
     * @param questionList : 题目信息查询结果
     * @return : cn.gpnusz.ucloudteach.common.PageResp<cn.gpnusz.ucloudteach.entity.Question>
     * @author h0ss
     */
    private PageResp<Question> getQuestionResp(List<Question> questionList) {
        List<Question> respList = new ArrayList<>(questionList.size());
        // 将questionList中的元素深拷贝到Question
        for (Question question : questionList) {
            Question questionQueryResp = new Question();
            BeanUtils.copyProperties(question, questionQueryResp);
            respList.add(questionQueryResp);
        }
        // 创建分页信息对象
        PageInfo<Question> pageInfo = new PageInfo<>(questionList);
        LOG.info("总行数:{}", pageInfo.getTotal());
        LOG.info("总页数:{}", pageInfo.getPages());

        // 返回分页结果对象
        PageResp<Question> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);
        return pageResp;
    }

    /**
     * 获取题目答案的业务方法
     *
     * @param id : 题目id
     * @return : cn.gpnusz.ucloudteach.resp.QuestionAnswerResp
     * @author h0ss
     */
    public QuestionAnswerResp getAnswer(Long id) {
        QuestionExample example = new QuestionExample();
        QuestionExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<Question> res = questionMapper.selectByExampleWithBLOBs(example);
        if (!res.isEmpty()) {
            QuestionAnswerResp resp = new QuestionAnswerResp();
            BeanUtils.copyProperties(res.get(0), resp);
            return resp;
        }
        return null;
    }
}

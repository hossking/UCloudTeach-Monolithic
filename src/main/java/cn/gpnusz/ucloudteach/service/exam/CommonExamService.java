package cn.gpnusz.ucloudteach.service.exam;

import cn.gpnusz.ucloudteach.common.PageResp;
import cn.gpnusz.ucloudteach.entity.ExamPaper;
import cn.gpnusz.ucloudteach.entity.ExamPaperExample;
import cn.gpnusz.ucloudteach.entity.Subject;
import cn.gpnusz.ucloudteach.mapper.ExamPaperMapper;
import cn.gpnusz.ucloudteach.req.ExamPaperQueryReq;
import cn.gpnusz.ucloudteach.service.course.SubjectService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author h0ss
 * @description 获取试卷信息的通用业务层
 * @date 2021/12/1 - 2:09
 */
@Service
public class CommonExamService {

    @Resource
    private SubjectService subjectService;

    @Resource
    private ExamPaperService examPaperService;

    @Resource
    private ExamPaperMapper examPaperMapper;


    /**
     * 根据年级id获取对应试卷信息
     *
     * @param gradeId : 年级id
     * @param size    : 每页数目
     * @param page    : 页码
     * @return : cn.gpnusz.ucloudteach.common.PageResp<cn.gpnusz.ucloudteach.entity.ExamPaper>
     * @author h0ss
     */
    public PageResp<ExamPaper> getPaperByGrade(Long gradeId, Integer size, Integer page) {
        List<Subject> subjects = subjectService.getByGrade(gradeId);
        if (subjects.isEmpty()) {
            return null;
        }
        ArrayList<Long> subjectIds = new ArrayList<>(subjects.size());
        for (Subject subject : subjects) {
            subjectIds.add(subject.getId());
        }
        ExamPaperExample example = new ExamPaperExample();
        ExamPaperExample.Criteria criteria = example.createCriteria();
        criteria.andSubjectIdIn(subjectIds);
        criteria.andStatusEqualTo(Boolean.TRUE);
        // 只查询有效期内的试卷
        criteria.andStartDateLessThanOrEqualTo(new Date());
        criteria.andEndDateGreaterThanOrEqualTo(new Date());
        PageHelper.startPage(page, size);
        example.setOrderByClause("sort desc");
        return examPaperService.getExamPaperResp(examPaperMapper.selectByExample(example));
    }


    /**
     * 公共查询试卷信息的业务方法 只显示有效期内的试卷
     *
     * @param examPaperQueryReq : 查询信息
     * @return : cn.gpnusz.ucloudteach.common.PageResp<cn.gpnusz.ucloudteach.entity.ExamPaper>
     * @author h0ss
     */
    public PageResp<ExamPaper> getPaperCommon(ExamPaperQueryReq examPaperQueryReq) {
        ExamPaperExample examPaperExample = new ExamPaperExample();
        ExamPaperExample.Criteria criteria = examPaperExample.createCriteria();
        if (!ObjectUtils.isEmpty(examPaperQueryReq.getId())) {
            criteria.andIdEqualTo(examPaperQueryReq.getId());
        }
        // 只查询有效期内的试卷
        criteria.andStartDateLessThanOrEqualTo(new Date());
        criteria.andEndDateGreaterThanOrEqualTo(new Date());
        criteria.andStatusEqualTo(examPaperQueryReq.getStatus());
        // 分页查询
        if (examPaperQueryReq.getPage() != null && examPaperQueryReq.getSize() != null) {
            PageHelper.startPage(examPaperQueryReq.getPage(), examPaperQueryReq.getSize());
        }
        examPaperExample.setOrderByClause("sort desc");
        List<ExamPaper> examPaperList = examPaperMapper.selectByExample(examPaperExample);
        return examPaperService.getExamPaperResp(examPaperList);
    }


}

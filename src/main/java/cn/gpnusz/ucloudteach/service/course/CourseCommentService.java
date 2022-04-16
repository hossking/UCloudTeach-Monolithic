package cn.gpnusz.ucloudteach.service.course;

import cn.gpnusz.ucloudteach.common.PageResp;
import cn.gpnusz.ucloudteach.entity.CourseComment;
import cn.gpnusz.ucloudteach.entity.CourseCommentExample;
import cn.gpnusz.ucloudteach.mapper.CourseCommentMapper;
import cn.gpnusz.ucloudteach.req.CourseCommentQueryReq;
import cn.gpnusz.ucloudteach.req.CourseCommentSaveReq;
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
 * @description 操作课程评论信息的业务层
 * @date 2021/11/17 22:16
 */

@Service
public class CourseCommentService {
    @Resource
    private CourseCommentMapper courseCommentMapper;

    @Resource
    private SnowFlake snowFlake;

    private static final Logger LOG = LoggerFactory.getLogger(CourseCommentService.class);

    /**
     * 按传入的条件查询课程评论
     *
     * @param courseCommentQueryReq : 查询对象
     * @return : cn.gpnusz.ucloudteach.common.PageResp<cn.gpnusz.ucloudteach.entity.CourseComment>
     * @author h0ss
     */
    public PageResp<CourseComment> getAllByCondition(CourseCommentQueryReq courseCommentQueryReq) {
        CourseCommentExample courseCommentExample = new CourseCommentExample();
        CourseCommentExample.Criteria criteria = courseCommentExample.createCriteria();
        // 查询条件判断
        if (!ObjectUtils.isEmpty(courseCommentQueryReq.getCourseId())) {
            criteria.andCourseIdEqualTo(courseCommentQueryReq.getCourseId());
        }
        if (!ObjectUtils.isEmpty(courseCommentQueryReq.getStudentId())) {
            criteria.andStudentIdEqualTo(courseCommentQueryReq.getStudentId());
        }
        if (!ObjectUtils.isEmpty(courseCommentQueryReq.getReplyId())) {
            criteria.andReplyIdEqualTo(courseCommentQueryReq.getReplyId());
        }
        // 获取全部课程评论信息,不设置则每次最多显示100条
        if (courseCommentQueryReq.getPage() != null && courseCommentQueryReq.getSize() != null) {
            PageHelper.startPage(courseCommentQueryReq.getPage(), courseCommentQueryReq.getSize());
        } else {
            PageHelper.startPage(1, 100);
        }
        courseCommentExample.setOrderByClause("top_flag desc, elite_flag desc, create_time desc");
        List<CourseComment> courseCommentList = courseCommentMapper.selectByExampleWithBLOBs(courseCommentExample);
        return getCourseCommentResp(courseCommentList);
    }

    /**
     * 新增或编辑课程评论信息的业务方法
     *
     * @param courseCommentSaveReq : 保存的课程评论信息数据
     * @author h0ss
     */
    public void save(CourseCommentSaveReq courseCommentSaveReq) {
        // 创建一个新对象
        CourseComment courseComment = new CourseComment();
        BeanUtils.copyProperties(courseCommentSaveReq, courseComment);
        // 判断是新增还是编辑
        if (courseComment.getId() != null) {
            CourseCommentExample courseCommentExample = new CourseCommentExample();
            CourseCommentExample.Criteria criteria = courseCommentExample.createCriteria();
            criteria.andIdEqualTo(courseComment.getId());
            courseCommentMapper.updateByExampleSelective(courseComment, courseCommentExample);
        } else {
            // 如果精华、置顶标志为null则置为false
            if (ObjectUtils.isEmpty(courseComment.getEliteFlag())) {
                courseComment.setEliteFlag(Boolean.FALSE);
            }
            if (ObjectUtils.isEmpty(courseComment.getTopFlag())) {
                courseComment.setTopFlag(Boolean.FALSE);
            }
            // 设置创建日期时间
            courseComment.setCreateDate(new Date());
            courseComment.setCreateTime(new Date());
            // 雪花算法生成id
            courseComment.setId(snowFlake.nextId());
            courseCommentMapper.insert(courseComment);
        }
    }

    /**
     * 删除课程评论信息的业务方法
     *
     * @param id : 要删除的课程评论信息id
     * @author h0ss
     */
    public void delete(Long id) {
        CourseCommentExample courseCommentExample = new CourseCommentExample();
        CourseCommentExample.Criteria criteria = courseCommentExample.createCriteria();
        criteria.andIdEqualTo(id);
        courseCommentMapper.deleteByExample(courseCommentExample);
    }

    /**
     * 将课程评论信息查询结果封装成PageResp对象的业务方法
     *
     * @param courseCommentList : 课程评论信息查询结果
     * @return : cn.gpnusz.ucloudteach.common.PageResp<cn.gpnusz.ucloudteach.entity.CourseComment>
     * @author h0ss
     */
    private PageResp<CourseComment> getCourseCommentResp(List<CourseComment> courseCommentList) {
        List<CourseComment> respList = new ArrayList<>(courseCommentList.size());
        // 将courseCommentList中的元素深拷贝到CourseComment
        for (CourseComment courseComment : courseCommentList) {
            CourseComment courseCommentQueryResp = new CourseComment();
            BeanUtils.copyProperties(courseComment, courseCommentQueryResp);
            respList.add(courseCommentQueryResp);
        }
        // 创建分页信息对象
        PageInfo<CourseComment> pageInfo = new PageInfo<>(courseCommentList);
        LOG.info("总行数:{}", pageInfo.getTotal());
        LOG.info("总页数:{}", pageInfo.getPages());

        // 返回分页结果对象
        PageResp<CourseComment> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);
        return pageResp;
    }

    /**
     * 查询评论和发布人是否匹配
     *
     * @param studentId : 用户id
     * @param id        : 评论id
     * @return : java.lang.Boolean
     * @author h0ss
     */
    public Boolean checkPair(Long studentId, Long id) {
        CourseCommentExample example = new CourseCommentExample();
        CourseCommentExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        criteria.andStudentIdEqualTo(studentId);
        if (courseCommentMapper.countByExample(example) == 0) {
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }
    }
}

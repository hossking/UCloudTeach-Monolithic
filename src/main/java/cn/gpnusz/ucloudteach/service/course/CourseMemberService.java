package cn.gpnusz.ucloudteach.service.course;

import cn.dev33.satoken.stp.StpUtil;
import cn.gpnusz.ucloudteach.common.PageResp;
import cn.gpnusz.ucloudteach.entity.CourseMember;
import cn.gpnusz.ucloudteach.entity.CourseMemberExample;
import cn.gpnusz.ucloudteach.mapper.CourseMemberMapper;
import cn.gpnusz.ucloudteach.req.CourseMemberQueryReq;
import cn.gpnusz.ucloudteach.req.CourseMemberSaveReq;
import cn.gpnusz.ucloudteach.util.SnowFlake;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author h0ss
 * @description 操作课程成员信息信息的业务层
 * @date 2021/11/17 2:11
 */

@Service
public class CourseMemberService {
    @Resource
    private CourseMemberMapper courseMemberMapper;

    @Resource
    private SnowFlake snowFlake;

    @Resource
    private CourseService courseService;

    private static final Logger LOG = LoggerFactory.getLogger(CourseMemberService.class);

    /**
     * 按传入条件查询课程成员信息的业务方法
     *
     * @param courseMemberQueryReq : 查询对象
     * @return : cn.gpnusz.ucloudteach.common.PageResp<cn.gpnusz.ucloudteach.entity.CourseMember>
     * @author h0ss
     */
    public PageResp<CourseMember> getAllByCondition(CourseMemberQueryReq courseMemberQueryReq) {
        CourseMemberExample courseMemberExample = new CourseMemberExample();
        CourseMemberExample.Criteria criteria = courseMemberExample.createCriteria();
        if (!ObjectUtils.isEmpty(courseMemberQueryReq.getStudentId())) {
            criteria.andStudentIdEqualTo(courseMemberQueryReq.getStudentId());
        }
        if (!ObjectUtils.isEmpty(courseMemberQueryReq.getCourseId())) {
            criteria.andCourseIdEqualTo(courseMemberQueryReq.getCourseId());
        }
        if (!ObjectUtils.isEmpty(courseMemberQueryReq.getJoinDate())) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                criteria.andJoinDateEqualTo(sdf.parse(courseMemberQueryReq.getJoinDate()));
            } catch (ParseException e) {
                LOG.error("日期转换有误，请输入正确日期");
            }
        }
        if (courseMemberQueryReq.getPage() != null && courseMemberQueryReq.getSize() != null) {
            PageHelper.startPage(courseMemberQueryReq.getPage(), courseMemberQueryReq.getSize());
        } else {
            PageHelper.startPage(1, 10);
        }
        List<CourseMember> courseMemberList = courseMemberMapper.selectByExample(courseMemberExample);
        return getCourseMemberResp(courseMemberList);
    }

    /**
     * 查询全部课程成员信息的业务方法
     *
     * @param courseMemberQueryReq : 查询（分页）参数
     * @return : cn.gpnusz.ucloudteach.common.PageResp<cn.gpnusz.ucloudteach.resp.CourseMember>
     * @author h0ss
     */
    public PageResp<CourseMember> getAll(CourseMemberQueryReq courseMemberQueryReq) {
        CourseMemberExample courseMemberExample = new CourseMemberExample();
        // 获取全部课程成员信息每次最多显示100条
        if (courseMemberQueryReq.getPage() != null && courseMemberQueryReq.getSize() != null) {
            PageHelper.startPage(courseMemberQueryReq.getPage(), courseMemberQueryReq.getSize());
        } else {
            PageHelper.startPage(1, 100);
        }
        List<CourseMember> courseMemberList = courseMemberMapper.selectByExample(courseMemberExample);
        return getCourseMemberResp(courseMemberList);
    }

    /**
     * 新增或编辑课程成员信息的业务方法
     *
     * @param courseMemberSaveReq : 保存的课程成员信息数据
     * @author h0ss
     */
    public void save(CourseMemberSaveReq courseMemberSaveReq, boolean userFlag) {
        // 创建一个新对象
        CourseMember courseMember = new CourseMember();
        BeanUtils.copyProperties(courseMemberSaveReq, courseMember);
        // 判断是新增还是编辑
        if (courseMember.getId() != null && !userFlag) {
            // 更新的情况
            CourseMemberExample courseMemberExample = new CourseMemberExample();
            CourseMemberExample.Criteria criteria = courseMemberExample.createCriteria();
            criteria.andIdEqualTo(courseMember.getId());
            courseMemberMapper.updateByExampleSelective(courseMember, courseMemberExample);
        } else {
            courseMember.setFinishCourse(0);
            courseMember.setJoinDate(new Date());
            courseMember.setJoinTime(new Date());
            // 需要判断是否是用户购买课程导致的新增
            if (!userFlag) {
                // 雪花算法生成id
                courseMember.setId(snowFlake.nextId());
            }
            courseMemberMapper.insert(courseMember);
            // 同步课程成员数
            courseService.increment(courseMember.getCourseId(), "member");
        }
    }

    /**
     * 删除课程成员信息的业务方法
     *
     * @param id : 要删除的课程成员信息id
     * @author h0ss
     */
    public void delete(Long id) {
        CourseMemberExample courseMemberExample = new CourseMemberExample();
        CourseMemberExample.Criteria criteria = courseMemberExample.createCriteria();
        criteria.andIdEqualTo(id);
        List<CourseMember> courseMembers = courseMemberMapper.selectByExample(courseMemberExample);
        courseMemberMapper.deleteByExample(courseMemberExample);
        // 同步课程成员数
        if (!ObjectUtils.isEmpty(courseMembers)) {
            courseService.decrement(courseMembers.get(0).getCourseId(), "member");
        }
    }

    /**
     * 将课程成员信息查询结果封装成PageResp对象的业务方法
     *
     * @param courseMemberList : 课程成员信息查询结果
     * @return : cn.gpnusz.ucloudteach.common.PageResp<cn.gpnusz.ucloudteach.resp.CourseMember>
     * @author h0ss
     */
    private PageResp<CourseMember> getCourseMemberResp(List<CourseMember> courseMemberList) {
        List<CourseMember> respList = new ArrayList<>(courseMemberList.size());
        // 将courseMemberList中的元素深拷贝到CourseMember
        for (CourseMember courseMember : courseMemberList) {
            CourseMember courseMemberQueryResp = new CourseMember();
            BeanUtils.copyProperties(courseMember, courseMemberQueryResp);
            respList.add(courseMemberQueryResp);
        }
        // 创建分页信息对象
        PageInfo<CourseMember> pageInfo = new PageInfo<>(courseMemberList);
        LOG.info("总行数:{}", pageInfo.getTotal());
        LOG.info("总页数:{}", pageInfo.getPages());

        // 返回分页结果对象
        PageResp<CourseMember> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);
        return pageResp;
    }

    /**
     * 查询学员是否参与了课程
     *
     * @param courseId : 课程id
     * @return : java.lang.Boolean
     * @author h0ss
     */
    public Boolean checkMember(Long courseId) {
        // 获取当前会话的id
        Long studentId = StpUtil.getLoginIdAsLong();
        if (!ObjectUtils.isEmpty(studentId) && !ObjectUtils.isEmpty(courseId)) {
            // 查询学习记录
            CourseMemberExample example = new CourseMemberExample();
            CourseMemberExample.Criteria criteria = example.createCriteria();
            criteria.andStudentIdEqualTo(studentId);
            criteria.andCourseIdEqualTo(courseId);
            long record = courseMemberMapper.countByExample(example);
            // 存在即返回true
            if (record > 0) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
}

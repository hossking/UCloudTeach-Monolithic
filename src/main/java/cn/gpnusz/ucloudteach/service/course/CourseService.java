package cn.gpnusz.ucloudteach.service.course;

import cn.gpnusz.ucloudteach.common.PageResp;
import cn.gpnusz.ucloudteach.entity.Course;
import cn.gpnusz.ucloudteach.entity.CourseExample;
import cn.gpnusz.ucloudteach.mapper.CourseCustMapper;
import cn.gpnusz.ucloudteach.mapper.CourseMapper;
import cn.gpnusz.ucloudteach.req.CourseCommonReq;
import cn.gpnusz.ucloudteach.req.CourseQueryReq;
import cn.gpnusz.ucloudteach.req.CourseSaveReq;
import cn.gpnusz.ucloudteach.resp.CourseCustResp;
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
 * @description 课程信息业务层
 * @date 2021/11/14 20:09
 */

@Service
public class CourseService {
    @Resource
    private CourseMapper courseMapper;

    @Resource
    private CourseCustMapper courseCustMapper;

    @Resource
    private SnowFlake snowFlake;

    private static final Logger LOG = LoggerFactory.getLogger(CourseService.class);

    private final String col1 = "member";
    private final String col2 = "section";
    private final String col3 = "period";

    /**
     * 按传入条件查询课程信息的业务方法
     *
     * @param courseQueryReq : 查询对象
     * @return : cn.gpnusz.ucloudteach.common.PageResp<cn.gpnusz.ucloudteach.entity.Course>
     * @author h0ss
     */
    public PageResp<Course> getAllByCondition(CourseQueryReq courseQueryReq) {
        CourseExample courseExample = new CourseExample();
        CourseExample.Criteria criteria = courseExample.createCriteria();
        courseExample.setOrderByClause("sort");
        if (!ObjectUtils.isEmpty(courseQueryReq.getName())) {
            criteria.andNameLike("%" + courseQueryReq.getName() + "%");
        }
        if (!ObjectUtils.isEmpty(courseQueryReq.getTeacher())) {
            criteria.andTeacherLike("%" + courseQueryReq.getTeacher() + "%");
        }
        if (!ObjectUtils.isEmpty(courseQueryReq.getStatus())) {
            criteria.andStatusEqualTo(courseQueryReq.getStatus());
        }
        if (courseQueryReq.getPage() != null && courseQueryReq.getSize() != null) {
            PageHelper.startPage(courseQueryReq.getPage(), courseQueryReq.getSize());
        } else {
            PageHelper.startPage(1, 10);
        }
        List<Course> courseList = courseMapper.selectByExampleWithBLOBs(courseExample);
        return getCourseResp(courseList);
    }

    /**
     * 查询全部课程信息的业务方法
     *
     * @param courseQueryReq : 查询（分页）参数
     * @return : cn.gpnusz.ucloudteach.common.PageResp<cn.gpnusz.ucloudteach.resp.Course>
     * @author h0ss
     */
    public PageResp<Course> getAll(CourseQueryReq courseQueryReq) {
        CourseExample courseExample = new CourseExample();
        courseExample.setOrderByClause("sort");
        // 获取全部课程信息每次最多显示100条
        if (courseQueryReq.getPage() != null && courseQueryReq.getSize() != null) {
            PageHelper.startPage(courseQueryReq.getPage(), courseQueryReq.getSize());
        } else {
            PageHelper.startPage(1, 100);
        }
        List<Course> courseList = courseMapper.selectByExampleWithBLOBs(courseExample);
        return getCourseResp(courseList);
    }

    /**
     * 新增或编辑课程信息的业务方法
     *
     * @param courseSaveReq : 保存的课程信息数据
     * @author h0ss
     */
    public void save(CourseSaveReq courseSaveReq) {
        // 创建一个新对象
        Course course = new Course();
        BeanUtils.copyProperties(courseSaveReq, course);
        // 判断是新增还是编辑
        if (course.getId() != null) {
            CourseExample courseExample = new CourseExample();
            CourseExample.Criteria criteria = courseExample.createCriteria();
            criteria.andIdEqualTo(course.getId());
            courseMapper.updateByExample(course, courseExample);
        } else {
            // 雪花算法生成id
            course.setId(snowFlake.nextId());
            // 创建日期
            course.setCreateTime(new Date());
            courseMapper.insertSelective(course);

        }
    }

    /**
     * 删除课程信息的业务方法
     *
     * @param id : 要删除的课程信息id
     * @author h0ss
     */
    public void delete(Long id) {
        CourseExample courseExample = new CourseExample();
        CourseExample.Criteria criteria = courseExample.createCriteria();
        criteria.andIdEqualTo(id);
        courseMapper.deleteByExample(courseExample);
    }

    /**
     * 对于指定字段的自增操作
     *
     * @param id  : 课程id
     * @param col : 字段简述
     * @author h0ss
     */
    public void increment(Long id, String col) {
        CourseExample courseExample = new CourseExample();
        CourseExample.Criteria criteria = courseExample.createCriteria();
        criteria.andIdEqualTo(id);
        List<Course> courses = courseMapper.selectByExample(courseExample);
        if (!ObjectUtils.isEmpty(courses)) {
            Course course = courses.get(0);
            if (col1.equals(col)) {
                // 原子操作实现自增
                AtomicInteger atomicInteger = new AtomicInteger(course.getTotalMember());
                atomicInteger.getAndIncrement();
                course.setTotalMember(atomicInteger.get());
            } else if (col2.equals(col)) {
                // 原子操作实现自增
                AtomicInteger atomicInteger = new AtomicInteger(course.getTotalSection());
                atomicInteger.getAndIncrement();
                course.setTotalSection(atomicInteger.get());
            } else if (col3.equals(col)) {
                // 原子操作实现自增
                AtomicInteger atomicInteger = new AtomicInteger(course.getTotalPeriod());
                atomicInteger.getAndIncrement();
                course.setTotalPeriod(atomicInteger.get());
            } else {
                return;
            }
            courseMapper.updateByExampleSelective(course, courseExample);
        }
    }

    /**
     * 对于指定字段的自减操作
     *
     * @param id  : 课程id
     * @param col : 字段简述
     * @author h0ss
     */
    public void decrement(Long id, String col) {
        CourseExample courseExample = new CourseExample();
        CourseExample.Criteria criteria = courseExample.createCriteria();
        criteria.andIdEqualTo(id);
        List<Course> courses = courseMapper.selectByExample(courseExample);
        if (!ObjectUtils.isEmpty(courses)) {
            Course course = courses.get(0);
            if (col1.equals(col)) {
                // 原子操作实现自减
                AtomicInteger atomicInteger = new AtomicInteger(course.getTotalMember());
                atomicInteger.getAndDecrement();
                course.setTotalMember(atomicInteger.get());
            } else if (col2.equals(col)) {
                // 原子操作实现自减
                AtomicInteger atomicInteger = new AtomicInteger(course.getTotalSection());
                atomicInteger.getAndDecrement();
                course.setTotalSection(atomicInteger.get());
            } else if (col3.equals(col)) {
                // 原子操作实现自减
                AtomicInteger atomicInteger = new AtomicInteger(course.getTotalPeriod());
                atomicInteger.getAndDecrement();
                course.setTotalPeriod(atomicInteger.get());
            } else {
                return;
            }
            courseMapper.updateByExampleSelective(course, courseExample);
        }
    }

    /**
     * 获取热门课程数据的业务方法[暂时直接从数据库中获取]
     *
     * @return : java.util.List<cn.gpnusz.ucloudteach.entity.Course>
     * @author h0ss
     */
    public List<CourseCustResp> getHot() {
        CourseExample courseExample = new CourseExample();
        CourseExample.Criteria criteria = courseExample.createCriteria();
        // 已上线的课程
        criteria.andStatusEqualTo(Boolean.TRUE);
        // 根据学员数来判定是否热门
        courseExample.setOrderByClause("total_member desc");
        // 只显示前六条数据
        PageHelper.startPage(1, 6);
        List<Course> hotCourses = courseMapper.selectByExampleWithBLOBs(courseExample);
        ArrayList<CourseCustResp> courseCustResps = new ArrayList<>(hotCourses.size());
        for (Course course : hotCourses) {
            CourseCustResp custResp = new CourseCustResp();
            BeanUtils.copyProperties(course, custResp);
            courseCustResps.add(custResp);
        }
        return courseCustResps;
    }

    /**
     * 根据传入信息获取课程详情的业务方法
     * @param courseCommonReq : 查询参数封装实体类
     * @return : java.util.List<cn.gpnusz.ucloudteach.resp.CourseCustResp>
     * @author h0ss
     */
    public List<CourseCustResp> getContent(CourseCommonReq courseCommonReq) {
        final String memberSort = "totalMember";
        // 这里需要多一步判断 不可直接查询 因为mybatis中排序用到的是$ 如果不判断就查询可能会造成注入危险
        if (memberSort.equals(courseCommonReq.getSortField())) {
            return courseCustMapper.selectCourseDetail(courseCommonReq.getCourseId(), courseCommonReq.getGradeId(), "total_member");
        }
        return courseCustMapper.selectCourseDetail(courseCommonReq.getCourseId(), courseCommonReq.getGradeId(), "sort");
    }


    /**
     * 将课程信息查询结果封装成PageResp对象的业务方法
     *
     * @param courseList : 课程信息查询结果
     * @return : cn.gpnusz.ucloudteach.common.PageResp<cn.gpnusz.ucloudteach.resp.Course>
     * @author h0ss
     */
    private PageResp<Course> getCourseResp(List<Course> courseList) {
        List<Course> respList = new ArrayList<>(courseList.size());
        // 将courseList中的元素深拷贝到Course
        for (Course course : courseList) {
            Course courseQueryResp = new Course();
            BeanUtils.copyProperties(course, courseQueryResp);
            respList.add(courseQueryResp);
        }
        // 创建分页信息对象
        PageInfo<Course> pageInfo = new PageInfo<>(courseList);
        LOG.info("总行数:{}", pageInfo.getTotal());
        LOG.info("总页数:{}", pageInfo.getPages());

        // 返回分页结果对象
        PageResp<Course> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);
        return pageResp;
    }
}

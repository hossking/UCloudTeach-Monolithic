package cn.gpnusz.ucloudteach.service.course;

import cn.gpnusz.ucloudteach.common.PageResp;
import cn.gpnusz.ucloudteach.entity.CourseSection;
import cn.gpnusz.ucloudteach.entity.CourseSectionExample;
import cn.gpnusz.ucloudteach.mapper.CourseSectionMapper;
import cn.gpnusz.ucloudteach.req.CourseSectionQueryReq;
import cn.gpnusz.ucloudteach.req.CourseSectionSaveReq;
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
import java.util.List;

/**
 * @author h0ss
 * @description 章节信息的业务层
 * @date 2021/11/14 23:06
 */

@Service
public class CourseSectionService {
    @Resource
    private CourseSectionMapper courseSectionMapper;

    @Resource
    private CourseService courseService;

    @Resource
    private SnowFlake snowFlake;

    private static final Logger LOG = LoggerFactory.getLogger(CourseSectionService.class);

    /**
     * 用于查询章节的业务方法
     * @param courseSectionQueryReq : 查询对象
     * @return : cn.gpnusz.ucloudteach.common.PageResp<cn.gpnusz.ucloudteach.entity.CourseSection>
     * @author h0ss
     */
    public PageResp<CourseSection> getAll(CourseSectionQueryReq courseSectionQueryReq) {
        CourseSectionExample courseSectionExample = new CourseSectionExample();
        CourseSectionExample.Criteria criteria = courseSectionExample.createCriteria();
        if (!ObjectUtils.isEmpty(courseSectionQueryReq.getCourseId())) {
            criteria.andCourseIdEqualTo(courseSectionQueryReq.getCourseId());
        }
        courseSectionExample.setOrderByClause("section");
        // 获取全部章节信息如果不指定每次最多显示100条
        if (courseSectionQueryReq.getPage() != null && courseSectionQueryReq.getSize() != null) {
            PageHelper.startPage(courseSectionQueryReq.getPage(), courseSectionQueryReq.getSize());
        } else {
            PageHelper.startPage(1, 100);
        }
        List<CourseSection> courseSectionList = courseSectionMapper.selectByExample(courseSectionExample);
        return getCourseSectionResp(courseSectionList);
    }

    /**
     * 新增或编辑章节信息的业务方法
     *
     * @param courseSectionSaveReq : 保存的章节信息数据
     * @author h0ss
     */
    public void save(CourseSectionSaveReq courseSectionSaveReq) {
        // 创建一个新对象
        CourseSection courseSection = new CourseSection();
        BeanUtils.copyProperties(courseSectionSaveReq, courseSection);
        // 判断是新增还是编辑
        if (courseSection.getId() != null) {
            CourseSectionExample courseSectionExample = new CourseSectionExample();
            CourseSectionExample.Criteria criteria = courseSectionExample.createCriteria();
            criteria.andIdEqualTo(courseSection.getId());
            courseSectionMapper.updateByExampleSelective(courseSection, courseSectionExample);
        } else {
            // 雪花算法生成id
            courseSection.setId(snowFlake.nextId());
            courseSectionMapper.insert(courseSection);
            // 对章节数进行自增
            courseService.increment(courseSection.getCourseId(),"section");
        }
    }

    /**
     * 删除章节信息的业务方法
     *
     * @param id : 要删除的章节信息id
     * @author h0ss
     */
    public void delete(Long id) {
        CourseSectionExample courseSectionExample = new CourseSectionExample();
        CourseSectionExample.Criteria criteria = courseSectionExample.createCriteria();
        criteria.andIdEqualTo(id);
        List<CourseSection> courseSections = courseSectionMapper.selectByExample(courseSectionExample);
        courseSectionMapper.deleteByExample(courseSectionExample);
        if (!ObjectUtils.isEmpty(courseSections)) {
            courseService.decrement(courseSections.get(0).getCourseId(), "section");
        }
    }

    /**
     * 将章节信息查询结果封装成PageResp对象的业务方法
     *
     * @param courseSectionList : 章节信息查询结果
     * @return : cn.gpnusz.ucloudteach.common.PageResp<cn.gpnusz.ucloudteach.entity.CourseSection>
     * @author h0ss
     */
    private PageResp<CourseSection> getCourseSectionResp(List<CourseSection> courseSectionList) {
        List<CourseSection> respList = new ArrayList<>(courseSectionList.size());
        // 将courseSectionList中的元素深拷贝到CourseSection
        for (CourseSection courseSection : courseSectionList) {
            CourseSection courseSectionQueryResp = new CourseSection();
            BeanUtils.copyProperties(courseSection, courseSectionQueryResp);
            respList.add(courseSectionQueryResp);
        }
        // 创建分页信息对象
        PageInfo<CourseSection> pageInfo = new PageInfo<>(courseSectionList);
        LOG.info("总行数:{}", pageInfo.getTotal());
        LOG.info("总页数:{}", pageInfo.getPages());

        // 返回分页结果对象
        PageResp<CourseSection> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);
        return pageResp;
    }
}

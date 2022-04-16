package cn.gpnusz.ucloudteach.service.course;

import cn.gpnusz.ucloudteach.common.PageResp;
import cn.gpnusz.ucloudteach.entity.CoursePeriod;
import cn.gpnusz.ucloudteach.entity.CoursePeriodExample;
import cn.gpnusz.ucloudteach.mapper.CoursePeriodMapper;
import cn.gpnusz.ucloudteach.req.CoursePeriodQueryReq;
import cn.gpnusz.ucloudteach.req.CoursePeriodSaveReq;
import cn.gpnusz.ucloudteach.resp.CommonCoursePeriodResp;
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
 * @description 课时信息的业务层
 * @date 2021/11/15 0:05
 */

@Service
public class CoursePeriodService {
    @Resource
    private CoursePeriodMapper coursePeriodMapper;

    @Resource
    private CourseService courseService;

    @Resource
    private SnowFlake snowFlake;

    private static final Logger LOG = LoggerFactory.getLogger(CoursePeriodService.class);

    /**
     * 用于查询课时的业务方法
     *
     * @param coursePeriodQueryReq : 查询对象
     * @return : cn.gpnusz.ucloudteach.common.PageResp<cn.gpnusz.ucloudteach.entity.CoursePeriod>
     * @author h0ss
     */
    public PageResp<CoursePeriod> getAll(CoursePeriodQueryReq coursePeriodQueryReq) {
        CoursePeriodExample coursePeriodExample = new CoursePeriodExample();
        CoursePeriodExample.Criteria criteria = coursePeriodExample.createCriteria();
        if (!ObjectUtils.isEmpty(coursePeriodQueryReq.getCourseId())) {
            criteria.andCourseIdEqualTo(coursePeriodQueryReq.getCourseId());
        }
        coursePeriodExample.setOrderByClause("period");
        if (coursePeriodQueryReq.getSectionId() != null) {
            criteria.andIdEqualTo(coursePeriodQueryReq.getSectionId());
        }
        // 获取全部课时信息如果不指定每次最多显示100条
        if (coursePeriodQueryReq.getPage() != null && coursePeriodQueryReq.getSize() != null) {
            PageHelper.startPage(coursePeriodQueryReq.getPage(), coursePeriodQueryReq.getSize());
        } else {
            PageHelper.startPage(1, 100);
        }
        List<CoursePeriod> coursePeriodList = coursePeriodMapper.selectByExampleWithBLOBs(coursePeriodExample);
        return getCoursePeriodResp(coursePeriodList);
    }

    /**
     * 根据课程id获取课时公共信息的业务方法
     * @param courseId : 课程id
     * @return : java.util.List<cn.gpnusz.ucloudteach.resp.CommonCoursePeriodResp>
     * @author h0ss
     */
    public List<CommonCoursePeriodResp> getAllCommon(Long courseId) {
        if (!ObjectUtils.isEmpty(courseId)) {
            // 根据课程id查询
            CoursePeriodExample coursePeriodExample = new CoursePeriodExample();
            CoursePeriodExample.Criteria criteria = coursePeriodExample.createCriteria();
            criteria.andCourseIdEqualTo(courseId);
            coursePeriodExample.setOrderByClause("period");
            // 获取全部课时公共信息
            List<CoursePeriod> coursePeriodList = coursePeriodMapper.selectByExampleWithBLOBs(coursePeriodExample);
            if (!coursePeriodList.isEmpty()) {
                ArrayList<CommonCoursePeriodResp> respList = new ArrayList<>(coursePeriodList.size());
                // 将公共信息取出
                for (CoursePeriod coursePeriod: coursePeriodList) {
                    CommonCoursePeriodResp resp = new CommonCoursePeriodResp();
                    BeanUtils.copyProperties(coursePeriod, resp);
                    respList.add(resp);
                }
                return respList;
            }
        }
        return null;
    }


    /**
     * 新增或编辑课时信息的业务方法
     *
     * @param coursePeriodSaveReq : 保存的课时信息数据
     * @author h0ss
     */
    public void save(CoursePeriodSaveReq coursePeriodSaveReq) {
        // 创建一个新对象
        CoursePeriod coursePeriod = new CoursePeriod();
        BeanUtils.copyProperties(coursePeriodSaveReq, coursePeriod);
        // 判断是新增还是编辑
        if (coursePeriod.getId() != null) {
            CoursePeriodExample coursePeriodExample = new CoursePeriodExample();
            CoursePeriodExample.Criteria criteria = coursePeriodExample.createCriteria();
            criteria.andIdEqualTo(coursePeriod.getId());
            coursePeriodMapper.updateByExampleSelective(coursePeriod, coursePeriodExample);
        } else {
            // 雪花算法生成id
            coursePeriod.setId(snowFlake.nextId());
            coursePeriodMapper.insert(coursePeriod);
            // 对课时数进行自增
            courseService.increment(coursePeriod.getCourseId(), "period");
        }
    }

    /**
     * 删除课时信息的业务方法
     *
     * @param id : 要删除的课时信息id
     * @author h0ss
     */
    public void delete(Long id) {
        CoursePeriodExample coursePeriodExample = new CoursePeriodExample();
        CoursePeriodExample.Criteria criteria = coursePeriodExample.createCriteria();
        criteria.andIdEqualTo(id);
        List<CoursePeriod> coursePeriods = coursePeriodMapper.selectByExample(coursePeriodExample);
        coursePeriodMapper.deleteByExample(coursePeriodExample);
        if (!ObjectUtils.isEmpty(coursePeriods)) {
            // 对课时数进行自减
            courseService.decrement(coursePeriods.get(0).getCourseId(), "period");
        }
    }

    /**
     * 将课时信息查询结果封装成PageResp对象的业务方法
     *
     * @param coursePeriodList : 课时信息查询结果
     * @return : cn.gpnusz.ucloudteach.common.PageResp<cn.gpnusz.ucloudteach.entity.CoursePeriod>
     * @author h0ss
     */
    private PageResp<CoursePeriod> getCoursePeriodResp(List<CoursePeriod> coursePeriodList) {
        List<CoursePeriod> respList = new ArrayList<>(coursePeriodList.size());
        // 将coursePeriodList中的元素深拷贝到CoursePeriod
        for (CoursePeriod coursePeriod : coursePeriodList) {
            CoursePeriod coursePeriodQueryResp = new CoursePeriod();
            BeanUtils.copyProperties(coursePeriod, coursePeriodQueryResp);
            respList.add(coursePeriodQueryResp);
        }
        // 创建分页信息对象
        PageInfo<CoursePeriod> pageInfo = new PageInfo<>(coursePeriodList);
        LOG.info("总行数:{}", pageInfo.getTotal());
        LOG.info("总页数:{}", pageInfo.getPages());

        // 返回分页结果对象
        PageResp<CoursePeriod> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);
        return pageResp;
    }
}

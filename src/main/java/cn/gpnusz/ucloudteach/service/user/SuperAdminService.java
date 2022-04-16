package cn.gpnusz.ucloudteach.service.user;

import cn.dev33.satoken.stp.StpUtil;
import cn.gpnusz.ucloudteach.common.PageReq;
import cn.gpnusz.ucloudteach.common.PageResp;
import cn.gpnusz.ucloudteach.entity.Admin;
import cn.gpnusz.ucloudteach.entity.AdminExample;
import cn.gpnusz.ucloudteach.mapper.AdminMapper;
import cn.gpnusz.ucloudteach.req.AdminSaveReq;
import cn.gpnusz.ucloudteach.util.RandomKeyUtil;
import cn.gpnusz.ucloudteach.util.SnowFlake;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author h0ss
 * @description 管理admin的业务层
 * @date 2021/11/23 - 21:26
 */
@Service
public class SuperAdminService {

    @Resource
    private AdminMapper adminMapper;

    @Resource
    private RandomKeyUtil randomKeyUtil;

    @Resource
    private SnowFlake snowFlake;

    private static final Logger LOG = LoggerFactory.getLogger(SuperAdminService.class);

    /**
     * 查询管理员信息的业务方法
     *
     * @param pageReq : 查询（分页）参数
     * @return : cn.gpnusz.ucloudteach.common.PageResp<cn.gpnusz.ucloudteach.entity.Admin>
     * @author h0ss
     */
    public PageResp<Admin> getAll(PageReq pageReq) {
        // 获取全部学员信息每次最多显示100条
        if (pageReq.getPage() != null && pageReq.getSize() != null) {
            PageHelper.startPage(pageReq.getPage(), pageReq.getSize());
        } else {
            PageHelper.startPage(1, 100);
        }
        List<Admin> adminList = adminMapper.selectByExample(null);
        // 创建分页信息对象
        PageInfo<Admin> pageInfo = new PageInfo<>();
        LOG.info("总行数:{}", pageInfo.getTotal());
        LOG.info("总页数:{}", pageInfo.getPages());

        // 返回分页结果对象
        PageResp<Admin> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(adminList);
        return pageResp;
    }

    /**
     * 新增或编辑管理员信息的业务方法
     *
     * @param adminSaveReq : 保存的管理员信息数据
     * @author h0ss
     */
    public void save(AdminSaveReq adminSaveReq) {
        // 获取随机盐值
        String salt = randomKeyUtil.getRandomSalt(6);
        // 密码+盐 MD5加密处理
        String passwd = adminSaveReq.getPassword() + salt;
        adminSaveReq.setPassword(DigestUtils.md5DigestAsHex(passwd.getBytes(StandardCharsets.UTF_8)));
        // 创建一个新对象
        Admin admin = new Admin();
        BeanUtils.copyProperties(adminSaveReq, admin);
        admin.setSalt(salt);
        // 判断是新增还是编辑
        if (admin.getId() != null) {
            admin.setPassword(null);
            admin.setSalt(null);
            AdminExample adminExample = new AdminExample();
            AdminExample.Criteria criteria = adminExample.createCriteria();
            criteria.andIdEqualTo(admin.getId());
            adminMapper.updateByExampleSelective(admin, adminExample);
        } else {
            // 雪花算法生成id
            admin.setId(snowFlake.nextId());
            adminMapper.insert(admin);
        }
    }


    /**
     * 删除管理员的业务方法
     *
     * @param id : 要删除的管理员id
     * @author h0ss
     */
    public void delete(Long id) {
        if (!id.equals(StpUtil.getLoginIdAsLong())) {
            AdminExample adminExample = new AdminExample();
            AdminExample.Criteria criteria = adminExample.createCriteria();
            criteria.andIdEqualTo(id);
            adminMapper.deleteByExample(adminExample);
        }
    }
}

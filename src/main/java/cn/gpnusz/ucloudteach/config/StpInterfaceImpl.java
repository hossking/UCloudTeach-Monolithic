package cn.gpnusz.ucloudteach.config;

import cn.dev33.satoken.stp.StpInterface;
import cn.gpnusz.ucloudteach.entity.Admin;
import cn.gpnusz.ucloudteach.entity.AdminExample;
import cn.gpnusz.ucloudteach.mapper.AdminMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author h0ss
 * @description 自定义权限验证接口扩展
 * @date 2021/11/23 - 12:08
 */

@Component
public class StpInterfaceImpl implements StpInterface {

    @Resource
    private AdminMapper adminMapper;

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return new ArrayList<>();
    }

    /**
     * 返回一个账号所拥有的角色标识集合
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        List<String> roleList = new ArrayList<>(2);
        // 从数据库中取出admin用户信息如果校验一致则赋予权限
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andIdEqualTo(Long.valueOf((String) loginId));
        List<Admin> adminList = adminMapper.selectByExample(adminExample);
        if (!ObjectUtils.isEmpty(adminList)) {
            if (adminList.get(0).getSuperFlag()) {
                roleList.add("superAdmin");
            }
            roleList.add("admin");
        }
        if (!ObjectUtils.isEmpty(loginId)) {
            roleList.add("user");
        }
        return roleList;
    }

}

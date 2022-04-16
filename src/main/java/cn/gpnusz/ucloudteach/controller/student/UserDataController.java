package cn.gpnusz.ucloudteach.controller.student;

import cn.gpnusz.ucloudteach.common.CommonResp;
import cn.gpnusz.ucloudteach.req.UserDataSaveReq;
import cn.gpnusz.ucloudteach.service.user.UserDataService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author h0ss
 * @description 已登录用户对本人信息修改的接口
 * @date 2021/11/27 - 14:23
 */
@RestController
@RequestMapping("/api/user/info")
public class UserDataController {

    @Resource
    private UserDataService userDataService;

    /**
     * 用户编辑个人信息的接口
     *
     * @param userDataSaveReq : 请求编辑的信息
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<java.lang.Object>
     * @author h0ss
     */
    @PostMapping("/save")
    public CommonResp<Object> updateInfo(@RequestBody UserDataSaveReq userDataSaveReq) {
        CommonResp<Object> resp = new CommonResp<>();
        userDataService.updateInfo(userDataSaveReq);
        resp.setMessage("信息更新成功");
        return resp;
    }
}

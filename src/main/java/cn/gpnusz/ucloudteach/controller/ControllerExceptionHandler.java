package cn.gpnusz.ucloudteach.controller;

import cn.dev33.satoken.exception.DisableLoginException;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import cn.gpnusz.ucloudteach.exception.BusinessException;
import cn.gpnusz.ucloudteach.common.CommonResp;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author h0ss
 * @description 校验规则异常处理类
 * @date 2021/11/11 21:48
 */
@ControllerAdvice
public class ControllerExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    /**
     * 校验异常处理
     *
     * @param e : 异常信息
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<java.lang.Object>
     * @author h0ss
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public CommonResp<Object> validExceptionHandler(BindException e) {
        CommonResp<Object> commonResp = new CommonResp<>();
        LOG.warn("参数校验失败：{}", e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        commonResp.setSuccess(false);
        commonResp.setMessage(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return commonResp;
    }

    /**
     * 业务异常处理
     *
     * @param e : 异常信息
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<java.lang.Object>
     * @author h0ss
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public CommonResp<Object> businessExceptionHandler(BusinessException e) {
        CommonResp<Object> commonResp = new CommonResp<>();
        LOG.warn("业务异常：{}", e.getCode().getDesc());
        commonResp.setSuccess(false);
        commonResp.setMessage(e.getCode().getDesc());
        return commonResp;
    }

    /**
     * 系统异常处理
     *
     * @param e : 异常信息
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<java.lang.Object>
     * @author h0ss
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public CommonResp<Object> otherExceptionHandler(Exception e) {
        CommonResp<Object> commonResp = new CommonResp<>();
        LOG.error("系统异常：", e);
        commonResp.setSuccess(false);
        commonResp.setMessage("系统发生异常，请联系管理员");
        return commonResp;
    }

    /**
     * 自定义处理登录校验异常信息
     * @param e : 异常信息
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<java.lang.Object>
     * @author h0ss
     */
    @ResponseBody
    @ExceptionHandler(value = {NotLoginException.class,NotRoleException.class,NotPermissionException.class,DisableLoginException.class})
    public CommonResp<Object> notLoginExceptionHandler(Exception e) {
        CommonResp<Object> commonResp = new CommonResp<>();
        commonResp.setSuccess(false);
        if (e instanceof NotLoginException) {
            NotLoginException ee = (NotLoginException) e;
            LOG.error("未登录异常：{}", ee.getMessage());
            String message;
            if(NotLoginException.TOKEN_TIMEOUT.equals(ee.getType())) {
                message = "当前会话已过期";
            }
            else if(NotLoginException.BE_REPLACED.equals(ee.getType())) {
                message = "此账号在别处登录，当前会话已自动下线";
            }
            else if(NotLoginException.KICK_OUT.equals(ee.getType())) {
                message = "当前会话已被强制断开，请重新登录";
            }
            else {
                message = "当前会话未登录，请登录后访问";
            }
            commonResp.setMessage(message);
        } else if (e instanceof NotRoleException) {
            NotRoleException ee = (NotRoleException) e;
            LOG.error("角色未匹配异常：{}", ee.getMessage());
            commonResp.setMessage("您没有权限操作");
        } else if (e instanceof NotPermissionException) {
            NotPermissionException ee = (NotPermissionException) e;
            LOG.error("无此权限：{}", ee.getCode());
            commonResp.setMessage("您没有权限操作");
        } else if (e instanceof DisableLoginException) {
            DisableLoginException ee = (DisableLoginException) e;
            LOG.error("账号被封禁：{} {}", ee.getDisableTime(), "秒后解封");
            commonResp.setMessage("账号被封禁：" + ee.getDisableTime() + "秒后解封");
        }
        // 返回401给到前端进行sessionStorage清除以及跳转功能
        commonResp.setContent(JSON.parse("{code:401}"));
        return commonResp;
    }
}

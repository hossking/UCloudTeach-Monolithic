package cn.gpnusz.ucloudteach.controller;

import cn.gpnusz.ucloudteach.common.CommonResp;
import cn.gpnusz.ucloudteach.entity.AliReturnBean;
import cn.gpnusz.ucloudteach.req.CourseCommonReq;
import cn.gpnusz.ucloudteach.service.PayService;
import com.alipay.api.AlipayApiException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author h0ss
 * @description 支付接口
 * @date 2021/11/28 - 18:46
 */
@RestController
public class PayController {

    @Resource
    private PayService payService;


    /**
     * 生成交易订单的业务方法
     * 前端只需要传过来一个课程id，其他的信息由后端去数据库查询之后写入支付实体
     * @param req : 课程信息
     * @return : java.lang.String
     * @author h0ss
     */
    @PostMapping("/api/user/pay/create")
    public CommonResp<String> alipay(@RequestBody CourseCommonReq req) throws AlipayApiException {
        return payService.aliPay(req);
    }

    /**
     * 利用同步通知内容进行验签 用于页面跳转 不做业务相关操作
     * @param aliReturnBean : 请求信息
     * @author h0ss
     */
    @GetMapping("/api/user/pay/check")
    public CommonResp<Object> checkSign(AliReturnBean aliReturnBean) throws AlipayApiException {
        return payService.checkSignService(aliReturnBean);
    }


    /**
     * 开放给支付宝用以异步通知 收到通知之后就可以验签以及其他业务操作了
     * @param request : 请求信息
     * @author h0ss
     */
    @PostMapping("/api/common/pay/notify")
    public void notifyUrl(HttpServletRequest request) throws IOException, AlipayApiException {
        payService.addMember(request);
    }
}
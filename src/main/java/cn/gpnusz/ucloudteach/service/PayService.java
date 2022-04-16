package cn.gpnusz.ucloudteach.service;

import cn.dev33.satoken.stp.StpUtil;
import cn.gpnusz.ucloudteach.common.CommonResp;
import cn.gpnusz.ucloudteach.config.AlipayConfig;
import cn.gpnusz.ucloudteach.entity.AliReturnBean;
import cn.gpnusz.ucloudteach.entity.AlipayBean;
import cn.gpnusz.ucloudteach.req.CourseCommonReq;
import cn.gpnusz.ucloudteach.req.CourseMemberSaveReq;
import cn.gpnusz.ucloudteach.resp.CourseCustResp;
import cn.gpnusz.ucloudteach.service.course.CourseMemberService;
import cn.gpnusz.ucloudteach.service.course.CourseService;
import cn.gpnusz.ucloudteach.util.SnowFlake;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author h0ss
 * @description 课程支付业务层
 * @date 2021/11/28 - 18:46
 */
@Service
public class PayService {

    @Resource
    private AlipayConfig alipayConfig;

    @Resource
    private CourseService courseService;

    @Resource
    private CourseMemberService courseMemberService;

    @Resource
    private SnowFlake snowFlake;

    private static final Logger LOG = LoggerFactory.getLogger(PayService.class);


    /**
     * 用户申请加入课程，如果免费则直接添加学员返回消息，如果付费则返回的是需要跳转到的支付宝支付页面
     *
     * @param req : 请求参数【课程id】
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<java.lang.String>
     * @author h0ss
     */
    public CommonResp<String> aliPay(CourseCommonReq req) throws AlipayApiException {
        // 创建返回对象
        CommonResp<String> resp = new CommonResp<>();
        // 根据课程id获取课程相关信息写入支付信息中
        List<CourseCustResp> content = courseService.getContent(req);
        // 获取课程信息
        if (content.isEmpty()) {
            resp.setMessage("课程信息获取失败，请稍后重试");
            resp.setSuccess(false);
        } else {
            // 判断是否已经加入
            if (courseMemberService.checkMember(content.get(0).getId())) {
                // 返回消息
                resp.setContent(String.valueOf(content.get(0).getId()));
                resp.setMessage("您已成功参与课程");
                return resp;
            }
            // 判断是否免费课程
            if (new BigDecimal("0.00").compareTo(content.get(0).getPrice()) == 0) {
                // 添加到数据表中
                addMemberInfo(StpUtil.getLoginIdAsLong(), content.get(0).getId(), snowFlake.nextId());
                // 返回消息
                resp.setContent(String.valueOf(content.get(0).getId()));
                resp.setMessage("成功参与课程");
                return resp;
            }
            // 将课程信息写入支付实体
            AlipayBean alipayBean = new AlipayBean();
            alipayBean.setOut_trade_no(String.valueOf(snowFlake.nextId()));
            alipayBean.setSubject(content.get(0).getName());
            alipayBean.setTotal_amount(String.valueOf(content.get(0).getPrice()));
            // 获取当前用户id记录到auth_token中
            alipayBean.setPassback_params(StpUtil.getLoginIdAsString());
            // 课程id存入body中
            alipayBean.setBody(String.valueOf(content.get(0).getId()));

            // 获得初始化的AlipayClient
            String serverUrl = alipayConfig.gatewayUrl;
            String appId = alipayConfig.appId;
            String privateKey = alipayConfig.privateKey;
            String format = "json";
            String charset = alipayConfig.charset;
            String alipayPublicKey = alipayConfig.publicKey;
            String signType = alipayConfig.signType;
            String returnUrl = alipayConfig.returnUrl;
            String notifyUrl = alipayConfig.notifyUrl;
            AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, appId, privateKey, format, charset, alipayPublicKey, signType);

            // 设置请求参数
            AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
            // 设置页面跳转同步通知页面路径
            alipayRequest.setReturnUrl(returnUrl);
            // 设置服务器异步通知页面路径
            alipayRequest.setNotifyUrl(notifyUrl);
            // 封装参数
            alipayRequest.setBizContent(JSON.toJSONString(alipayBean));
            // 请求支付宝进行付款，返回的是支付页面的html标签数据
            String result = alipayClient.pageExecute(alipayRequest).getBody();
            resp.setContent(result);
            resp.setMessage("请求成功");
        }
        return resp;
    }


    /**
     * 利用同步通知信息进行验签的业务方法
     *
     * @param aliReturnBean : 验签信息
     * @return : cn.gpnusz.ucloudteach.common.CommonResp<java.lang.Object>
     * @author h0ss
     */
    public CommonResp<Object> checkSignService(AliReturnBean aliReturnBean) throws AlipayApiException {
        System.out.println(aliReturnBean);
        CommonResp<Object> resp = new CommonResp<>();
        LOG.info("支付宝同步通知跳转验签");
        boolean signVerified = AlipaySignature.rsaCheck(aliReturnBean.toString(), aliReturnBean.getSign(), alipayConfig.publicKey, alipayConfig.charset, alipayConfig.signType);
        if (signVerified) {
            LOG.info("验签成功-跳转到成功后页面");
            resp.setMessage("支付成功");
            resp.setSuccess(true);
        } else {
            LOG.info("验签失败-跳转到失败页面");
            resp.setMessage("支付失败");
            resp.setSuccess(false);
        }
        return resp;
    }

    /**
     * 支付宝异步通知验签 在验签完成之后进行相关业务操作
     *
     * @param request : 请求信息
     * @author h0ss
     */
    @Transactional(rollbackFor = Exception.class)
    public void addMember(HttpServletRequest request) throws UnsupportedEncodingException, AlipayApiException {
        LOG.info("接受支付宝异步通知，开始验签并准备相关业务操作");
        request.setCharacterEncoding("utf-8");
        //获取支付宝POST过来的反馈信息
        Map<String, String[]> requestParams = request.getParameterMap();
        if (requestParams.isEmpty()) {
            return;
        }
        Map<String, String> params = new HashMap<>(requestParams.size());
        for (Map.Entry<String, String[]> entry : requestParams.entrySet()) {
            String[] values = entry.getValue();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < values.length; i++) {
                if (i == values.length - 1) {
                    sb.append(values[i]);
                } else {
                    sb.append(values[i]);
                    sb.append(',');
                }
            }
            params.put(entry.getKey(), sb.toString());
        }
        // 验签
        boolean signVerified = AlipaySignature.rsaCheckV1(params, alipayConfig.publicKey, alipayConfig.charset, alipayConfig.signType);
        // 验签成功执行业务逻辑
        if (signVerified) {
            LOG.info("验签成功，开始执行业务方法");
            // 交易状态
            String status = new String(request.getParameter("trade_status").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            if ("TRADE_SUCCESS".equals(status)) {
                addMemberInfo(Long.valueOf(params.get("passback_params")), Long.valueOf(params.get("body")), Long.valueOf(params.get("out_trade_no")));
            }
        }
    }

    /**
     * 将学员信息写入【课程-学员】信息表
     *
     * @param studentId : 学员id
     * @param courseId  : 课程id
     * @param id        : 流水号
     * @author h0ss
     */
    private void addMemberInfo(Long studentId, Long courseId, Long id) {
        if (!ObjectUtils.isEmpty(studentId) && !ObjectUtils.isEmpty(courseId) && !ObjectUtils.isEmpty(id)) {
            CourseMemberSaveReq req = new CourseMemberSaveReq();
            req.setId(id);
            req.setStudentId(studentId);
            req.setCourseId(courseId);
            courseMemberService.save(req, true);
        }
    }
}
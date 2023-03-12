package com.yibingo.race.web.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.yibingo.race.common.exception.BaseException;
import com.yibingo.race.common.utils.Result;
import com.yibingo.race.common.utils.ServletUtils;
import com.yibingo.race.core.service.base.BuyOrderBaseService;
import com.yibingo.race.dal.entity.BuyOrder;
import com.yibingo.race.dal.enums.BuyOrderStatusEnum;
import com.yibingo.race.pay.AbstractAliPayApiController;
import com.yibingo.race.pay.alipay.AliPayApi;
import com.yibingo.race.pay.alipay.AliPayApiConfig;
import com.yibingo.race.pay.alipay.AliPayApiConfigKit;
import com.yibingo.race.pay.entity.AliPayBean;
import com.yibingo.race.pay.kit.PayKit;
import com.yibingo.race.pay.kit.RsaKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: Yang Xin
 * @time: 2022/7/20 11:35
 */
@Api(
        value = "支付宝支付",
        tags = "支付宝支付"
)
@RestController
@RequestMapping("core/alipay")
public class AliPayController extends AbstractAliPayApiController {


    private static final String CHARSET = "UTF-8";

    // 普通公钥模式
//     private final static String NOTIFY_URL = "/aliPay/notify_url";

    // 普通公钥模式
//    private final static String RETURN_URL = "/aliPay/return_url";


    /**
     * 证书模式
     */
    //异步通知
    private final static String NOTIFY_URL = "/core/aliPay/cert/notify/url";

    /**
     * 证书模式
     */
    //同步通知
    private final static String RETURN_URL = "/core/aliPay/cert/return/url";


    @Resource
    private AliPayBean aliPayBean;

    @Autowired
    private AliPayApiConfig aliPayApiConfig;

    @Autowired
    private BuyOrderBaseService buyOrderBaseService;

    @Override
    public AliPayApiConfig getApiConfig() throws AlipayApiException {

        try {
            aliPayApiConfig = AliPayApiConfigKit.getApiConfig(aliPayBean.getAppId());
        } catch (Exception e) {
            aliPayApiConfig = AliPayApiConfig.builder()
                    .setAppId(aliPayBean.getAppId())
                    .setAliPayPublicKey(aliPayBean.getPublicKey())
                    .setAppCertPath(aliPayBean.getAppCertPath())
                    .setAliPayCertPath(aliPayBean.getAliPayCertPath())
                    .setAliPayRootCertPath(aliPayBean.getAliPayRootCertPath())
                    .setCharset("UTF-8")
                    .setPrivateKey(aliPayBean.getPrivateKey())
                    .setServiceUrl(aliPayBean.getServerUrl())
                    .setSignType("RSA2")
                    // 普通公钥方式
                    //.build();
                    // 证书模式
                    .buildByCert();

        }
        return aliPayApiConfig;
    }

    @ApiOperation(
            value = "app支付",
            notes = "app支付"
    )
    @RequestMapping(
            value = "/app",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> appPay(@RequestParam String buyOrderId, @RequestParam String totalAmount) {
        try {
            getApiConfig();
            AliPayApiConfigKit.setThreadLocalAliPayApiConfig(aliPayApiConfig);

            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
            model.setBody("购买");
            model.setSubject("魅塔艺术");
            //这是外部订单号  商家网站自己的唯一号
            model.setOutTradeNo(buyOrderId);
            model.setTimeoutExpress("30m");
            model.setTotalAmount(totalAmount);
            //这俩参数好像没用
            model.setPassbackParams("callback params");
            model.setProductCode("QUICK_MSECURITY_PAY");

            String orderInfo = AliPayApi.appPayToResponse(model, aliPayBean.getDomain() + NOTIFY_URL).getBody();

            return Result.success(orderInfo).map();

        } catch (AlipayApiException e) {
            throw new BaseException(e.toString());
        }
    }

    /*@ApiOperation(
            value = "手机网站h5支付",
            notes = "手机网站h5支付"
    )*/
    @RequestMapping(
            value = "/h5/nosdk",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public void wapPayNoSdk(HttpServletResponse response) {
        try {
            Date date = DateUtil.date();
            DateTime timeExpire = DateUtil.offsetMinute(date, 30);

            AliPayApiConfig aliPayApiConfig = AliPayApiConfigKit.getAliPayApiConfig();
            Map<String, String> paramsMap = new HashMap<>();
            paramsMap.put("app_id", aliPayApiConfig.getAppId());
            paramsMap.put("method", "alipay.trade.wap.pay");
            paramsMap.put("return_url", aliPayBean.getDomain() + RETURN_URL);
            paramsMap.put("charset", aliPayApiConfig.getCharset());
            paramsMap.put("sign_type", aliPayApiConfig.getSignType());
            paramsMap.put("timestamp", DateUtil.format(date, "yyyy-MM-dd HH:mm:ss"));
            paramsMap.put("version", "1.0");
            paramsMap.put("notify_url", aliPayBean.getDomain() + NOTIFY_URL);

            Map<String, String> bizMap = new HashMap<>();
            bizMap.put("body", "购买");
            bizMap.put("subject", "魅塔艺术");
            //todo 这里取值可能有点问题
            bizMap.put("out_trade_no", response.getHeader("buyOrderId"));
            bizMap.put("total_amount", response.getHeader("totalAmount"));

            bizMap.put("time_expire", DateUtil.format(timeExpire, "yyyy-MM-dd HH:mm:ss"));

            bizMap.put("product_code", "QUICK_WAP_WAY");
            paramsMap.put("biz_content", JSON.toJSONString(bizMap));

            String content = PayKit.createLinkString(paramsMap);

            System.out.println(content);

            String encrypt = RsaKit.encryptByPrivateKey(content, aliPayApiConfig.getPrivateKey());
            System.out.println(encrypt);
//            encrypt = AlipaySignature.rsaSign(content,aliPayApiConfig.getPrivateKey(), "UTF-8","RSA2");
//            System.out.println(encrypt);
            paramsMap.put("sign", encrypt);

            String url = aliPayApiConfig.getServiceUrl() + "?" + PayKit.createLinkString(paramsMap, true);
            System.out.println(url);
            response.sendRedirect(url);

        } catch (Exception e) {
            throw new BaseException(e.toString());

        }
    }

    /*
     * buyOrderId  和  totalAmountId是要放在请求头里的
     * */
    @ApiOperation(
            value = "手机网站h5支付",
            notes = "手机网站h5支付 "
    )
    @RequestMapping(
            value = "/h5",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public void wapPay(HttpServletResponse response, @RequestParam String buyOrderId, @RequestParam String totalAmount) throws Exception {
        getApiConfig();
        AliPayApiConfigKit.setThreadLocalAliPayApiConfig(aliPayApiConfig);

        String body = "我是测试数据-By Javen";
        String subject = "Javen Wap支付测试";
        String passBackParams = "1";
        String returnUrl = aliPayBean.getDomain() + RETURN_URL;
        String notifyUrl = aliPayBean.getDomain() + NOTIFY_URL;
        //String buyOrderId = response.getHeader("buyOrderId");
        //String totalAmount = response.getHeader("totalAmount");

        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setBody(body);
        model.setSubject(subject);
        model.setTotalAmount(totalAmount);
        model.setPassbackParams(passBackParams);
        model.setTimeoutExpress("30m");

        model.setOutTradeNo(buyOrderId);
        model.setProductCode("QUICK_WAP_PAY");

        try {
            AliPayApi.wapPay(response, model, returnUrl, notifyUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * 由于同步通知和异步通知都可以作为支付完成的凭证，
     * 且异步通知支付宝一定会确保发送给商家服务端。为了简化集成流程，
     * 商家可以将同步结果仅作为一个支付结束的通知（忽略执行校验），实际支付是否成功，完全依赖服务端异步通知。
     * */


    /* @ApiOperation(
             value = "普通公钥的同步通知",
             notes = "普通公钥的同步通知"
     )*/
    @RequestMapping(
            value = "/return/url",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public String returnUrl(HttpServletRequest request) {
        try {
            // 获取支付宝GET过来反馈信息
            Map<String, String> map = AliPayApi.toMap(request);
            for (Map.Entry<String, String> entry : map.entrySet()) {
                System.out.println(entry.getKey() + " = " + entry.getValue());
            }

            boolean verifyResult = AlipaySignature.rsaCheckV1(map, aliPayBean.getPublicKey(), "UTF-8",
                    "RSA2");

            if (verifyResult) {
                // TODO 请在这里加上商户的业务逻辑程序代码
                System.out.println("return_url 验证成功");

                return "success";
            } else {
                System.out.println("return_url 验证失败");
                // TODO
                return "failure";
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return "failure";
        }
    }


    @ApiOperation(
            value = "证书模式的同步通知",
            notes = "证书模式的同步通知"
    )
    @RequestMapping(
            value = "/cert/return/url",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public String certReturnUrl(HttpServletRequest request) {
        try {
            HttpServletResponse response = ServletUtils.getResponse();
            response.setContentType("text/html;charset=" + CHARSET);
            PrintWriter out = response.getWriter();


            // 获取支付宝GET过来反馈信息
            Map<String, String> map = AliPayApi.toMap(request);
            for (Map.Entry<String, String> entry : map.entrySet()) {
                System.out.println(entry.getKey() + " = " + entry.getValue());
            }

            boolean verifyResult = AlipaySignature.rsaCertCheckV1(map, aliPayBean.getAliPayCertPath(), "UTF-8",
                    "RSA2");

            if (verifyResult) {
                // TODO 请在这里加上商户的业务逻辑程序代码

                System.out.println("certReturnUrl 验证成功");
                //商户订单号
                String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

                //支付宝交易号
                String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

                //付款金额
                String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");

                out.println("参数如下<br/>&nbsp;&nbsp;&nbsp;&nbsp;out_trade_no : " + out_trade_no + "<br/>&nbsp;&nbsp;&nbsp;&nbsp;trade_no : " + trade_no + "<br/>&nbsp;&nbsp;&nbsp;&nbsp;total_amount : " + total_amount);

                return "success";
            } else {
                System.out.println("certReturnUrl 验证失败");
                // TODO
                return "failure";
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return "failure";
        } catch (Exception e) {
            throw new BaseException(e.toString());
        }
    }

    @ApiOperation(
            value = "证书模式的异步通知",
            notes = "证书模式的异步通知"
    )
    @RequestMapping(
            value = "/cert/notify/url",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public String certNotifyUrl(HttpServletRequest request) {
        try {
            HttpServletResponse response = ServletUtils.getResponse();


            // 获取支付宝POST过来反馈信息
            Map<String, String> params = AliPayApi.toMap(request);

            for (Map.Entry<String, String> entry : params.entrySet()) {
                //取各个参数的信息
                System.out.println(entry.getKey() + " = " + entry.getValue());
            }

            //证书验签
            boolean verifyResult = AlipaySignature.rsaCertCheckV1(params, aliPayBean.getAliPayCertPath(), "UTF-8", "RSA2");

            if (verifyResult) {
                /* 请在这里加上商户的业务逻辑程序代码 异步通知可能出现订单重复通知 需要做去重处理*/

                System.out.println("certNotifyUrl 验证成功succcess");

                String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
                String tradeStatus = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
                BuyOrder buyOrder = buyOrderBaseService.getById(out_trade_no);

                //todo 状态有点问题，记得核对
                if (buyOrder.getStatus().equals(BuyOrderStatusEnum.Success.getKey()) ||
                        buyOrder.getStatus().equals(BuyOrderStatusEnum.WaitGive.getKey())) {
                    //意味着之前处理过这笔订单，直接返回不再修改了

                    //支付宝接口必须打印sucess
                    return "success";
                }

                //如果说交易成功，则修改订单表中的逻辑
                if (tradeStatus.equals("TRADE_SUCCESS") || tradeStatus.equals("TRADE_FINISHED")) {
                    buyOrder.setStatus(BuyOrderStatusEnum.WaitGive.getKey());
                    buyOrderBaseService.updateById(buyOrder);
                }

                //支付宝接口必须打印sucess
                return "success";

            } else {
                System.out.println("certNotifyUrl 验证失败");
                // TODO
                return "failure";
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return "failure";
        } catch (Exception e) {
            throw new BaseException(e.toString());
        }
    }

    /*@ApiOperation(
            value = "普通公钥的异步通知",
            notes = "普通公钥的异步通知"
    )*/
    @RequestMapping(
            value = "/notify/url",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public String notifyUrl(HttpServletRequest request) {
        try {
            // 获取支付宝POST过来反馈信息
            Map<String, String> params = AliPayApi.toMap(request);

            for (Map.Entry<String, String> entry : params.entrySet()) {
                System.out.println(entry.getKey() + " = " + entry.getValue());
            }

            boolean verifyResult = AlipaySignature.rsaCheckV1(params, aliPayBean.getPublicKey(), "UTF-8", "RSA2");

            if (verifyResult) {
                // TODO 请在这里加上商户的业务逻辑程序代码 异步通知可能出现订单重复通知 需要做去重处理
                System.out.println("notify_url 验证成功succcess");
                return "success";
            } else {
                System.out.println("notify_url 验证失败");
                // TODO
                return "failure";
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return "failure";
        }
    }


}

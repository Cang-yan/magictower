package com.yibingo.race.pay;

import com.alipay.api.AlipayApiException;
import com.yibingo.race.pay.alipay.AliPayApiConfig;


/**
 * @author Javen
 */
public abstract class AbstractAliPayApiController {
    /**
     * 获取支付宝配置
     *
     * @return {@link AliPayApiConfig} 支付宝配置
     * @throws AlipayApiException 支付宝 Api 异常
     */
    public abstract AliPayApiConfig getApiConfig() throws AlipayApiException;
}

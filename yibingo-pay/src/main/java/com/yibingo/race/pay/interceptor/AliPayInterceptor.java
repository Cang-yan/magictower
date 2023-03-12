package com.yibingo.race.pay.interceptor;

import com.alipay.api.AlipayApiException;
import com.yibingo.race.pay.AbstractAliPayApiController;
import com.yibingo.race.pay.alipay.AliPayApiConfigKit;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 *
 */
public class AliPayInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws AlipayApiException {
        // 获取处理method
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod method = (HandlerMethod) handler;
        Object controller = method.getBean();
        if(controller instanceof AbstractAliPayApiController){
            AliPayApiConfigKit.setThreadLocalAliPayApiConfig(((AbstractAliPayApiController) controller).getApiConfig());
        }
        return true;
        /*if (HandlerMethod.class.equals(handler.getClass())) {
            HandlerMethod method = (HandlerMethod) handler;
            Object controller = method.getBean();
            if (!(controller instanceof AbstractAliPayApiController)) {
                throw new RuntimeException("控制器需要继承 AbstractAliPayApiController");
            }
            AliPayApiConfigKit.setThreadLocalAliPayApiConfig(((AbstractAliPayApiController) controller).getApiConfig());
            return true;
        }
        return false;*/
    }
}
package com.yibingo.race.web.exception;

import cn.dev33.satoken.exception.SaTokenException;
import cn.hutool.core.exceptions.ValidateException;
import com.yibingo.race.common.constant.ResponseCodeConstants;
import com.yibingo.race.common.exception.BaseException;
import com.yibingo.race.common.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Map;

/**
 * 异常处理器
 *
 * @author smalljop
 */
@RestControllerAdvice
@Slf4j
public class BaseExceptionHandler {


    /**
     * 处理自定义异常
     */
    @ExceptionHandler(BaseException.class)
    public Map<String, Object> handleBaseException(BaseException e) {
        log.error(e.getMessage(), e);
        return Result.failed(e.getCode(), e.getMsg()).map();

    }

    @ExceptionHandler(SaTokenException.class)
    public Map<String, Object> handleSatokenException(SaTokenException e) {
        log.error(e.getMessage(), e);
        return Result.failed(e.getCode(),e.getMessage()).map();

    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public Map<String, Object> handlerNoFoundException(NoHandlerFoundException e) {
        log.error(e.getMessage(), e);
        return Result.failed(ResponseCodeConstants.NOT_FOUND, "路径不存在，请检查路径是否正确" ).map();
    }


    //    @ExceptionHandler(AuthorizationException.class)
//    public Result handlerAuthorizationException(AuthorizationException e) {
//        log.error(e.getMessage(), e);
//        return Result.failed(ResponseCodeConstants.UNAUTHORIZED, "登录状态过期");
//    }

    @ExceptionHandler(NullPointerException.class)
    public Map<String, Object> handlerNullPointerException(NullPointerException e) {
        log.error(e.getMessage(), e);
        return Result.failed(e.toString()).map();
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public Map<String, Object> handleDuplicateKeyException(DuplicateKeyException e) {
        log.error(e.getMessage(), e);
        return Result.failed("数据库中已存在该记录" ).map();
    }

    @ExceptionHandler({ValidateException.class})
    public Map<String, Object> handleValidateException(ValidateException e) {
        log.error(e.getMessage(), e);
        return Result.failed(e.getMessage()).map();
    }

    @ResponseBody
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public Map<String, Object> exceptionHandler(MissingServletRequestParameterException e) {
        log.error("System Exception:{}" , e.getMessage());
        return Result.failed(e.getMessage()).map();
    }

    /**
     * 处理微信异常
     * 没有配置错误也不返回500给前端 降低使用配置
     */
//    @ExceptionHandler(WxErrorException.class)
//    public Result handleWxException(WxErrorException e) {
//        log.error(e.getMessage(), e);
//        return Result.success(e.getMessage());
//    }
    @ExceptionHandler(Exception.class)
    public Map<String, Object> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return Result.failed(e.getMessage()).map();
    }
}

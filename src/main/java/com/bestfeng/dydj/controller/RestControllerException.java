package com.bestfeng.dydj.controller;

import com.bestfeng.dydj.enums.ApiErrorCodeEnums;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.aurochsframework.boot.commons.api.CommonResult;
import org.aurochsframework.boot.core.exceptions.BusinessException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeoutException;

@Slf4j
@RestControllerAdvice
public class RestControllerException {

    @ExceptionHandler(value = Throwable.class)
    @ResponseBody
    public CommonResult<Void> handleException(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        log.error("异常消息msg={}",exception.getMessage(),exception);
        Throwable rootException = Throwables.getRootCause(exception);
        ApiErrorCodeEnums responseCode = ApiErrorCodeEnums.INTERNAL_SERVER_ERROR;
        Integer code = responseCode.getCode();
        String text = responseCode.getText();
        if (rootException instanceof BusinessException) {
            BusinessException businessException = (BusinessException) exception;
            return CommonResult.custom(businessException.getStatus(), businessException.getMessage(), null);
        }
        if (rootException instanceof IllegalArgumentException) {
            return CommonResult.custom(ApiErrorCodeEnums.ILLEGAL_ARGUMENT_ERROR.getCode(), exception.getMessage(), null);
        }
        if(rootException instanceof HttpRequestMethodNotSupportedException){
            return CommonResult.custom(ApiErrorCodeEnums.ILLEGAL_ACCESS_ERROR.getCode(),ApiErrorCodeEnums.ILLEGAL_ACCESS_ERROR.getText(),null);
        }
        if (rootException instanceof TimeoutException) {
            return CommonResult.custom(ApiErrorCodeEnums.TIME_OUT_ERROR.getCode(),ApiErrorCodeEnums.TIME_OUT_ERROR.getText(),null);
        }
        return CommonResult.custom(code, text, null);
    }
}

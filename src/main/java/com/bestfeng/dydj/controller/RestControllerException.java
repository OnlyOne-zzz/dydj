package com.bestfeng.dydj.controller;

import com.bestfeng.dydj.enums.ApiErrorCodeEnums;
import lombok.extern.slf4j.Slf4j;
import org.aurochsframework.boot.commons.api.CommonResult;
import org.aurochsframework.boot.core.exceptions.BusinessException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestControllerAdvice
public class RestControllerException {


//    @ExceptionHandler(BusinessException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public CommonResult<Object> handleException(BusinessException exception) {
//        return CommonResult.custom(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), exception.getCode());
//    }

    @ExceptionHandler(value = Throwable.class)
    @ResponseBody
    public CommonResult<Void> handleException(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        ApiErrorCodeEnums responseCode = ApiErrorCodeEnums.INTERNAL_SERVER_ERROR;
        Integer code = responseCode.getCode();
        String text = responseCode.getText();
        if (exception instanceof BusinessException) {
            BusinessException businessException = (BusinessException) exception;
            return CommonResult.custom(businessException.getStatus(), businessException.getMessage(), null);
        }
        if (exception instanceof IllegalArgumentException) {
            return CommonResult.custom(ApiErrorCodeEnums.ILLEGAL_ARGUMENT_ERROR.getCode(), exception.getMessage(), null);
        }
        return CommonResult.custom(code, text, null);
    }
}

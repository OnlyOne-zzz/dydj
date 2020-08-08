package com.bestfeng.dydj.controller;

import lombok.extern.slf4j.Slf4j;
import org.aurochsframework.boot.commons.api.CommonResult;
import org.aurochsframework.boot.core.exceptions.AccessDenyException;
import org.aurochsframework.boot.core.exceptions.BusinessException;
import org.aurochsframework.boot.core.exceptions.UnAuthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class RestControllerException {


    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult<Object> handleException(BusinessException exception) {
        return CommonResult.custom(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), exception.getCode());
    }
}

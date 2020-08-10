package com.bestfeng.dydj.controller;

import com.alibaba.fastjson.JSON;
import com.bestfeng.dydj.configuration.request.TestRequest;
import com.bestfeng.dydj.manager.request.SignVerifySupport;
import lombok.extern.slf4j.Slf4j;
import org.aurochsframework.boot.commons.api.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bsetfeng
 * @since 1.0
 **/
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    private SignVerifySupport signVerifySupport;

    @PostMapping
    public CommonResult<Object> test(@RequestBody TestRequest request) {
        log.info("test 请求参数={}", JSON.toJSONString(request));
        signVerifySupport.check(request);
        return CommonResult.success("测试请求成功");
    }
}

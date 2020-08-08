package com.bestfeng.dydj.controller;

import com.bestfeng.dydj.configuration.request.TestRequest;
import com.bestfeng.dydj.manager.request.SignVerifySupport;
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
public class TestController {

    @Autowired
    private SignVerifySupport signVerifySupport;

    @PostMapping
    public CommonResult<Object> test(@RequestBody TestRequest request) {
        signVerifySupport.check(request);
        return CommonResult.success("测试请求成功");
    }
}

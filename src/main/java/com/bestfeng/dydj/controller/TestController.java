package com.bestfeng.dydj.controller;

import com.bestfeng.dydj.controller.request.TestRequest;
import com.bestfeng.dydj.manager.request.SignVerifySupport;
import com.bestfeng.dydj.utils.FastJsons;
import lombok.extern.slf4j.Slf4j;
import org.aurochsframework.boot.commons.api.CommonResult;
import org.aurochsframework.boot.core.exceptions.BusinessException;
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
//    @SignValidated
    public CommonResult<Object> testException(@RequestBody TestRequest request) {
        log.info("test 请求参数={}", FastJsons.convertObjectToJSON(request));
        Integer type = request.getType();
        switch (type){
            case 0:
                int i = 10;
                int j = 0;
                int m = i/j;
                break;

            case 1:
                throw new IllegalArgumentException("参数错了");
            case 2:
                throw new BusinessException("业务异常了");
            case 3:
                throw new NullPointerException("空指针异常");
            case 4:
                throw new RuntimeException("运行时异常");
            case 5:
                try {
                    Thread.sleep(10000l);
                }catch (Exception e){

                }
        }
//        signVerifySupport.check(request);
        return CommonResult.success("测试请求成功");
    }
}

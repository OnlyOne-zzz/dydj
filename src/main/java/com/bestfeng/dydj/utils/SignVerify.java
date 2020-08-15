package com.bestfeng.dydj.utils;

import com.alibaba.fastjson.JSON;
import com.bestfeng.dydj.controller.request.BasicRequest;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author bsetfeng
 * @since 1.0
 **/
public class SignVerify {

    private final static String SIGN_KEY = "111";


    public <T extends BasicRequest> void check(T t) {

    }


    protected boolean check(String sign, String localSign) {
        return localSign.equals(sign);
    }

    protected String signParamsSplicing(Object params) {
        return JSON.parseObject(JSON.toJSONString(params))
                .entrySet()
                .stream()
                .filter(entry -> "sign".equals(entry.getKey()))
                .sorted(Map.Entry.comparingByKey())
                .reduce("", (e1, e2) -> e2.getKey().concat("=").concat(e2.getValue().toString()).concat("&"), (e1, e2) -> e1);

    }

    protected String getCompleteSignParams(String singParams) {
        return singParams.concat("key=").concat(SIGN_KEY);
    }

    protected String getMd5Sign(String completeSign) {
        return DigestUtils.md5DigestAsHex(completeSign.getBytes(StandardCharsets.UTF_8));
    }


}

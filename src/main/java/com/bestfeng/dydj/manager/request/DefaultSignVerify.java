package com.bestfeng.dydj.manager.request;

import com.bestfeng.dydj.controller.request.BasicRequest;
import com.bestfeng.dydj.enums.ApiErrorCodeEnums;
import com.bestfeng.dydj.utils.FastJsons;
import lombok.extern.slf4j.Slf4j;
import org.aurochsframework.boot.core.exceptions.BusinessException;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author bsetfeng
 * @since 1.0
 **/
@Slf4j
@Component
public class DefaultSignVerify implements SignVerifySupport {

    @Override
    public <T extends BasicRequest> void check(T t) {
        check(t.getSign(), t);
    }


    public void check(String sign, Object params) {
        log.warn("验签参数:{}", sign);
        if (sign == null || sign.length() < 15) {
            log.warn("签名格式异常。");
            throw new BusinessException(ApiErrorCodeEnums.SIGN_ERR.getText(), ApiErrorCodeEnums.SIGN_ERR.getCode());
        }
        String timeMillis = sign.substring(0, 14);
        String localSign = getMd5Sign(timeMillis.concat(SIGN_KEY));
        if (StringUtils.isEmpty(sign) || !localSign.equals(sign)) {
            log.warn("请求验签失败。");
            throw new BusinessException(ApiErrorCodeEnums.SIGN_ERR.getText(), ApiErrorCodeEnums.SIGN_ERR.getCode());
        }
    }

    protected String signParamsSplicing(Object params) {
        return FastJsons.convertObjectToJSONObject(params)
                .entrySet()
                .stream()
                .filter(entry -> !"sign".equals(entry.getKey()) && !StringUtils.isEmpty(entry.getValue()))
                .sorted(Map.Entry.comparingByKey())
                .peek(entry -> {
                    System.out.println(entry.getKey());
                })
                .reduce("", (e1, e2) -> e1.concat(e2.getKey().concat("=").concat(e2.getValue().toString()).concat("&")), (e1, e2) -> e1);

    }


    protected String getCompleteSignParams(String singParams) {
        String completeSignParams = singParams.concat("key=").concat(SIGN_KEY);
        log.warn("验签参数:{}", completeSignParams);
        return completeSignParams;
    }

    protected String getMd5Sign(String completeSign) {
        return DigestUtils.md5DigestAsHex(completeSign.getBytes(StandardCharsets.UTF_8));
    }

}

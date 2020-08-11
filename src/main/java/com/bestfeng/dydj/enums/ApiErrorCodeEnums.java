package com.bestfeng.dydj.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.aurochsframework.boot.core.enums.EnumDict;

/**
 * @author bsetfeng
 * @since 1.0
 **/
@Getter
@AllArgsConstructor
public enum  ApiErrorCodeEnums implements EnumDict<String> {
    INTERNAL_SERVER_ERROR( 500,"服务器开了一会儿小差，请稍后再试"),
    SIGN_ERR(10001, "验签失败");

    private Integer code;
    private String text;


    @Override
    public String getValue() {
        return null;
    }
}

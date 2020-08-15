package com.bestfeng.dydj.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.aurochsframework.boot.core.enums.EnumDict;
import org.aurochsframework.boot.core.exceptions.BusinessException;

/**
 * @author bsetfeng
 * @since 1.0
 **/
@Getter
@AllArgsConstructor
public enum ApiErrorCodeEnums implements EnumDict<String> {

    USER_NOT_FOUND(40001, "未找到对应的用户"),

    INTERNAL_SERVER_ERROR(500, "服务器开了一会儿小差，请稍后再试"),
    ILLEGAL_ARGUMENT_ERROR(501, "参数异常"),
    SIGN_ERR(10001, "验签失败");

    private Integer code;
    private String text;


    @Override
    public String getValue() {
        return null;
    }

    public static BusinessException businessException(ApiErrorCodeEnums errorCodeEnums) {
        return new BusinessException(errorCodeEnums.getText(), errorCodeEnums.getCode());
    }

}

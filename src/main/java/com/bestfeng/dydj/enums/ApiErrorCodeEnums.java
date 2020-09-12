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

    /**通用异常,展示给用户看*/
    INTERNAL_SERVER_ERROR(501, "服务器开了一会儿小差，请稍后再试"),
    
    ILLEGAL_ARGUMENT_ERROR(502, "参数异常"),

    NOTE_CAN_NOT_SERVICE_ERROR(601, "您好,技师:%s暂时不可预约,请您更换技师"),


    SIGN_ERR(10001, "验签失败"),

    TIME_OUT_ERROR(9998, "超时访问"),
    /**不支持的访问方法*/
    ILLEGAL_ACCESS_ERROR(9999, "非法访问");


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

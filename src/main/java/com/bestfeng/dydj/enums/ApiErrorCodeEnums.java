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

    SIGN_ERR("验签失败", "10001");

    private String text;

    private String value;

}

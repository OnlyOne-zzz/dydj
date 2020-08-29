package com.bestfeng.dydj.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.aurochsframework.boot.core.enums.EnumDict;

/**
 * @author bsetfeng
 * @since 1.0
 **/
@Getter
@AllArgsConstructor
@Slf4j
public enum CouponTypeEnums implements EnumDict<Byte> {

    NOT_USED((byte)0, "未使用"),
    USED((byte)1, "已使用"),
    INVALID((byte)2, "已失效");

    private Byte value;

    private String text;

    public static CouponTypeEnums ofValue(int value) {
        return EnumDict.findByValue(CouponTypeEnums.class, value)
                .orElseGet(() -> {
                    log.warn("未找到优惠券类型:{}", value);
                    return null;
                });
    }

}

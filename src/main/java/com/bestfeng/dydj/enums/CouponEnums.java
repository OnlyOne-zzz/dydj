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
public enum CouponEnums implements EnumDict<Integer> {

    COUPON_REGISTER(1, "注册赠送优惠券");

    private Integer value;

    private String text;

    public static CouponEnums ofValue(int value) {
        return EnumDict.findByValue(CouponEnums.class, value)
                .orElseGet(() -> {
                    log.warn("未找到优惠券类型:{}", value);
                    return null;
                });
    }

}

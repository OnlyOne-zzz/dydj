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
public enum TravelTypeEnums implements EnumDict<Integer> {

    TAXI(1, "出租车"),
    SUBWAY(2, "公交地铁");

    private Integer value;

    private String text;

    public static TravelTypeEnums ofValue(int value) {
        return EnumDict.findByValue(TravelTypeEnums.class, value)
                .orElseGet(() -> {
                    log.warn("未找到出行类型:{}", value);
                    return null;
                });
    }

}

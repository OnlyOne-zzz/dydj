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
public enum NoteServiceStatusEnums implements EnumDict<Integer> {

    SERVICEABLE(1, "可服务"),
    IN_SERVICE(2, "服务中");

    private Integer value;

    private String text;

    public static NoteServiceStatusEnums ofValue(int value) {
        return EnumDict.findByValue(NoteServiceStatusEnums.class, value)
                .orElseGet(() -> {
                    log.warn("未找技术服务状态类型:{}", value);
                    return null;
                });
    }

}

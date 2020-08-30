package com.bestfeng.dydj.manager.wechat.template;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author bsetfeng
 * @since 1.0
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TemplateValueWrapper {

    private String value;

    /**
     * 字体颜色 #173177
     */
    private String color;
}

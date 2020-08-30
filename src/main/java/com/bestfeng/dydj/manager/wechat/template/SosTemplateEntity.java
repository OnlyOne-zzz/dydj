package com.bestfeng.dydj.manager.wechat.template;

import lombok.Getter;
import lombok.Setter;

/**
 * 发送模板body
 * @author bsetfeng
 * @since 1.0
 **/
@Getter
@Setter
public class SosTemplateEntity {

    /**
     * openId
     */
    private String touser;

    private String template_id;

    private String url;

    private TemplateWrapperEntity data;
}

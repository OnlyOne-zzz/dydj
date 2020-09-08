package com.bestfeng.dydj.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author bsetfeng
 * @since 1.0
 **/
@ConfigurationProperties(prefix = "local.access")
@Getter
@Setter
public class LocalAccessConfig {

    private String uri = "https://dydj.gotod.cn/attachment/";
}

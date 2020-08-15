package com.bestfeng.dydj.controller.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author bsetfeng
 * @since 1.0
 **/
@Getter
@Setter
public class BasicRequest implements Serializable {

    @ApiModelProperty("签名")
    private String sign;

    @ApiModelProperty("请求时间")
    private Date requestTime;
}

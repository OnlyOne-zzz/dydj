package com.bestfeng.dydj.controller.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TravelRequest extends BasicRequest{

    @ApiModelProperty("出行方式")
    private Integer type;

    @ApiModelProperty("间隔距离")
    private String distance;
}

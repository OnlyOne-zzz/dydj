package com.bestfeng.dydj.controller.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author bsetfeng
 * @since 1.0
 **/
@Getter
@Setter
public class NoteSosMessageRequest extends BasicRequest{

    private int noteId;

    @ApiModelProperty("纬度")
    private String lat;

    @ApiModelProperty("经度")
    private String lng;
}

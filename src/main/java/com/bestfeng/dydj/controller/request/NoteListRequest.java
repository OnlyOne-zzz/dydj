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
public class NoteListRequest extends BasicRequest{

    private String orderColumn;

    private String orderType;

    @ApiModelProperty("技师状态")
    private Integer serviceStatus;
}

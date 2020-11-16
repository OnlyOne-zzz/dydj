package com.bestfeng.dydj.controller.request;

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
public class CustomizeOnlineIntervalRequest implements Serializable {

    private Date startTime;

    private Date endTime;

    private Integer loginid;
}

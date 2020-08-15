package com.bestfeng.dydj.controller.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author bsetfeng
 * @since 1.0
 **/
@Getter
@Setter
public class CouponOrderReceiveRequest extends BasicRequest {

    private int type;

    private int uid;
}

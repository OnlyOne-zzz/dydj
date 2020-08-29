package com.bestfeng.dydj.controller.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author bsetfeng
 * @since 1.0
 **/
@Getter
@Setter
public class UserAvailableCouponListRequest extends BasicRequest {

    private int uid;

    private int contentId;
}

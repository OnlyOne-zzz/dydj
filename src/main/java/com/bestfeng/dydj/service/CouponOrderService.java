package com.bestfeng.dydj.service;

import com.bestfeng.dydj.mbg.model.CouponOrder;
import org.aurochsframework.boot.commons.service.GeneralService;

/**
 * 订单优惠券
 *
 * @author bsetfeng
 * @since 1.0
 **/
public interface CouponOrderService extends GeneralService<CouponOrder> {


    /**
     * 优惠券赠送
     *
     * @param type 优惠券
     * @param uid  用户ID
     */
    void receive(int type, int uid);

}

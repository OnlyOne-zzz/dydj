package com.bestfeng.dydj.service;

import com.bestfeng.dydj.mbg.model.CouponOrder;
import org.aurochsframework.boot.commons.service.GeneralService;

import java.util.List;

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
    void receive(Integer type, Integer uid);

    /**
     * 用户优惠券列表
     *
     * @param uid
     * @return
     */
    List<CouponOrder> userCouponList(Integer uid, Integer status);


    /**
     * 优惠券使用
     *
     * @param couponOrderId 优惠券ID
     */
    void use(Integer couponOrderId);

    /**
     * 根据项目ID查询用户可使用的优惠券列表
     *
     * @param contentId 项目内容ID
     * @param uid       用户ID
     * @return
     */
    List<CouponOrder> availableCouponList(Integer contentId, Integer uid);

}

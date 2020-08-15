package com.bestfeng.dydj.controller;

import com.bestfeng.dydj.mbg.model.Coupon;
import com.bestfeng.dydj.service.CouponService;
import io.swagger.annotations.Api;
import org.aurochsframework.boot.commons.controller.GeneralCrudController;
import org.aurochsframework.boot.commons.service.GeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bsetfeng
 * @since 1.0
 **/
@RestController
@RequestMapping("/coupon")
@Api(tags = "CouponController", description = "优惠券管理")
public class CouponController implements GeneralCrudController<Coupon> {


    @Autowired
    private CouponService couponService;


    @Override
    public GeneralService<Coupon> getService() {
        return couponService;
    }
}

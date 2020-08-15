package com.bestfeng.dydj.controller;

import com.bestfeng.dydj.annotation.SignValidated;
import com.bestfeng.dydj.controller.request.CouponOrderReceiveRequest;
import com.bestfeng.dydj.mbg.model.CouponOrder;
import com.bestfeng.dydj.service.CouponOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aurochsframework.boot.commons.api.CommonResult;
import org.aurochsframework.boot.commons.controller.GeneralCrudController;
import org.aurochsframework.boot.commons.service.GeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bsetfeng
 * @since 1.0
 **/
@RestController
@RequestMapping("/coupon-order")
@Api(tags = "CouponOrderController", description = "订单优惠券管理")
@SignValidated
public class CouponOrderController implements GeneralCrudController<CouponOrder> {


    @Autowired
    private CouponOrderService couponOrderService;


    @Override
    public GeneralService<CouponOrder> getService() {
        return couponOrderService;
    }


    @PostMapping("/receive")
    @ApiOperation("新增订单优惠券")
    public CommonResult<Object> receive(CouponOrderReceiveRequest receiveRequest) {
        couponOrderService.receive(receiveRequest.getType(), receiveRequest.getUid());
        return CommonResult.success("操作成功");
    }
}

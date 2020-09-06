package com.bestfeng.dydj.controller;

import com.bestfeng.dydj.annotation.SignValidated;
import com.bestfeng.dydj.controller.request.CouponOrderReceiveRequest;
import com.bestfeng.dydj.controller.request.UserAvailableCouponListRequest;
import com.bestfeng.dydj.controller.request.UserCouponListRequest;
import com.bestfeng.dydj.service.CouponOrderService;
import com.bestfeng.dydj.vo.CouponOrderVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.aurochsframework.boot.commons.api.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author bsetfeng
 * @since 1.0
 **/
@RestController
@RequestMapping("/coupon-order")
@Api(tags = "CouponOrderController", description = "订单优惠券管理")
@SignValidated
public class CouponOrderController {


    @Autowired
    private CouponOrderService couponOrderService;


    @PostMapping("/receive")
    @ApiOperation("新增订单优惠券")
    public CommonResult<Object> receive(@RequestBody CouponOrderReceiveRequest receiveRequest) {
        couponOrderService.receive(receiveRequest.getType(), receiveRequest.getUid());
        return CommonResult.success("操作成功");
    }

    @PostMapping("/_user/list")
    @ApiOperation("用户优惠券列表")
    public CommonResult<List<CouponOrderVo>> userCouponList(@RequestBody UserCouponListRequest request) {
        return CommonResult.success(couponOrderService.userCouponList(request.getUid(), request.getState())
                .stream()
                .map(CouponOrderVo::of)
                .collect(Collectors.toList())
        );
    }

    @PostMapping("/user-available/list")
    @ApiOperation("用户可使用的优惠券列表")
    public CommonResult<List<CouponOrderVo>> availableCouponList(@RequestBody UserAvailableCouponListRequest request) {
        return CommonResult.success(couponOrderService.availableCouponList(request.getContentId(), request.getUid())
                .stream()
                .map(CouponOrderVo::of)
                .collect(Collectors.toList())
        );
    }

}

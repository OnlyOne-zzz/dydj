package com.bestfeng.dydj.controller;

import com.bestfeng.dydj.annotation.SignValidated;
import com.bestfeng.dydj.dto.OrderDto;
import com.bestfeng.dydj.mbg.model.Order;
import com.bestfeng.dydj.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.aurochsframework.boot.commons.api.CommonResult;
import org.aurochsframework.boot.commons.controller.GeneralCrudController;
import org.aurochsframework.boot.commons.service.GeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author bsetfeng
 * @since 1.0
 **/
@RestController
@RequestMapping("/order")
@Api(tags = "OrderController", description = "订单管理")
@SignValidated
public class OrderController implements GeneralCrudController<Order> {


    @Autowired
    private OrderService orderService;


    @Override
    public GeneralService<Order> getService() {
        return orderService;
    }

    @PostMapping("/saveOrder")
    @ApiOperation(value = "创建订单")
    public CommonResult<Void> saveOrder(@RequestBody OrderDto orderDto){
        Assert.notNull(orderDto,"订单参数不能为空");
        Assert.notNull(orderDto.getContentId(),"项目Id不能为空");
        Assert.notNull(orderDto.getNoteid(),"技师Id不能为空");
        Assert.notNull(orderDto.getMoney(),"项目金额不能为空");
        Assert.notNull(orderDto.getFinalmoney(),"总金额不能为空");
        Assert.notNull(orderDto.getTrafficType(),"出行类型不能为空");
        Assert.isTrue(!StringUtils.isEmpty(orderDto.getName()),"用户名不能为空");
        Assert.isTrue(!StringUtils.isEmpty(orderDto.getTel()),"手机号不能为空");
        Assert.isTrue(!StringUtils.isEmpty(orderDto.getAddress()),"地址不能为空");
        Assert.isTrue(!StringUtils.isEmpty(orderDto.getDaddress()),"详细地址不能为空");
        orderService.saveOrder(orderDto);
        return CommonResult.success();
    }

    @PostMapping("/userRefund")
    @ApiOperation(value = "用户退款")
    public CommonResult<Void> userRefund(@RequestBody OrderDto orderDto){
        Assert.notNull(orderDto,"订单参数不能为空");
        Assert.notNull(orderDto.getId(),"订单主键Id不能为空");
        orderService.userRefund(orderDto);
        return CommonResult.success();
    }

    @PostMapping("/artificerDoOrder")
    @ApiOperation(value = "技师操作订单")
    public CommonResult<Void> artificerDoOrder(@RequestBody OrderDto orderDto){
        Assert.notNull(orderDto,"订单参数不能为空");
        Assert.notNull(orderDto.getId(),"Id不能为空");
        Assert.notNull(orderDto.getOrderStatusEnum(),"订单状态不能为空");
        orderService.operationOrder(orderDto);
        return CommonResult.success();
    }


    @PostMapping("/userEvaluateCallBack")
    @ApiOperation(value = "用户评价回调")
    public CommonResult<Void> userEvaluateCallBack(@RequestBody OrderDto orderDto){
        Assert.notNull(orderDto,"订单参数不能为空");
        Assert.notNull(orderDto.getId(),"Id不能为空");
        orderService.userEvaluateCallBack(orderDto.getId());
        return CommonResult.success();
    }
}

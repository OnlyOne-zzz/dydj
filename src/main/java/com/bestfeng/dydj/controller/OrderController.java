package com.bestfeng.dydj.controller;

import com.bestfeng.dydj.mbg.model.Order;
import com.bestfeng.dydj.service.OrderService;
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
@RequestMapping("/order")
@Api(tags = "OrderController", description = "订单管理")
public class OrderController implements GeneralCrudController<Order> {


    @Autowired
    private OrderService orderService;


    @Override
    public GeneralService<Order> getService() {
        return orderService;
    }
}

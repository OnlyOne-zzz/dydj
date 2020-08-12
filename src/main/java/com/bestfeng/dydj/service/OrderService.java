package com.bestfeng.dydj.service;

import com.bestfeng.dydj.dto.OrderDto;
import com.bestfeng.dydj.mbg.model.Order;
import org.aurochsframework.boot.commons.service.GeneralService;

/**
 * @author bsetfeng
 * @since 1.0
 **/
public interface OrderService extends GeneralService<Order> {

    /**订单创建*/
    void doOrder(OrderDto orderDto);
}

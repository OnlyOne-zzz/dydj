package com.bestfeng.dydj.service.impl;

import com.bestfeng.dydj.mbg.mapper.OrderMapper;
import com.bestfeng.dydj.mbg.model.Order;
import com.bestfeng.dydj.mbg.model.OrderExample;
import com.bestfeng.dydj.service.OrderService;
import org.aurochsframework.boot.commons.service.AbstractGeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author bsetfeng
 * @since 1.0
 **/
@Service
public class OrderServiceImpl extends AbstractGeneralService<Order> implements OrderService {

    @Autowired
    private OrderMapper mapper;

    @Override
    public Object getMapper() {
        return mapper;
    }

    @Override
    public Object getExample() {
        return new OrderExample();
    }
}

package com.bestfeng.dydj.service;

import com.bestfeng.dydj.dto.OrderDto;
import com.bestfeng.dydj.mbg.model.NoteOrder;
import com.bestfeng.dydj.mbg.model.Order;
import org.aurochsframework.boot.commons.service.GeneralService;

import java.util.Map;

/**
 * @author bsetfeng
 * @since 1.0
 **/
public interface OrderService extends GeneralService<NoteOrder> {

    /**订单创建*/
    OrderDto saveOrder(OrderDto orderDto);
    /**订单退款*/
    void userRefund(OrderDto orderDto);

    void operationOrder(OrderDto orderDto);

    void userEvaluateCallBack(Integer orderId);

    /**通过技师ID查询已完成订单数量*/
    Integer getOrderNumByNoteId(Integer noteId);

    /**
     * 技师（已完成）订单分组组
     * @return
     */
    Map<Integer, Integer> endOrderGroup();
}

package com.bestfeng.dydj.delay.task;

import com.bestfeng.dydj.enums.NoteServiceStatusEnums;
import com.bestfeng.dydj.enums.OrderEnums;
import com.bestfeng.dydj.mbg.mapper.NoteOrderMapper;
import com.bestfeng.dydj.mbg.model.NoteOrder;
import com.bestfeng.dydj.service.CouponOrderService;
import com.bestfeng.dydj.service.OrderService;
import com.bestfeng.dydj.utils.FastJsons;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Slf4j
public class OrderUnPayDelayTask implements Runnable {

    @Autowired
    private NoteOrderMapper noteOrderMapper;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CouponOrderService couponOrderService;
    @Getter
    @Setter
    private String orderNo;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void run() {
        log.info("订单未支付订单 关闭订单延迟消息消费 orderNo={}",orderNo);
        NoteOrder noteOrder = noteOrderMapper.selectObjByOrderNo(orderNo);
        Optional.ofNullable(noteOrder).filter(order->order.getPaid() == OrderEnums.OrderPayStatusEnum.PAY_OFF.getCode()).ifPresent(
                order->{
                    Integer couponId = noteOrder.getCouponid();
                    order.setStatus(OrderEnums.OrderStatusEnum.USER_CLOSE.getCode());
                    //订单
                    noteOrderMapper.updateByPrimaryKeySelective(order);
                    //技师表
                    orderService.noteServiceStatusHandel(noteOrder.getShopid(), NoteServiceStatusEnums.SERVICEABLE);
                    if(null!=couponId && 0!=couponId){
                        //卡券
                        couponOrderService.cancelUse(couponId);
                    }
                    log.info("订单未支付订单 关闭订单延迟消息消费 orderNo={} 关闭成功",orderNo);
                }
        );
    }
}

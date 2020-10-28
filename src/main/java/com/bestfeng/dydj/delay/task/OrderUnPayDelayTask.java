package com.bestfeng.dydj.delay.task;

import com.bestfeng.dydj.enums.NoteServiceStatusEnums;
import com.bestfeng.dydj.enums.OrderEnums;
import com.bestfeng.dydj.manager.queue.delayed.DelayQueueManager;
import com.bestfeng.dydj.mbg.mapper.NoteOrderMapper;
import com.bestfeng.dydj.mbg.model.NoteOrder;
import com.bestfeng.dydj.service.CouponOrderService;
import com.bestfeng.dydj.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class OrderUnPayDelayTask implements CommandLineRunner {

    @Autowired
    private NoteOrderMapper noteOrderMapper;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CouponOrderService couponOrderService;
    @Autowired
    private DelayQueueManager delayQueueManager;

    @Transactional
    public void doTask(String orderNo){
        log.info("订单未支付 关闭订单延迟消息消费 orderNo={}",orderNo);
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
                    log.info("订单未支付 关闭订单延迟消息消费 orderNo={} 关闭成功",orderNo);
                }
        );
    }

    @Override
    public void run(String... args) throws Exception {
        this.initTask();
    }

    /**
     * 初始化任务
     */
    private void initTask(){
        List<NoteOrder> noteOrders = noteOrderMapper.selectListNoteOrder();
        log.info("初始化 未支付订单处理任务 noteOrderList={}", noteOrders);
        Optional.ofNullable(noteOrders).ifPresent(o->{
            o.stream().forEach(order -> {
                //订单创建时间
                Integer createTime =order.getCreatetime();
                Integer metaExpireTime = createTime + 1*60;
                //当前时间
                Integer curTime = (int)(System.currentTimeMillis()/1000L);
                //时间差
                Integer timeDiff = metaExpireTime-curTime;
                if(timeDiff>0){
                    delayQueueManager.put(()->{this.doTask(order.getOrderid());},timeDiff,TimeUnit.SECONDS);
                }else {
                    //已经超过5分钟的，增加任务时长为30s执行
                    delayQueueManager.put(()->{this.doTask(order.getOrderid());},30,TimeUnit.SECONDS);
                }
            });
        });
    }
}

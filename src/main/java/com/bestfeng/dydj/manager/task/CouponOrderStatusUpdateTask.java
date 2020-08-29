package com.bestfeng.dydj.manager.task;

import com.bestfeng.dydj.enums.CouponTypeEnums;
import com.bestfeng.dydj.mbg.model.CouponOrder;
import com.bestfeng.dydj.service.CouponOrderService;
import org.aurochsframework.boot.commons.param.QueryParam;
import org.aurochsframework.boot.commons.param.TermType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 优惠券状态更新任务
 *
 * @author bsetfeng
 * @since 1.0
 **/
@Component
public class CouponOrderStatusUpdateTask implements CommandLineRunner {

    @Autowired
    private CouponOrderService couponOrderService;

    @Autowired
    private ScheduledExecutorService scheduledExecutorService;

    @Override
    public void run(String... args) throws Exception {
        //10分钟更新一次优惠券状态
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            int nowTime = (int) (System.currentTimeMillis() / 1000);
            CouponOrder couponOrder = new CouponOrder();
            couponOrder.setStatus(CouponTypeEnums.INVALID.getValue());
            couponOrderService.updateByExampleSelective(couponOrder,
                    QueryParam.createQueryParam()
                            .and("validityPeriod", TermType.LTE, nowTime)
                            .and("status", CouponTypeEnums.NOT_USED.getValue())
            );
        }, 0, 10, TimeUnit.MINUTES);
    }


}

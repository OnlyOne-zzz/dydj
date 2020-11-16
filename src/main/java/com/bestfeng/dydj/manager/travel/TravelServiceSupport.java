package com.bestfeng.dydj.manager.travel;


import com.bestfeng.dydj.enums.TravelTypeEnums;
import org.aurochsframework.boot.core.exceptions.BusinessException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 出行服务
 */
@Component
public class TravelServiceSupport implements BeanPostProcessor {

    Map<TravelTypeEnums, TravelService> travelServiceMap = new ConcurrentHashMap<>(8);

    /**
     * 出行价格
     *
     * @param distance
     * @return
     */
    public long travelFare(TravelTypeEnums typeEnums, String distance) {
        return Optional.ofNullable(travelServiceMap.get(typeEnums))
                .map(travelService -> Math.round(travelService.travelFare(distance)))
                .orElseThrow(() -> new BusinessException("不支持:" + typeEnums.getText() + "类型的出行方式"));
    }

    /**
     * 往返出行价格
     *
     * @param distance
     * @return
     */
    public long travelFareTo(TravelTypeEnums typeEnums, String distance) {
        return travelFare(typeEnums, distance) * 2;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof TravelService) {
            TravelService travelService = (TravelService) bean;
            travelServiceMap.put(travelService.getType(), travelService);
        }
        return bean;
    }
}

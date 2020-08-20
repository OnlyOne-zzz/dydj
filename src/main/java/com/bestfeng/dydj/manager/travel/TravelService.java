package com.bestfeng.dydj.manager.travel;

import com.bestfeng.dydj.enums.ApiErrorCodeEnums;
import com.bestfeng.dydj.enums.TravelTypeEnums;
import org.aurochsframework.boot.core.exceptions.BusinessException;

public interface TravelService {


    static double distanceToDouble(String distance){
        try {
            return Double.parseDouble(distance);
        }catch (Exception e){
            throw new BusinessException("距离参数："+ distance +"非法", ApiErrorCodeEnums.ILLEGAL_ARGUMENT_ERROR.getCode());
        }
    }

    TravelTypeEnums getType();


    /**
     * 出行价格
     *
     * @param distance
     * @return
     */
    double travelFare(String distance);


}

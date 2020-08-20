package com.bestfeng.dydj.manager.travel;

import com.bestfeng.dydj.enums.TravelTypeEnums;
import org.aurochsframework.boot.core.exceptions.BusinessException;
import org.springframework.stereotype.Component;

import java.util.Calendar;


/**
 * 公交车出行
 */
@Component
public class SubwayTravelService implements TravelService {


    @Override
    public TravelTypeEnums getType() {
        return TravelTypeEnums.SUBWAY;
    }


    @Override
    public double travelFare(String distance) {
        if (isOpenBusTravelTimeInterval()) {
            throw new BusinessException("晚八点到早八点期间不能选择公交地铁出行");
        }
        return fareCount(distance);
    }


    /**
     * 公交出行开放时段判断
     *
     * @return
     */
    protected boolean isOpenBusTravelTimeInterval() {
        //晚上八点到早上八点不能公交出行
        Calendar calendar = Calendar.getInstance();
        int nowHour = calendar.get(Calendar.HOUR_OF_DAY);
        return nowHour >= 20 || nowHour < 8;
    }

    /**
     * 白天计费
     *
     * @param distance
     * @return
     */
    protected static double fareCount(String distance) {
        double dte = TravelService.distanceToDouble(distance);
        if (dte <= 6) {
            return 2D;
        }
        if (dte > 6 && dte <= 11) {
            return 3D;
        }
        if (dte > 11 && dte <= 17) {
            return 4D;
        }
        if (dte > 17 && dte <= 24) {
            return 5D;
        }
        if (dte > 24 && dte <= 32) {
            return 6D;
        }
        if (dte > 32 && dte <= 41) {
            return 7D;
        }
        return 10;
    }
}

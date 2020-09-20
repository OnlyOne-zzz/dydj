package com.bestfeng.dydj.manager.travel;

import com.bestfeng.dydj.enums.TravelTypeEnums;
import org.springframework.stereotype.Component;

import java.util.Calendar;


/**
 * 出租车出行
 */
@Component
public class TaxiTravelService implements TravelService {


    @Override
    public TravelTypeEnums getType() {
        return TravelTypeEnums.TAXI;
    }


    @Override
    public double travelFare(String distance) {
        return isNight() ? nightFareCount(distance) : dayTimeFareCount(distance);
    }



    /**
     * 出租车晚上出行判断
     *
     * @return
     */
    protected boolean isNight() {
        //晚上11点到早上6点算晚上
        Calendar calendar = Calendar.getInstance();
        int nowHour = calendar.get(Calendar.HOUR_OF_DAY);
        return nowHour >= 23 || nowHour < 6;
    }
    /**
     * 白天计费
     *
     * @param distance
     * @return
     */
    protected static double dayTimeFareCount(String distance) {
        double dte = TravelService.distanceToDouble(distance);
        //三公里起步价10块
        if (dte <= 3) {
            return 10D;
        }
//        if (dte > 3 && dte <= 8) {
//            //3公里后，每一公里+2块 + 误差费 2元
//            return 10 + (dte - 3) * 2.3;
//        }
        //3公里后，每一公里+2块 + 误差费 2元 + 堵车费用预估
        return 10 + (dte - 3) * 2.3;


    }

    /**
     * 晚上计费
     *
     * @param distance
     * @return
     */
    protected static double nightFareCount(String distance) {
        double dte = TravelService.distanceToDouble(distance);
        //三公里起步价10块
        if (dte <= 3) {
            return 11D;
        }
//        if (dte > 3 && dte <= 8) {
//            //3公里后，每一公里+2.3块 + 误差费 2元
//            return 11 + (dte - 3) * 2 + 2;
//        }
        //3公里后，每一公里+2.6块
        return 11 + (dte - 3) * 2.6;
    }
}

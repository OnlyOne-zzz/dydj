package com.bestfeng.dydj.enums;

import lombok.Getter;

/**
 * @author zh
 * @Des 订单相关枚举
 */
@Getter
public class OrderEnums {

    /**
     * 订单的状态枚举
     */
    @Getter
    public enum  OrderStatusEnum{

        JS_WAITE_GET(0,"待接单"),
        JS_ARRAY_GET(1,"技师接单"),
        JS_SET_OUT(2,"技师出发"),
        JS_ARRIVE(3,"技师到达"),
        JS_SERVICE_START(4,"开始服务"),
        JS_SERVICE_END(5,"服务完成"),
        USER_WAIT_EVALUATE(6,"用户待评价"),
        USER_EVALUATE_SUCCESS(7,"已评价"),
        USER_REFUND_ING(8,"退款中"),
        USER_REFUND_SUCCESS(9,"退款成功"),
        USER_CLOSE(10,"订单已关闭");

        private Integer code;
        private String name;

        OrderStatusEnum(Integer code,String name){
            this.code=code;
            this.name=name;
        }

        public static OrderStatusEnum getNameByCode(Integer code){
            OrderStatusEnum[]  statusEnums = OrderStatusEnum.values();
            for (OrderStatusEnum statusEnum:statusEnums){
                if(statusEnum.getCode() == code){
                    return statusEnum;
                }
            }
            return null;
        }
    }

    /**
     * 订单的状态枚举
     */
    @Getter
    public enum  OrderRefundEnum{

        REFUND_ING(0,"退款中"),
        REFUND_SUCCESS(1,"退款成功"),
        REFUND_FAIL(2,"退款失败");

        private Integer code;
        private String name;

        OrderRefundEnum(Integer code,String name){
            this.code=code;
            this.name=name;
        }

        public String getNameByCode(Integer code){
            OrderStatusEnum[]  statusEnums = OrderStatusEnum.values();
            for (OrderStatusEnum statusEnum:statusEnums){
                if(statusEnum.getCode() == code){
                    return statusEnum.getName();
                }
            }
            return null;
        }
    }



}

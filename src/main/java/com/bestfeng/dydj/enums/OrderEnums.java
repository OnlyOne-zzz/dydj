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

        PAY_OFF(0,"待支付"),
        PAY_SUCCESS(1,"支付成功"),
        DOOR_ARRIVE(2,"技师确认上门"),
        SERVICE_START(3,"技师开始服务"),
        REFUND_ING(4,"退款中"),
        REFUND_SUCCESS(5,"退款成功"),
        WAIT_EVALUATE(6,"用户待评价");

        private Integer code;
        private String name;

        OrderStatusEnum(Integer code,String name){
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

package com.bestfeng.dydj.enums;

import lombok.Getter;

public class UserEnums {

    /**
     * 用户身份
     */
    @Getter
    public enum  UserIdentityEnum{

        ARTIFICER(0,"技师"),
        USER(1,"普通用户");

        private Integer code;
        private String name;

        UserIdentityEnum(Integer code,String name){
            this.code=code;
            this.name=name;
        }

        public String getNameByCode(Integer code){
            OrderEnums.OrderStatusEnum[]  statusEnums = OrderEnums.OrderStatusEnum.values();
            for (OrderEnums.OrderStatusEnum statusEnum:statusEnums){
                if(statusEnum.getCode() == code){
                    return statusEnum.getName();
                }
            }
            return null;
        }
    }
}

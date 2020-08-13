package com.bestfeng.dydj.dto;

import com.bestfeng.dydj.enums.OrderEnums;
import com.bestfeng.dydj.enums.UserEnums;
import com.bestfeng.dydj.mbg.model.Order;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderDto extends Order implements Serializable {

    private OrderEnums.OrderStatusEnum orderStatusEnum;

    private UserEnums.UserIdentityEnum userIdentityEnum;
}

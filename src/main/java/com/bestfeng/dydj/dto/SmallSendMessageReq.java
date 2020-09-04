package com.bestfeng.dydj.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 小程序订阅消息的前
 */
@Data
public class SmallSendMessageReq implements Serializable {
    private String OrderNo;
    private String templateId;
}

package com.bestfeng.dydj.service;

import com.bestfeng.dydj.dto.SendMessageDto;
import com.bestfeng.dydj.dto.SmallSendMessageReq;

public interface SendMessageService {

    void sendMessage(SmallSendMessageReq smallSendMessageReq)throws Exception;
}

package com.bestfeng.dydj.service;

import com.bestfeng.dydj.dto.SendMessageDto;

public interface WeChatService {

    void sendMessage(SendMessageDto sendMessageDto)throws Exception;

    String getAccessToken()throws Exception;

}

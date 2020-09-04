package com.bestfeng.dydj.controller;

import com.bestfeng.dydj.annotation.SignValidated;
import com.bestfeng.dydj.controller.request.NoteSosMessageRequest;
import com.bestfeng.dydj.dto.SendMessageDto;
import com.bestfeng.dydj.dto.SmallSendMessageReq;
import com.bestfeng.dydj.service.SendMessageService;
import com.bestfeng.dydj.service.SosTemplateMessageService;
import com.bestfeng.dydj.service.WeChatService;
import io.swagger.annotations.ApiOperation;
import org.aurochsframework.boot.commons.api.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 小程序订阅消息接口
 *
 * @author zh
 **/
@RestController("/sendMessage")
@SignValidated
public class SendMessageController {


    @Autowired
    private SendMessageService sendMessageService;

    @PostMapping("/small/send")
    @ApiOperation("发送小程序订阅消息")
    public CommonResult<Object> sosSend(@RequestBody SmallSendMessageReq smallSendMessageReq) throws Exception{
        sendMessageService.sendMessage(smallSendMessageReq);
        return CommonResult.success("发送成功");
    }
}

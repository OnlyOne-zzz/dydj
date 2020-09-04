package com.bestfeng.dydj.service.impl;

import com.bestfeng.dydj.constants.SendMessageDataTemplateConstant;
import com.bestfeng.dydj.dto.SendMessageDto;
import com.bestfeng.dydj.dto.SmallSendMessageReq;
import com.bestfeng.dydj.mbg.mapper.NoteOrderMapper;
import com.bestfeng.dydj.mbg.model.NoteOrder;
import com.bestfeng.dydj.mbg.model.UserInfo;
import com.bestfeng.dydj.service.SendMessageService;
import com.bestfeng.dydj.service.UserInfoService;
import com.bestfeng.dydj.service.WeChatService;
import com.bestfeng.dydj.utils.FastJsons;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


@Service
@Slf4j
public class SendMessageImpl implements SendMessageService {

    @Autowired
    private WeChatService weChatService;
    @Autowired
    private NoteOrderMapper orderMapper;
    @Autowired
    private UserInfoService userInfoService;

    @Override
    public void sendMessage(SmallSendMessageReq smallSendMessageReq) throws Exception {
        log.info("小程序订阅消息发送 req={}", FastJsons.convertObjectToJSONObject(smallSendMessageReq));
        Assert.notNull(smallSendMessageReq,"小程序订阅消息发送参数实体不能为空");
        String orderNo = smallSendMessageReq.getOrderNo();
        String templateId = smallSendMessageReq.getTemplateId();
        NoteOrder noteOrder = orderMapper.selectObjByOrderNo(orderNo);
        Assert.notNull(noteOrder,"订单不存在");
        Integer uid = noteOrder.getUid();
        UserInfo userInfo = userInfoService.selectByUId(uid);
        Assert.notNull(userInfo,"用户不存在");
        SendMessageDto sendMessageDto = new SendMessageDto();
        sendMessageDto.setTemplate_id(templateId);
        sendMessageDto.setTouser(userInfo.getOpenid());
        sendMessageDto.setMiniprogram_state("developer");//开发版发布
        sendMessageDto.setData(String.format(SendMessageDataTemplateConstant.ORDER_TEMPLATE,orderNo,noteOrder.getStatus()));
        weChatService.sendMessage(sendMessageDto);
    }
}

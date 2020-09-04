package com.bestfeng.dydj.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bestfeng.dydj.configuration.AccessTokenManagerCenter;
import com.bestfeng.dydj.dto.SendMessageDto;
import com.bestfeng.dydj.service.WeChatService;
import com.bestfeng.dydj.utils.FastJsons;
import com.bestfeng.dydj.utils.httpclient.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.aurochsframework.boot.core.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Service
@Slf4j
public class WeChatServiceImpl implements WeChatService {

    @Value("${wechat.appId}")
    private String appId;

    @Value("${wechat.appSecret}")
    private String appSecret;

    @Autowired
    private AccessTokenManagerCenter managerCenter;

    private static final String SEND_MESSAGE_URI = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send";

    @Override
    public void sendMessage(SendMessageDto sendMessageDto)throws Exception {
        Assert.notNull(sendMessageDto,"小程序订阅消息发送参数实体不能为空");
        String accessToken = managerCenter.getAccessToken();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(SEND_MESSAGE_URI)
                .queryParam("access_token", accessToken);
        sendMessageDto.setAccess_token(accessToken);
        String url = builder.build().toUri().toString();
        Map<String,String>  sendParam = FastJsons.convertJSONMap(FastJsons.convertObjectToJSON(sendMessageDto));
        log.info("小程序订阅消息发送 请求参数 url={} request={}",url,FastJsons.convertObjectToJSON(sendParam));
        String response = HttpClientUtil.doPost(url,sendParam);
        log.info("小程序订阅消息发送 响应数据 response={}",response);
        if(StringUtils.isEmpty(response)){
            throw new BusinessException("小程序订阅消息发送 失败");
        }
        JSONObject jsonObject = FastJsons.convertObjectToJSONObject(response);
        if(jsonObject.getIntValue("errCode") != 0){
            throw new BusinessException("小程序订阅消息发送 失败");
        }
    }
}

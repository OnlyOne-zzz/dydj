package com.bestfeng.dydj.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bestfeng.dydj.configuration.AccessTokenManagerCenter;
import com.bestfeng.dydj.constants.Constants;
import com.bestfeng.dydj.dto.SendMessageDto;
import com.bestfeng.dydj.dto.SmallProDto;
import com.bestfeng.dydj.service.WeChatService;
import com.bestfeng.dydj.utils.FastJsons;
import com.bestfeng.dydj.utils.RedisUtil;
import com.bestfeng.dydj.utils.SmallEncryptedDateUtil;
import com.bestfeng.dydj.utils.httpclient.HttpClientUtil;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.aurochsframework.boot.core.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static cn.hutool.core.util.CharsetUtil.CHARSET_UTF_8;


@Service
@Slf4j
public class WeChatServiceImpl implements WeChatService {

    @Value("${wechat.small.appId}")
    private String appId;

    @Value("${wechat.small.appSecret}")
    private String appSecret;

    @Autowired
    private AccessTokenManagerCenter managerCenter;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RedisUtil redisUtil;

    private static final String SEND_MESSAGE_URI = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send";

    private static final String  code2sessionUri="https://api.weixin.qq.com/sns/jscode2session";

    private static final String smallAppid = "wxfee9e92111e44d0b";
    private static final String smallAppSecret= "beac8aa28b94156394432b9e32596c40";

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
        Integer code = jsonObject.getInteger("errcode");
        if(0 != code.intValue() ){
            throw new BusinessException("小程序订阅消息发送失败".concat(jsonObject.getString("errmsg")));
        }
    }

    @Override
    public String getAccessToken()throws Exception{
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(Constants.ACCESS_TOKEN_REQUEST_URL)
                .queryParam("grant_type", "client_credential")
                .queryParam("appid", appId)
                .queryParam("secret", appSecret);
        String response = restTemplate.getForObject(builder.build().toUri().toString(), String.class);
        JSONObject result = JSON.parseObject(response);
        String token = "";
        if (!result.containsKey("access_token")) {
            log.error("获取access_token失败,response:{}", response);
            throw new BusinessException("获取accessToken异常");
        } else {
            token = result.getString("access_token");
            log.info("一次access-token刷新成功，token:{}", token);
        }

        return token;
    }

    /**
     * 获取小程序的手机号
     * @param proDto
     * @return
     * @throws Exception
     */
    public JSONObject getWechatInfo(SmallProDto proDto)throws Exception{
//        String code =proDto.getCode();
        String iv =proDto.getIv();
        String encryptedData = proDto.getEncryptedData();
//        Map<String,Object> sessionKeyMap = this.code2session(code);
//        String sessionKey = MapUtils.getString(sessionKeyMap,"sessionKey");
        String sessionKey = proDto.getSessionKey();
        JSONObject jsonObject = this.getEncryptedDate(encryptedData,sessionKey,iv);
//        String     phone     = jsonObject.getString("purePhoneNumber");//小程序的手机号
        return jsonObject;
    }
    /**
     * 小程序code换取openid与sessionKey
     *
     * @return
     */
    public Map<String, Object> code2session(String code) throws Exception {

        StringBuffer sb = new StringBuffer();
        sb.append(code2sessionUri).append("?appid=")
                .append(smallAppid).append("&secret=")
                .append(smallAppSecret).append("&js_code=")
                .append(code).append("&grant_type=authorization_code");
        log.info("小程序code2session请求url与参数:{},code：{}", sb.toString(), code);
        String result = HttpClientUtil.doGet(sb.toString(), null, CHARSET_UTF_8.displayName());
        log.info("小程序code2session响应结果:{}", result);
        if (StringUtils.isBlank(result)) {
            log.error("微信code2session接口响应结果为空");
            throw new RuntimeException("微信code2session接口响应结果为空");
        }
        JSONObject sessionJSON = JSONObject.parseObject(result);
        String     unionid     = "";
        if (sessionJSON.containsKey("openid")) {
            String              openid     = sessionJSON.getString("openid");
            String              sessionKey = sessionJSON.getString("session_key");
            if (sessionJSON.containsKey("unionid")) {
                unionid = sessionJSON.getString("unionid");
            }
            Map<String, Object> retMap     = Maps.newHashMap();
            retMap.put("openid", openid);
            retMap.put("sessionKey", sessionKey);
            retMap.put("unionid", unionid);

            return retMap;
        } else {
            throw new BusinessException(sessionJSON.getString("errmsg"));
        }
    }

    /**
     * 获取解密之后的小程序用户信息
     *
     * @param encryptedData
     * @param sessionkey
     * @param iv
     * @return
     * @throws Exception
     */
    public JSONObject getEncryptedDate(String encryptedData, String sessionkey, String iv) throws Exception {
        log.info("encryptedData:{} sessionkey:{} iv:{}", encryptedData, sessionkey, iv);
        String data = SmallEncryptedDateUtil.encryptedData(encryptedData, sessionkey, iv);
        log.info(data);
        if (org.apache.commons.lang.StringUtils.isNotBlank(data)) {
            return JSONObject.parseObject(data);
        }
        throw new BusinessException("解密数据为空字符串!");
    }
}

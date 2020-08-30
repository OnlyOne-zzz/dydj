package com.bestfeng.dydj.manager.wechat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bestfeng.dydj.configuration.AccessTokenManagerCenter;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.aurochsframework.boot.core.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author bsetfeng
 * @since 1.0
 **/
@Service
@Slf4j
public class WechatUserService {

    private Map<String, List<String>> tagUsersMap = new ConcurrentHashMap<>();

    private final static String GET_TAG_USERS_URL = "https://api.weixin.qq.com/cgi-bin/user/tag/get";
    private final static String GET_ALL_USER_OPENID_URL = "https://api.weixin.qq.com/cgi-bin/user/get";
    private static final String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
    @Autowired
    private AccessTokenManagerCenter accessTokenManagerCenter;

    @Autowired
    private RestTemplate restTemplate;




    public List<String> getOpenIdsByTag(String tagId, String nextOpenId) {
        return tagUsersMap.computeIfAbsent(tagId, k -> getTagUsers(tagId, nextOpenId));
    }

    public void refreshTagUsers() {
        tagUsersMap.forEach((k, v) -> tagUsersMap.put(k, getTagUsers(k, null)));
    }


    private List<String> getTagUsers(String tagId, String nextOpenId) {
        Map<String, Object> body = new HashMap<>();
        body.put("tagid", tagId);
        body.put("next_openid", nextOpenId);
        String result = restTemplate.postForObject(getTagUsersCompleteUrl(), body, String.class);
        if (result != null && result.contains("count")) {
            JSONObject data = JSON.parseObject(result).getJSONObject("data");
            return data.getJSONArray("openid").toJavaList(String.class);
        }
        log.error("获取标签用户列表失败，url:{},tagId:{},next_openId:{}", getTagUsersCompleteUrl(), tagId, nextOpenId);
        throw new BusinessException("获取标签用户列表失败:" + result);
    }

    private String getTagUsersCompleteUrl() {
        return UriComponentsBuilder.fromHttpUrl(GET_TAG_USERS_URL)
                .queryParam("access_token", accessTokenManagerCenter.getAccessToken())
                .build().toUri().toString();
    }

    private String getAllUserCompleteUrl() {
        return UriComponentsBuilder.fromHttpUrl(GET_ALL_USER_OPENID_URL)
                .queryParam("access_token", accessTokenManagerCenter.getAccessToken())
                .build().toUri().toString();
    }



    public List<String> getAllOpenId(String next_openid) {
        //return getOpenIdsByTag("100","");
        Map<String, Object> requestParams = Maps.newHashMap();
        requestParams.put("next_openid", next_openid);
        String result = restTemplate.postForObject(getAllUserCompleteUrl(), requestParams, String.class);
        if (result != null && result.contains("count")) {
            JSONObject data = JSON.parseObject(result).getJSONObject("data");
            return data.getJSONArray("openid").toJavaList(String.class);
        }
        return Collections.emptyList();
    }
}

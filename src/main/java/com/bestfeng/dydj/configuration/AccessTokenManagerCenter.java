package com.bestfeng.dydj.configuration;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bestfeng.dydj.constants.Constants;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


/**
 * @author bsetfeng
 * @since 1.0
 **/
@Component
@EnableScheduling
@Slf4j
@Order(2)
public class AccessTokenManagerCenter implements SchedulingConfigurer, CommandLineRunner {


    @Getter
    @Value("${wechat.appId:wx3a064f24347b34d2}")
    private String appId;
    @Getter
    @Value("${wechat.appSecret:2fe25c657f2c7ab751e09f249c04b7c7}")
    private String appSecret;

    @Value("${wechat.template-id:O7qm04PozaNS375KaRZxPTe3GB8wNM_H8VH0_mQnZHc}")
    private String templateId;


    @Value("${wechat.refreshCron:0 0 0-23 * * ?}")
    private String refreshCron;

    @Autowired
    private RestTemplate restTemplate;

    private volatile String accessToken;

    public String getAccessToken() {
        return accessToken;
    }


    public void refreshAccessToken() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(Constants.ACCESS_TOKEN_REQUEST_URL)
                .queryParam("grant_type", "client_credential")
                .queryParam("appid", appId)
                .queryParam("secret", appSecret);
        String response = restTemplate.getForObject(builder.build().toUri().toString(), String.class);
        JSONObject result = JSON.parseObject(response);
        if (!result.containsKey("access_token")) {
            log.error("获取access_token失败,response:{}", response);
        } else {
            String token = result.getString("access_token");
            this.accessToken = token;
            log.info("一次access-token刷新成功，token:{}", token);
        }
    }


    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(() -> {
                    refreshAccessToken();
                },
                triggerContext -> new CronTrigger(refreshCron).nextExecutionTime(triggerContext));
    }

    @Override
    public void run(String... args) {
        refreshAccessToken();
    }
}

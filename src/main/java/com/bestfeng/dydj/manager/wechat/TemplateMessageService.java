package com.bestfeng.dydj.manager.wechat;

import com.bestfeng.dydj.configuration.AccessTokenManagerCenter;
import com.bestfeng.dydj.manager.wechat.template.SosTemplateEntity;
import com.bestfeng.dydj.manager.wechat.template.TemplateWrapperEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author bsetfeng
 * @since 1.0
 **/

@Service
@Slf4j
public class TemplateMessageService {


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AccessTokenManagerCenter accessTokenManagerCenter;


    private final static String SEND_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send";

    @Value("${wechat.power.template-id:J_gja9f3O1egF5cZx0iFXhpbEoYizyXASFiS0DDj6nU}")
    private String templateId;

    /**
     *
     * @param url 模板消息 点击详跳转的超连接地址
     * @param openId 发给指定微信用户
     * @param templateData 模板的内容
     * @return
     */
    public String sendTemplateMessage(String url, String openId, TemplateWrapperEntity templateData) {
        SosTemplateEntity templateEntity = new SosTemplateEntity();
        templateEntity.setTouser(openId);
        templateEntity.setData(templateData);
        templateEntity.setTemplate_id(templateId);
        templateEntity.setUrl(url);
        String sendTemplateUrl = getSendTemplateCompleteUrl();
        log.debug("模板消息url:{}", sendTemplateUrl);
        return restTemplate.postForObject(sendTemplateUrl, templateEntity, String.class);
    }


    private String getSendTemplateCompleteUrl() {
        return UriComponentsBuilder.fromHttpUrl(SEND_TEMPLATE_URL)
                .queryParam("access_token", accessTokenManagerCenter.getAccessToken())
                .build().toUri().toString();
    }

}

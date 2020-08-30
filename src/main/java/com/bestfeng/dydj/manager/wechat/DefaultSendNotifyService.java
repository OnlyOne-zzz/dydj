package com.bestfeng.dydj.manager.wechat;

import com.bestfeng.dydj.manager.wechat.template.TemplateWrapperEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author zzh
 * @date 2020-05-22 10:05
 * @desc
 */
@Slf4j
@Service
public class DefaultSendNotifyService implements SendNotifyService {

    @Autowired
    private WechatUserService wechatUserService;

    @Autowired
    private TemplateMessageService templateMessageService;


    //委托实现方法
    @Override
    public boolean sendNotify(TemplateWrapperEntity entity, String tagId) {
        return sendNotify(entity, wechatUserService.getOpenIdsByTag(tagId, ""));
    }


    private boolean sendNotify(TemplateWrapperEntity entity, List<String> openIds) {
        AtomicBoolean re = new AtomicBoolean(false);
        log.info("通知的用户数量size:{}", openIds.size());
        openIds.forEach(openId -> {
            String result = templateMessageService.sendTemplateMessage("", openId, entity);
            re.compareAndSet(false, result.contains("ok"));
            log.debug("模板消息发送结果：{}", result);
        });
        return re.get();
    }

}

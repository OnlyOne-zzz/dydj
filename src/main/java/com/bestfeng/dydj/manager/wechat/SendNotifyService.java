package com.bestfeng.dydj.manager.wechat;

import com.bestfeng.dydj.manager.wechat.template.TemplateWrapperEntity;

/**
 * @author zzh
 * @date 2020-05-22 9:57
 * @desc 公众号发送模板消息
 */
public interface SendNotifyService {



    boolean sendNotify(TemplateWrapperEntity entity, String tagId);
}

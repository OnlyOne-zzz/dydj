package com.bestfeng.dydj.service;

import com.bestfeng.dydj.manager.wechat.SendNotifyService;
import com.bestfeng.dydj.manager.wechat.template.TemplateValueWrapper;
import com.bestfeng.dydj.manager.wechat.template.TemplateWrapperEntity;
import com.bestfeng.dydj.mbg.model.NoteOrder;
import com.bestfeng.dydj.mbg.model.UserInfo;
import com.bestfeng.dydj.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 订单创建模板消息service
 *
 * @author bsetfeng
 * @since 1.0
 **/
@Service
@Slf4j
public class OrderCreateTemplateMessageService {


    @Autowired
    private SendNotifyService sendNotifyService;

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 发送用户下单通知信息
     *
     * @param noteOrder
     */
    public void orderCreateMsgSend(NoteOrder noteOrder) {
        UserInfo userInfo = userInfoService.selectByUId(noteOrder.getUid());
        sendNotifyService.sendNotify(assemble(noteOrder.getAddress(), noteOrder.getName(), userInfo.getTel(),noteOrder.getTel()), "100");
    }

    private TemplateWrapperEntity assemble(String address, String name, String phone1, String phone2) {
        TemplateValueWrapper first = new TemplateValueWrapper("有用户下单啦", "#173177");
        TemplateValueWrapper keyword1 = new TemplateValueWrapper("用户姓名:" + name, "#173177");
        TemplateValueWrapper keyword2 = new TemplateValueWrapper("联系电话:" + phone1 + "," + phone2, "#173177");
        TemplateValueWrapper keyword3 = new TemplateValueWrapper(TimeUtils.timeStampToStr(System.currentTimeMillis()), "#173177");
        TemplateValueWrapper keyword4 = new TemplateValueWrapper(address, "#173177");
        TemplateValueWrapper remark = new TemplateValueWrapper("请尽快联系用户处理", "#173177");
        return new TemplateWrapperEntity(first, keyword1, keyword2, keyword3, keyword4, remark);
    }

}

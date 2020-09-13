package com.bestfeng.dydj.service;

import com.bestfeng.dydj.controller.request.NoteSosMessageRequest;
import com.bestfeng.dydj.manager.wechat.SendNotifyService;
import com.bestfeng.dydj.manager.wechat.template.TemplateValueWrapper;
import com.bestfeng.dydj.manager.wechat.template.TemplateWrapperEntity;
import com.bestfeng.dydj.mbg.model.Note;
import com.bestfeng.dydj.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.aurochsframework.boot.commons.param.QueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 模板消息service
 *
 * @author bsetfeng
 * @since 1.0
 **/
@Service
@Slf4j
public class SosTemplateMessageService {


    @Autowired
    private NoteService noteService;


    @Autowired
    private SendNotifyService sendNotifyService;


    /**
     * 发送技师求助信息
     *
     * @param request
     */
    public void sosSend(NoteSosMessageRequest request) {
        Note note = noteService.fetchOne(QueryParam.createQueryParam().and("id", request.getNoteId()));
        sendNotifyService.sendNotify(assemble(request, note.getShopname(), note.getTel()), "100");
    }

    private TemplateWrapperEntity assemble(NoteSosMessageRequest request, String name, String phone) {
        TemplateValueWrapper first = new TemplateValueWrapper("兄弟们，我们的技师有危险！", "#173177");
        TemplateValueWrapper keyword1 = new TemplateValueWrapper("技师姓名:" + name, "#173177");
        TemplateValueWrapper keyword2 = new TemplateValueWrapper("联系电话:" + phone, "#173177");
        TemplateValueWrapper keyword3 = new TemplateValueWrapper(TimeUtils.timeStampToStr(System.currentTimeMillis()), "#173177");
        TemplateValueWrapper keyword4 = new TemplateValueWrapper(request.getAddress(), "#173177");
        TemplateValueWrapper remark = new TemplateValueWrapper("请尽快联系技师处理", "#173177");
        return new TemplateWrapperEntity(first, keyword1, keyword2, keyword3, keyword4, remark);
    }

}

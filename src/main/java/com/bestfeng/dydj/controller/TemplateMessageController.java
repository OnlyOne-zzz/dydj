package com.bestfeng.dydj.controller;

import com.bestfeng.dydj.annotation.SignValidated;
import com.bestfeng.dydj.controller.request.NoteSosMessageRequest;
import com.bestfeng.dydj.service.SosTemplateMessageService;
import io.swagger.annotations.ApiOperation;
import org.aurochsframework.boot.commons.api.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模板消息接口
 *
 * @author bsetfeng
 * @since 1.0
 **/
@RestController("/template-message")
@SignValidated
public class TemplateMessageController {


    @Autowired
    private SosTemplateMessageService templateMessageService;

    @PostMapping("/sos/send")
    @ApiOperation("发送技师求救模板消息")
    public CommonResult<Object> sosSend(@RequestBody NoteSosMessageRequest request) {
        if (request.getNoteId() != 0) {
            templateMessageService.sosSend(request);
            return CommonResult.success("发送成功");
        }
        return CommonResult.success("发送失败");
    }
}

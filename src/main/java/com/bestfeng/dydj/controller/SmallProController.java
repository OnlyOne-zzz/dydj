package com.bestfeng.dydj.controller;

import com.alibaba.fastjson.JSONObject;
import com.bestfeng.dydj.dto.SmallProDto;
import com.bestfeng.dydj.service.impl.WeChatServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.aurochsframework.boot.commons.api.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zh
 * @since 1.0
 **/
@RestController
@RequestMapping("/smallPro")
@Api(tags = "SmallProController", description = "小程序")
public class SmallProController {


    @Autowired
    private WeChatServiceImpl weChatService;


    @PostMapping("/getWechatInfo")
    @ApiOperation(value = "获取微信解密的信息")
    public CommonResult<JSONObject> getWechatPhone(@RequestBody SmallProDto smallProDto)throws Exception{
        Assert.notNull(smallProDto,"小程序参数不能为空");
        Assert.isTrue(!StringUtils.isEmpty(smallProDto.getIv()),"iv不能为空");
        Assert.isTrue(!StringUtils.isEmpty(smallProDto.getSessionKey()),"sessionKey不能为空");
        Assert.isTrue(!StringUtils.isEmpty(smallProDto.getEncryptedData()),"加密数据不能为空");
        return CommonResult.success(weChatService.getWechatInfo(smallProDto));
    }
}

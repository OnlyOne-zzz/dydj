package com.bestfeng.dydj.controller;

import com.bestfeng.dydj.mbg.model.Content;
import com.bestfeng.dydj.service.ContentService;
import io.swagger.annotations.Api;
import org.aurochsframework.boot.commons.controller.GeneralCrudController;
import org.aurochsframework.boot.commons.service.GeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bsetfeng
 * @since 1.0
 **/
@RestController
@RequestMapping("/Content")
@Api(tags = "ContentController", description = "服务内容管理")
public class ContentController implements GeneralCrudController<Content> {


    @Autowired
    private ContentService contentService;


    @Override
    public GeneralService<Content> getService() {
        return contentService;
    }
}

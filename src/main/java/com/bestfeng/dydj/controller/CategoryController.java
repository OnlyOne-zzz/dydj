package com.bestfeng.dydj.controller;

import com.bestfeng.dydj.mbg.model.Category;
import com.bestfeng.dydj.service.CategoryService;
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
@RequestMapping("/category")
@Api(tags = "CategoryController", description = "服务项目管理")
public class CategoryController implements GeneralCrudController<Category> {


    @Autowired
    private CategoryService categoryService;


    @Override
    public GeneralService<Category> getService() {
        return categoryService;
    }
}

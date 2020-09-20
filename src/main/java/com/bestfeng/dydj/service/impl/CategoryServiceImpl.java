package com.bestfeng.dydj.service.impl;

import com.bestfeng.dydj.mbg.mapper.CategoryMapper;
import com.bestfeng.dydj.mbg.model.Category;
import com.bestfeng.dydj.mbg.model.CategoryExample;
import com.bestfeng.dydj.service.CategoryService;
import org.aurochsframework.boot.commons.service.AbstractGeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author bsetfeng
 * @since 1.0
 **/
@Service
public class CategoryServiceImpl extends AbstractGeneralService<Category> implements CategoryService {

    @Autowired
    private CategoryMapper mapper;

    @Override
    public Object getMapper() {
        return mapper;
    }

    @Override
    public Object getExample() {
        return new CategoryExample();
    }

    @Override
    public Map<Integer, List<Category>> idGroup() {
        return fetchAll()
                .stream()
                .collect(Collectors.groupingBy(Category::getId, Collectors.toList()));
    }
}

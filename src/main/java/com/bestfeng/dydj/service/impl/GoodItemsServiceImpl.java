package com.bestfeng.dydj.service.impl;

import com.bestfeng.dydj.mbg.mapper.GoodItemsMapper;
import com.bestfeng.dydj.mbg.model.GoodItems;
import com.bestfeng.dydj.mbg.model.GoodItemsExample;
import com.bestfeng.dydj.service.GoodItemsService;
import org.aurochsframework.boot.commons.service.AbstractGeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author bsetfeng
 * @since 1.0
 **/
@Service
public class GoodItemsServiceImpl extends AbstractGeneralService<GoodItems> implements GoodItemsService {

    @Autowired
    private GoodItemsMapper mapper;

    @Override
    public Object getMapper() {
        return mapper;
    }

    @Override
    public Object getExample() {
        return new GoodItemsExample();
    }
}

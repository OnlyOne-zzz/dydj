package com.bestfeng.dydj.service.impl;

import com.bestfeng.dydj.mbg.mapper.ContentMapper;
import com.bestfeng.dydj.mbg.model.Content;
import com.bestfeng.dydj.mbg.model.ContentExample;
import com.bestfeng.dydj.service.ContentService;
import org.aurochsframework.boot.commons.service.AbstractGeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author bsetfeng
 * @since 1.0
 **/
@Service
public class ContentServiceImpl extends AbstractGeneralService<Content> implements ContentService {

    @Autowired
    private ContentMapper mapper;

    @Override
    public Object getMapper() {
        return mapper;
    }

    @Override
    public Object getExample() {
        return new ContentExample();
    }
}

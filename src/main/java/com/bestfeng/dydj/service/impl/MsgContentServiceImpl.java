package com.bestfeng.dydj.service.impl;

import com.bestfeng.dydj.mbg.mapper.MsgContentMapper;
import com.bestfeng.dydj.mbg.model.MsgContent;
import com.bestfeng.dydj.mbg.model.MsgContentExample;
import com.bestfeng.dydj.service.MsgContentService;
import org.aurochsframework.boot.commons.service.AbstractGeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author bsetfeng
 * @since 1.0
 **/
@Service
public class MsgContentServiceImpl extends AbstractGeneralService<MsgContent> implements MsgContentService {

    @Autowired
    private MsgContentMapper mapper;

    @Override
    public Object getMapper() {
        return mapper;
    }

    @Override
    public Object getExample() {
        return new MsgContentExample();
    }
}

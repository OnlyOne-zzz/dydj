package com.bestfeng.dydj.service.impl;

import com.bestfeng.dydj.mbg.mapper.AddressMapper;
import com.bestfeng.dydj.mbg.model.Address;
import com.bestfeng.dydj.mbg.model.AddressExample;
import com.bestfeng.dydj.service.AddressService;
import org.aurochsframework.boot.commons.service.AbstractGeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author bsetfeng
 * @since 1.0
 **/
@Service
public class AddressServiceImpl extends AbstractGeneralService<Address> implements AddressService {
    
    @Autowired
    private AddressMapper mapper;
    
    @Override
    public Object getMapper() {
        return mapper;
    }

    @Override
    public Object getExample() {
        return new AddressExample();
    }
}

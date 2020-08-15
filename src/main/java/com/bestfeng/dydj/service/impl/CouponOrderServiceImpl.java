package com.bestfeng.dydj.service.impl;

import com.bestfeng.dydj.mbg.mapper.CouponOrderMapper;
import com.bestfeng.dydj.mbg.model.CouponOrder;
import com.bestfeng.dydj.mbg.model.CouponOrderExample;
import com.bestfeng.dydj.service.CouponOrderService;
import org.aurochsframework.boot.commons.service.AbstractGeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author bsetfeng
 * @since 1.0
 **/
@Service
public class CouponOrderServiceImpl extends AbstractGeneralService<CouponOrder> implements CouponOrderService {

    @Autowired
    private CouponOrderMapper mapper;

    @Override
    public Object getMapper() {
        return mapper;
    }

    @Override
    public Object getExample() {
        return new CouponOrderExample();
    }
}

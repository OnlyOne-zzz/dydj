package com.bestfeng.dydj.service.impl;

import com.bestfeng.dydj.mbg.mapper.CouponMapper;
import com.bestfeng.dydj.mbg.model.Coupon;
import com.bestfeng.dydj.mbg.model.CouponExample;
import com.bestfeng.dydj.service.CouponService;
import org.aurochsframework.boot.commons.service.AbstractGeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author bsetfeng
 * @since 1.0
 **/
@Service
public class CouponServiceImpl extends AbstractGeneralService<Coupon> implements CouponService {

    @Autowired
    private CouponMapper mapper;

    @Override
    public Object getMapper() {
        return mapper;
    }

    @Override
    public Object getExample() {
        return new CouponExample();
    }
}

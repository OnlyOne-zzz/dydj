package com.bestfeng.dydj.service.impl;

import com.bestfeng.dydj.mbg.mapper.CouponRecordMapper;
import com.bestfeng.dydj.mbg.model.CouponRecord;
import com.bestfeng.dydj.mbg.model.CouponRecordExample;
import com.bestfeng.dydj.service.CouponRecordService;
import org.aurochsframework.boot.commons.service.AbstractGeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author bsetfeng
 * @since 1.0
 **/
@Service
public class CouponRecordServiceImpl extends AbstractGeneralService<CouponRecord> implements CouponRecordService {

    @Autowired
    private CouponRecordMapper mapper;

    @Override
    public Object getMapper() {
        return mapper;
    }

    @Override
    public Object getExample() {
        return new CouponRecordExample();
    }
}

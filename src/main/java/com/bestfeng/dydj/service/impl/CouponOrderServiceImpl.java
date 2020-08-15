package com.bestfeng.dydj.service.impl;

import com.bestfeng.dydj.constants.Constants;
import com.bestfeng.dydj.mbg.mapper.CouponOrderMapper;
import com.bestfeng.dydj.mbg.model.Coupon;
import com.bestfeng.dydj.mbg.model.CouponOrder;
import com.bestfeng.dydj.mbg.model.CouponOrderExample;
import com.bestfeng.dydj.mbg.model.UserInfo;
import com.bestfeng.dydj.service.CouponOrderService;
import com.bestfeng.dydj.service.CouponService;
import com.bestfeng.dydj.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.aurochsframework.boot.commons.param.QueryParam;
import org.aurochsframework.boot.commons.service.AbstractGeneralService;
import org.aurochsframework.boot.core.id.IDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author bsetfeng
 * @since 1.0
 **/
@Service
@Slf4j
public class CouponOrderServiceImpl extends AbstractGeneralService<CouponOrder> implements CouponOrderService {

    @Autowired
    private CouponOrderMapper mapper;

    @Autowired
    private CouponService couponService;

    @Autowired
    private UserInfoService userInfoService;

    @Override
    public Object getMapper() {
        return mapper;
    }

    @Override
    public Object getExample() {
        return new CouponOrderExample();
    }

    @Override
    public void receive(int type, int uid) {
        UserInfo userInfo = userInfoService.selectByUId(uid);
        List<Coupon> coupons = couponService.fetch(QueryParam.createQueryParam().and("type", type));
        if (CollectionUtils.isEmpty(coupons)) {
            log.warn("类型{}优惠券列表为空", type);
        } else {
            coupons.forEach(coupon -> saveCouponOrder(coupon, uid, userInfo.getTel()));
        }

    }

    protected CouponOrder saveCouponOrder(Coupon coupon, int uid, String tel) {
        CouponOrder couponOrder = new CouponOrder();
        couponOrder.setCreatetime( (int) (System.currentTimeMillis() / 1000));
        couponOrder.setMoney(coupon.getMoney().toString());
        couponOrder.setName(coupon.getTitle());
        couponOrder.setOrderid(IDGenerator.SNOW_FLAKE_STRING.generate());
        couponOrder.setUid(uid);
        couponOrder.setTitle(coupon.getTitle());
        couponOrder.setPid(coupon.getId());
        couponOrder.setUniacid(Constants.WECHAT_PID);
        couponOrder.setTel(tel);
        couponOrder.setStatus((byte) 0);
        couponOrder.setPaid((byte) 0);
        mapper.insert(couponOrder);
        return couponOrder;
    }
}

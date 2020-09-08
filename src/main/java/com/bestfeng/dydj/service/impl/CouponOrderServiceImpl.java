package com.bestfeng.dydj.service.impl;

import com.bestfeng.dydj.constants.Constants;
import com.bestfeng.dydj.enums.CouponTypeEnums;
import com.bestfeng.dydj.mbg.mapper.CouponOrderMapper;
import com.bestfeng.dydj.mbg.model.*;
import com.bestfeng.dydj.service.*;
import lombok.extern.slf4j.Slf4j;
import org.aurochsframework.boot.commons.param.QueryParam;
import org.aurochsframework.boot.commons.param.TermType;
import org.aurochsframework.boot.commons.service.AbstractGeneralService;
import org.aurochsframework.boot.core.id.IDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author bsetfeng
 * @since 1.0
 **/
@Service
@Slf4j
@Transactional
public class CouponOrderServiceImpl extends AbstractGeneralService<CouponOrder> implements CouponOrderService {

    @Autowired
    private CouponOrderMapper mapper;

    @Autowired
    private CouponService couponService;


    @Autowired
    private ContentService contentService;

    @Autowired
    private GoodItemsService goodItemsService;

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
    public void receive(Integer type, Integer uid) {
        UserInfo userInfo = userInfoService.selectByUId(uid);
        List<Coupon> coupons = couponService.fetch(QueryParam.createQueryParam().and("type", type));
        if (CollectionUtils.isEmpty(coupons)) {
            log.warn("类型{}优惠券列表为空", type);
        } else {
            coupons.forEach(coupon -> saveCouponOrder(coupon, uid, userInfo.getTel()));
        }

    }

    protected CouponOrder saveCouponOrder(Coupon coupon, Integer uid, String tel) {
        CouponOrder couponOrder = new CouponOrder();
        couponOrder.setCreatetime((int) (System.currentTimeMillis() / 1000));
        //有效时间15天
        couponOrder.setValidityPeriod((int) ((System.currentTimeMillis() / 1000) + (15 * 24 * 3600)));
        couponOrder.setMoney(coupon.getMoney().toString());
        couponOrder.setAllmoney(coupon.getAllmoney());
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

    /**
     * 用户优惠券列表
     *
     * @param uid
     * @return
     */
    @Override
    public List<CouponOrder> userCouponList(Integer uid, Integer status) {
        return fetch(QueryParam.createQueryParam().and("uid", uid)
                .and("status", status.byteValue())
        );
    }

    /**
     * 优惠券使用
     *
     * @param couponOrderId 优惠券ID
     */
    @Override
    public void use(Integer couponOrderId) {
        CouponOrder couponOrder = new CouponOrder();
        couponOrder.setId(couponOrderId);
        couponOrder.setStatus(CouponTypeEnums.USED.getValue());
        mapper.updateByPrimaryKeySelective(couponOrder);
    }

    /**
     * 取消优惠券使用
     *
     * @param couponOrderId 优惠券ID
     */
    @Override
    public void cancelUse(Integer couponOrderId) {
        CouponOrder couponOrder = new CouponOrder();
        couponOrder.setId(couponOrderId);
        couponOrder.setStatus(CouponTypeEnums.NOT_USED.getValue());
        mapper.updateByPrimaryKeySelective(couponOrder);
    }

    /**
     * 根据项目ID查询用户可使用的优惠券列表
     *
     * @param contentId 项目内容ID
     * @param uid       用户ID
     * @return
     */
    @Override
    public List<CouponOrder> availableCouponList(Integer contentId, Integer uid) {
        GoodItems goodItems = goodItemsService.fetchOne(QueryParam.createQueryParam().and("id", contentId));
        Float money = goodItems.getMoney();
        return fetch(QueryParam.createQueryParam()
                .and("allmoney", TermType.GTE, money)
                .and("status", CouponTypeEnums.NOT_USED.getValue())
                .and("uid", uid)
        );
    }
}

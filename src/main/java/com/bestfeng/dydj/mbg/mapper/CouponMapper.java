package com.bestfeng.dydj.mbg.mapper;

import com.bestfeng.dydj.mbg.model.Coupon;
import com.bestfeng.dydj.mbg.model.CouponExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CouponMapper {
    long countByExample(CouponExample example);

    int deleteByExample(CouponExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Coupon record);

    int insertSelective(Coupon record);

    List<Coupon> selectByExampleWithBLOBs(CouponExample example);

    List<Coupon> selectByExample(CouponExample example);

    Coupon selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Coupon record, @Param("example") CouponExample example);

    int updateByExampleWithBLOBs(@Param("record") Coupon record, @Param("example") CouponExample example);

    int updateByExample(@Param("record") Coupon record, @Param("example") CouponExample example);

    int updateByPrimaryKeySelective(Coupon record);

    int updateByPrimaryKeyWithBLOBs(Coupon record);

    int updateByPrimaryKey(Coupon record);
}
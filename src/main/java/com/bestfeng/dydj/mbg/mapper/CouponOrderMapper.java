package com.bestfeng.dydj.mbg.mapper;

import com.bestfeng.dydj.mbg.model.CouponOrder;
import com.bestfeng.dydj.mbg.model.CouponOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CouponOrderMapper {
    long countByExample(CouponOrderExample example);

    int deleteByExample(CouponOrderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CouponOrder record);

    int insertSelective(CouponOrder record);

    List<CouponOrder> selectByExample(CouponOrderExample example);

    CouponOrder selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CouponOrder record, @Param("example") CouponOrderExample example);

    int updateByExample(@Param("record") CouponOrder record, @Param("example") CouponOrderExample example);

    int updateByPrimaryKeySelective(CouponOrder record);

    int updateByPrimaryKey(CouponOrder record);
}
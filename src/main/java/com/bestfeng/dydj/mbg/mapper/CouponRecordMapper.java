package com.bestfeng.dydj.mbg.mapper;

import com.bestfeng.dydj.mbg.model.CouponRecord;
import com.bestfeng.dydj.mbg.model.CouponRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CouponRecordMapper {
    long countByExample(CouponRecordExample example);

    int deleteByExample(CouponRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CouponRecord record);

    int insertSelective(CouponRecord record);

    List<CouponRecord> selectByExample(CouponRecordExample example);

    CouponRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CouponRecord record, @Param("example") CouponRecordExample example);

    int updateByExample(@Param("record") CouponRecord record, @Param("example") CouponRecordExample example);

    int updateByPrimaryKeySelective(CouponRecord record);

    int updateByPrimaryKey(CouponRecord record);
}
package com.bestfeng.dydj.mbg.mapper;

import com.bestfeng.dydj.mbg.model.GoodItems;
import com.bestfeng.dydj.mbg.model.GoodItemsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GoodItemsMapper {
    long countByExample(GoodItemsExample example);

    int deleteByExample(GoodItemsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(GoodItems record);

    int insertSelective(GoodItems record);

    List<GoodItems> selectByExample(GoodItemsExample example);

    GoodItems selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") GoodItems record, @Param("example") GoodItemsExample example);

    int updateByExample(@Param("record") GoodItems record, @Param("example") GoodItemsExample example);

    int updateByPrimaryKeySelective(GoodItems record);

    int updateByPrimaryKey(GoodItems record);
}
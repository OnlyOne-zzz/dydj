package com.bestfeng.dydj.mbg.mapper;

import com.bestfeng.dydj.mbg.model.ContentGoodItems;
import com.bestfeng.dydj.mbg.model.ContentGoodItemsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ContentGoodItemsMapper {
    long countByExample(ContentGoodItemsExample example);

    int deleteByExample(ContentGoodItemsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ContentGoodItems record);

    int insertSelective(ContentGoodItems record);

    List<ContentGoodItems> selectByExample(ContentGoodItemsExample example);

    ContentGoodItems selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ContentGoodItems record, @Param("example") ContentGoodItemsExample example);

    int updateByExample(@Param("record") ContentGoodItems record, @Param("example") ContentGoodItemsExample example);

    int updateByPrimaryKeySelective(ContentGoodItems record);

    int updateByPrimaryKey(ContentGoodItems record);
}
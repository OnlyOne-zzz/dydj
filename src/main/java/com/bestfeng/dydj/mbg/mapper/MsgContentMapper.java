package com.bestfeng.dydj.mbg.mapper;

import com.bestfeng.dydj.mbg.model.MsgContent;
import com.bestfeng.dydj.mbg.model.MsgContentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MsgContentMapper {
    long countByExample(MsgContentExample example);

    int deleteByExample(MsgContentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MsgContent record);

    int insertSelective(MsgContent record);

    List<MsgContent> selectByExampleWithBLOBs(MsgContentExample example);

    List<MsgContent> selectByExample(MsgContentExample example);

    MsgContent selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MsgContent record, @Param("example") MsgContentExample example);

    int updateByExampleWithBLOBs(@Param("record") MsgContent record, @Param("example") MsgContentExample example);

    int updateByExample(@Param("record") MsgContent record, @Param("example") MsgContentExample example);

    int updateByPrimaryKeySelective(MsgContent record);

    int updateByPrimaryKeyWithBLOBs(MsgContent record);

    int updateByPrimaryKey(MsgContent record);
}
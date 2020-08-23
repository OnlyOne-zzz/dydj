package com.bestfeng.dydj.mbg.mapper;

import com.bestfeng.dydj.mbg.model.MsgIdList;
import com.bestfeng.dydj.mbg.model.MsgIdListExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MsgIdListMapper {
    long countByExample(MsgIdListExample example);

    int deleteByExample(MsgIdListExample example);

    int insert(MsgIdList record);

    int insertSelective(MsgIdList record);

    List<MsgIdList> selectByExample(MsgIdListExample example);

    int updateByExampleSelective(@Param("record") MsgIdList record, @Param("example") MsgIdListExample example);

    int updateByExample(@Param("record") MsgIdList record, @Param("example") MsgIdListExample example);
}
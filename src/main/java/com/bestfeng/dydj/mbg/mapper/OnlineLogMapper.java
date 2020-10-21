package com.bestfeng.dydj.mbg.mapper;

import com.bestfeng.dydj.mbg.model.OnlineLog;
import com.bestfeng.dydj.mbg.model.OnlineLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OnlineLogMapper {
    long countByExample(OnlineLogExample example);

    int deleteByExample(OnlineLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OnlineLog record);

    int insertSelective(OnlineLog record);

    List<OnlineLog> selectByExample(OnlineLogExample example);

    OnlineLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OnlineLog record, @Param("example") OnlineLogExample example);

    int updateByExample(@Param("record") OnlineLog record, @Param("example") OnlineLogExample example);

    int updateByPrimaryKeySelective(OnlineLog record);

    int updateByPrimaryKey(OnlineLog record);
}
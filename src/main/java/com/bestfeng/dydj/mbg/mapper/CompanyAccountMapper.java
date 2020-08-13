package com.bestfeng.dydj.mbg.mapper;

import com.bestfeng.dydj.mbg.model.CompanyAccount;
import com.bestfeng.dydj.mbg.model.CompanyAccountExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CompanyAccountMapper {
    long countByExample(CompanyAccountExample example);

    int deleteByExample(CompanyAccountExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CompanyAccount record);

    int insertSelective(CompanyAccount record);

    List<CompanyAccount> selectByExample(CompanyAccountExample example);

    CompanyAccount selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CompanyAccount record, @Param("example") CompanyAccountExample example);

    int updateByExample(@Param("record") CompanyAccount record, @Param("example") CompanyAccountExample example);

    int updateByPrimaryKeySelective(CompanyAccount record);

    int updateByPrimaryKey(CompanyAccount record);

    CompanyAccount selectObjByUid(Integer uid);
}
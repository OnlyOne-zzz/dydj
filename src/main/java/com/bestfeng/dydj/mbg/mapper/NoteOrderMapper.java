package com.bestfeng.dydj.mbg.mapper;

import com.bestfeng.dydj.mbg.model.NoteOrder;
import com.bestfeng.dydj.mbg.model.NoteOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NoteOrderMapper {
    long countByExample(NoteOrderExample example);

    int deleteByExample(NoteOrderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(NoteOrder record);

    int insertSelective(NoteOrder record);

    List<NoteOrder> selectByExample(NoteOrderExample example);

    NoteOrder selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") NoteOrder record, @Param("example") NoteOrderExample example);

    int updateByExample(@Param("record") NoteOrder record, @Param("example") NoteOrderExample example);

    int updateByPrimaryKeySelective(NoteOrder record);

    int updateByPrimaryKey(NoteOrder record);
}
package com.bestfeng.dydj.mbg.mapper;

import com.bestfeng.dydj.mbg.model.Note;
import com.bestfeng.dydj.mbg.model.NoteExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.aspectj.weaver.ast.Not;

public interface NoteMapper {
    long countByExample(NoteExample example);

    int deleteByExample(NoteExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Note record);

    int insertSelective(Note record);

    List<Note> selectByExampleWithBLOBs(NoteExample example);

    List<Note> selectByExample(NoteExample example);

    Note selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Note record, @Param("example") NoteExample example);

    int updateByExampleWithBLOBs(@Param("record") Note record, @Param("example") NoteExample example);

    int updateByExample(@Param("record") Note record, @Param("example") NoteExample example);

    int updateByPrimaryKeySelective(Note record);

    int updateByPrimaryKeyWithBLOBs(Note record);

    int updateByPrimaryKey(Note record);

    void updateStatusByLoginId(Note note);

    Note selectServiceStatus(Integer loginId);
}
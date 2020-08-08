package com.bestfeng.dydj.service.impl;

import com.bestfeng.dydj.mbg.mapper.NoteMapper;
import com.bestfeng.dydj.mbg.model.Note;
import com.bestfeng.dydj.mbg.model.NoteExample;
import com.bestfeng.dydj.service.NoteService;
import org.aurochsframework.boot.commons.service.AbstractGeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author bsetfeng
 * @since 1.0
 **/
@Service
public class NoteServiceImpl extends AbstractGeneralService<Note> implements NoteService {

    @Autowired
    private NoteMapper mapper;

    @Override
    public Object getMapper() {
        return mapper;
    }

    @Override
    public Object getExample() {
        return new NoteExample();
    }
}

package com.bestfeng.dydj.controller;

import com.bestfeng.dydj.mbg.model.Note;
import com.bestfeng.dydj.service.NoteService;
import io.swagger.annotations.Api;
import org.aurochsframework.boot.commons.controller.GeneralCrudController;
import org.aurochsframework.boot.commons.service.GeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bsetfeng
 * @since 1.0
 **/
@RestController
@RequestMapping("/note")
@Api(tags = "NoteController", description = "技师管理")
public class NoteController implements GeneralCrudController<Note> {


    @Autowired
    private NoteService noteService;


    @Override
    public GeneralService<Note> getService() {
        return noteService;
    }
}

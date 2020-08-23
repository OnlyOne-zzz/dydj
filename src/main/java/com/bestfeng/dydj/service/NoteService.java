package com.bestfeng.dydj.service;

import com.bestfeng.dydj.controller.request.NoteListRequest;
import com.bestfeng.dydj.mbg.model.Note;
import com.bestfeng.dydj.mbg.model.Order;
import org.aurochsframework.boot.commons.api.CommonPage;
import org.aurochsframework.boot.commons.service.GeneralService;

/**
 * @author bsetfeng
 * @since 1.0
 **/
public interface NoteService extends GeneralService<Note> {


    /**
     * 排序查询
     * @param request
     * @return
     */
    CommonPage<Note> paging(NoteListRequest request);


    /**
     * 根据名称模糊查询
     * @param name
     * @return
     */
    CommonPage<Note> pagingByName(String name);


}

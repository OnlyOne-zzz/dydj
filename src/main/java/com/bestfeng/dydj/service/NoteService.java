package com.bestfeng.dydj.service;

import com.bestfeng.dydj.controller.request.NoteListRequest;
import com.bestfeng.dydj.mbg.model.Note;
import com.bestfeng.dydj.mbg.model.Order;
import com.bestfeng.dydj.vo.NoteVo;
import org.aurochsframework.boot.commons.api.CommonPage;
import org.aurochsframework.boot.commons.service.GeneralService;

import java.util.Date;

/**
 * @author bsetfeng
 * @since 1.0
 **/
public interface NoteService extends GeneralService<Note> {


    /**
     * 排序查询
     *
     * @param request
     * @return
     */
    CommonPage<NoteVo> paging(NoteListRequest request);


    /**
     * 根据名称模糊查询
     *
     * @param name
     * @return
     */
    CommonPage<NoteVo> pagingByName(String name);

    void updateStatusByLoginId(Note note);

    /**
     * 自定义技师上下线区间
     * @param startTime
     * @param endTime
     * @param loginid
     */
    void customizeOnlineInterval(Date startTime, Date endTime, Integer loginid);

    Note selectServiceStatus(Integer loginId);


}

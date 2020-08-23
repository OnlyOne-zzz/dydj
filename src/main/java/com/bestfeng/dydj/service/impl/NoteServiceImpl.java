package com.bestfeng.dydj.service.impl;

import com.bestfeng.dydj.controller.request.NoteListRequest;
import com.bestfeng.dydj.enums.NoteServiceStatusEnums;
import com.bestfeng.dydj.mbg.mapper.NoteMapper;
import com.bestfeng.dydj.mbg.model.Note;
import com.bestfeng.dydj.mbg.model.NoteExample;
import com.bestfeng.dydj.service.NoteService;
import org.aurochsframework.boot.commons.api.CommonPage;
import org.aurochsframework.boot.commons.param.QueryParam;
import org.aurochsframework.boot.commons.param.Sort;
import org.aurochsframework.boot.commons.service.AbstractGeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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


    @Override
    public CommonPage<Note> paging(NoteListRequest request) {
        QueryParam queryParam = QueryParam.createQueryParam();
        if (request.getServiceStatus() != 0){
            queryParam.and("serviceStatus", NoteServiceStatusEnums.ofValue(request.getServiceStatus()));
        }
        Sort sort = new Sort();
        if (StringUtils.hasText(request.getOrderColumn())){
            sort.setName(request.getOrderColumn());
            sort.setOrder(request.getOrderType());
        }
        queryParam.orderBy(sort);
        return paging(queryParam);
    }

    @Override
    public CommonPage<Note> pagingByName(String name) {
        QueryParam queryParam = QueryParam.createQueryParam();
        if (StringUtils.hasText(name)){
            queryParam.and("name", "%".concat(name).concat("%"));
        }
        return paging(queryParam);
    }
}

package com.bestfeng.dydj.service.impl;

import com.bestfeng.dydj.configuration.LocalAccessConfig;
import com.bestfeng.dydj.controller.request.NoteListRequest;
import com.bestfeng.dydj.enums.NoteServiceStatusEnums;
import com.bestfeng.dydj.mbg.mapper.NoteMapper;
import com.bestfeng.dydj.mbg.model.Note;
import com.bestfeng.dydj.mbg.model.NoteExample;
import com.bestfeng.dydj.service.CommentService;
import com.bestfeng.dydj.service.NoteService;
import com.bestfeng.dydj.service.OrderService;
import com.bestfeng.dydj.vo.NoteVo;
import org.aurochsframework.boot.commons.api.CommonPage;
import org.aurochsframework.boot.commons.param.QueryParam;
import org.aurochsframework.boot.commons.param.Sort;
import org.aurochsframework.boot.commons.service.AbstractGeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author bsetfeng
 * @since 1.0
 **/
@Service
public class NoteServiceImpl extends AbstractGeneralService<Note> implements NoteService {

    @Autowired
    private NoteMapper mapper;

    @Autowired
    private LocalAccessConfig localAccessConfig;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CommentService commentService;

    @Override
    public Object getMapper() {
        return mapper;
    }

    @Override
    public Object getExample() {
        return new NoteExample();
    }


    @Override
    public CommonPage<NoteVo> paging(NoteListRequest request) {
        QueryParam queryParam = QueryParam.createQueryParam();
        if (request.getServiceStatus() != 0) {
            queryParam.and("serviceStatus", NoteServiceStatusEnums.ofValue(request.getServiceStatus()).getValue());
        }
        Sort sort = new Sort();
        if (StringUtils.hasText(request.getOrderColumn())) {
            sort.setName(request.getOrderColumn());
            sort.setOrder(request.getOrderType());
            queryParam.orderBy(sort);
        }
        return query(queryParam);
    }

    @Override
    public CommonPage<NoteVo> pagingByName(String name) {
        QueryParam queryParam = QueryParam.createQueryParam();
        if (StringUtils.hasText(name)) {
            queryParam.and("name", "%".concat(name).concat("%"));
        }
        return query(queryParam);
    }

    private CommonPage<NoteVo> query(QueryParam queryParam) {
        CommonPage<Note> pages = paging(queryParam);
        Map<Integer, Long> orderGroup = orderService.endOrderGroup();
        Map<Integer, Double> commentGroup = commentService.storeGroup();
        return CommonPage.restPage(pages, () -> pages.getList().stream()
                .peek(note -> note.setAvatarurl(localAccessConfig.getUri().concat(note.getAvatarurl())))
                .sorted(Comparator.comparingInt(Note::getServiceStatus))
                .map(note -> NoteVo.of(note, orderGroup.getOrDefault(note.getId(), 10L), commentGroup.getOrDefault(note.getId(), 4.5D)))
                .collect(Collectors.toList()));
    }

}

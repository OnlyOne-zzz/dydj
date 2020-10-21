package com.bestfeng.dydj.service.impl;

import com.bestfeng.dydj.configuration.LocalAccessConfig;
import com.bestfeng.dydj.controller.request.NoteListRequest;
import com.bestfeng.dydj.enums.NoteServiceStatusEnums;
import com.bestfeng.dydj.mbg.mapper.NoteMapper;
import com.bestfeng.dydj.mbg.mapper.NoteOrderMapper;
import com.bestfeng.dydj.mbg.mapper.OnlineLogMapper;
import com.bestfeng.dydj.mbg.model.Category;
import com.bestfeng.dydj.mbg.model.Note;
import com.bestfeng.dydj.mbg.model.NoteExample;
import com.bestfeng.dydj.mbg.model.OnlineLog;
import com.bestfeng.dydj.service.CategoryService;
import com.bestfeng.dydj.service.CommentService;
import com.bestfeng.dydj.service.NoteService;
import com.bestfeng.dydj.service.OrderService;
import com.bestfeng.dydj.utils.DateUtil;
import com.bestfeng.dydj.vo.NoteVo;
import lombok.extern.slf4j.Slf4j;
import org.aurochsframework.boot.commons.api.CommonPage;
import org.aurochsframework.boot.commons.param.QueryParam;
import org.aurochsframework.boot.commons.param.Sort;
import org.aurochsframework.boot.commons.param.TermType;
import org.aurochsframework.boot.commons.service.AbstractGeneralService;
import org.aurochsframework.boot.core.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author bsetfeng
 * @since 1.0
 **/
@Service
@Slf4j
public class NoteServiceImpl extends AbstractGeneralService<Note> implements NoteService {

    @Autowired
    private NoteMapper mapper;

    @Autowired
    private LocalAccessConfig localAccessConfig;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CommentService commentService;
    @Autowired
    private NoteOrderMapper noteOrderMapper;
    @Autowired
    private OnlineLogMapper onlineLogMapper;

    private final static String DEFAULT_CATE_NAME = "暂无推荐";

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
            queryParam.and("shopname", TermType.LIKE, "%".concat(name).concat("%"));
        }
        return query(queryParam);
    }

    @Override
    public void updateStatusByLoginId(Note note) {
        Integer serviceStatus = note.getServiceStatus();
        Integer loginid = note.getLoginid();
        Note noteObj = this.selectServiceStatus(loginid);
        Integer metaStatus = noteObj.getServiceStatus();
        if(serviceStatus==metaStatus){
            log.error("技师状态的上线/下线 状态一致不需要操作 metaStatus={}",metaStatus);
            throw new BusinessException("当前状态不能上线和下线");
        }
        Integer count = noteOrderMapper.selectCountByNoteId(noteObj.getId());
        /**是否存在这个技师的订单在未完成的订单*/
        if(count>0){
            log.error("技师状态的上线/下线 异常操作");
            throw new BusinessException("有未完成的订单不可更换技师服务的状态");
        }
        mapper.updateStatusByLoginId(note);
        /**操作日志*/
        OnlineLog onlineLog = new OnlineLog();
        onlineLog.setNoteId(noteObj.getId());
        onlineLog.setServiceStatus(serviceStatus);
        onlineLog.setCreateTime(DateUtil.getCurDate());
        onlineLogMapper.insert(onlineLog);

    }

    @Override
    public Note selectServiceStatus(Integer loginId) {
        return mapper.selectServiceStatus(loginId);
    }

    private CommonPage<NoteVo> query(QueryParam queryParam) {
        queryParam.setPageSize(100);
        CommonPage<Note> pages = paging(queryParam);
        Map<Integer, Long> orderGroup = orderService.endOrderGroup();
        Map<Integer, Double> commentGroup = commentService.storeGroup();


        return CommonPage.restPage(pages, () -> pages.getList().stream()
                .peek(note -> note.setAvatarurl(localAccessConfig.getUri() + note.getAvatarurl()))
                .sorted(Comparator.comparingInt(Note::getServiceStatus))
                .map(note -> NoteVo.of(note, getNoteServiceFrequency(orderGroup, note.getId(), note.getBasicServiceFrequency()), getCateName(categoryService.idGroup(), note.getSpecial()),
                        getNoteScore(commentGroup, note.getId())))
                .collect(Collectors.toList()));
    }

    private String getCateName(Map<Integer, List<Category>> idGroup, String special) {
        int integer;
        try {
            integer = Integer.parseInt(special);
        } catch (NumberFormatException e) {
            return DEFAULT_CATE_NAME;
        }
        return Optional.ofNullable(idGroup.get(integer))
                .map(list -> {
                    if (!CollectionUtils.isEmpty(list)) {
                        return list.get(0).getName();
                    }
                    return DEFAULT_CATE_NAME;
                }).orElse(DEFAULT_CATE_NAME);

    }

    private Double getNoteScore(Map<Integer, Double> commentGroup, int noteId) {
        return Math.max(commentGroup.getOrDefault(noteId, 4.5D), 4.5D);
    }

    private long getNoteServiceFrequency(Map<Integer, Long> orderGroup, int noteId, int basicServiceFrequency) {
        return orderGroup.getOrDefault(noteId, 0L) + basicServiceFrequency;
    }

}

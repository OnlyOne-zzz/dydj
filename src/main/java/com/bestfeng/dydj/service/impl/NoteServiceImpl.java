package com.bestfeng.dydj.service.impl;

import com.alibaba.fastjson.JSON;
import com.bestfeng.dydj.configuration.LocalAccessConfig;
import com.bestfeng.dydj.controller.request.NoteListRequest;
import com.bestfeng.dydj.enums.NoteServiceStatusEnums;
import com.bestfeng.dydj.mbg.mapper.NoteMapper;
import com.bestfeng.dydj.mbg.mapper.NoteOrderMapper;
import com.bestfeng.dydj.mbg.mapper.OnlineLogMapper;
import com.bestfeng.dydj.mbg.model.*;
import com.bestfeng.dydj.service.*;
import com.bestfeng.dydj.utils.DateUtil;
import com.bestfeng.dydj.vo.NoteVo;
import lombok.extern.slf4j.Slf4j;
import org.aurochsframework.boot.commons.api.CommonPage;
import org.aurochsframework.boot.commons.param.QueryParam;
import org.aurochsframework.boot.commons.param.Sort;
import org.aurochsframework.boot.commons.param.Term;
import org.aurochsframework.boot.commons.param.TermType;
import org.aurochsframework.boot.commons.service.AbstractGeneralService;
import org.aurochsframework.boot.core.exceptions.BusinessException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;
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
    private MsgContentService msgContentService;

    @Autowired
    private CommentService commentService;
    @Autowired
    private NoteOrderMapper noteOrderMapper;
    @Autowired
    private OnlineLogMapper onlineLogMapper;

    @Autowired
    private ScheduledExecutorService executorService;

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
        log.warn("收到技师列表查询参数:{}", JSON.toJSONString(request));
        QueryParam queryParam = QueryParam.createQueryParam();
        if (NoteServiceStatusEnums.SERVICEABLE.getValue().equals(request.getServiceStatus())) {
            queryParam.and("serviceStatus", NoteServiceStatusEnums.ofValue(request.getServiceStatus()).getValue());
        }
        // TODO: 2020/11/16 服务中的技师，只要不是可服务就默认服务中
        if (NoteServiceStatusEnums.IN_SERVICE.getValue().equals(request.getServiceStatus())) {
            queryParam.and("serviceStatus", TermType.NOT, NoteServiceStatusEnums.SERVICEABLE.getValue());
        }
        Sort sort = new Sort();
        if (StringUtils.hasText(request.getOrderColumn())) {
            sort.setName(request.getOrderColumn());
            sort.setOrder(request.getOrderType());
            queryParam.orderBy(sort);
        }
        CommonPage<NoteVo> page = query(queryParam);
        List<NoteVo> noteVos = notesIntersection(request.getContentPid(), page.getList());

        // TODO: 2020/11/13 技师数据量大于100是时，分页逻辑需重做

        CommonPage<NoteVo> commonPage = new CommonPage<>();
        commonPage.setList(noteVos);
        commonPage.setTotal((long) noteVos.size());
        commonPage.setPageNum(1);
        commonPage.setPageSize(100);
        commonPage.setTotalPage(1);
        return commonPage;
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
        if (serviceStatus.equals(metaStatus)) {
            log.warn("技师状态的上线/下线 状态一致不需要操作 metaStatus={}", metaStatus);
            return;
        }
        Integer count = noteOrderMapper.selectCountByNoteId(noteObj.getId());
        /**是否存在这个技师的订单在未完成的订单*/
        if (count > 0) {
            log.error("技师状态的上线/下线 异常操作");
            throw new BusinessException("有未完成的订单不可更换技师服务的状态");
        }
        mapper.updateStatusByLoginId(note);
        /**操作日志*/
        noteOnlineLogRecord(noteObj.getId(), serviceStatus);
    }

    @Override
    public void customizeOnlineInterval(Date startTime, Date endTime, Integer loginid) {
        Note noteObj = this.selectServiceStatus(loginid);
        customizeIntervalPointExecute(startTime, endTime, () -> {
            updateNoteStatus(noteObj, NoteServiceStatusEnums.SERVICEABLE.getValue());
        }, () -> {
            updateNoteStatus(noteObj, NoteServiceStatusEnums.OFFLINE.getValue());
        });
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

    private List<NoteVo> notesIntersection(Integer pid, List<NoteVo> noteVos) {
        if (pid == null) {
            return noteVos;
        }
        List<MsgContent> msgContents = msgContentService.fetch(QueryParam.createQueryParam().and("pid", pid).and("status", 0));
        List<Integer> cNoteIds = msgContents.stream()
                .map(MsgContent::getNoteid)
                .collect(Collectors.toList());
        return noteVos.stream()
                .filter(noteVo -> cNoteIds.contains(noteVo.getId()))
                .collect(Collectors.toList());
    }

    private void updateNoteStatus(Note noteSource, int serviceStatus) {
        Note note = new Note();
        BeanUtils.copyProperties(noteSource, note);
        note.setServiceStatus(serviceStatus);
        mapper.updateStatusByLoginId(note);
        noteOnlineLogRecord(note.getId(), serviceStatus);
    }

    /**
     * 自定义时间区间节点执行器
     *
     * @param startPoint   开始时间
     * @param endPoint     结束时长(单位小时)
     * @param startCommand
     * @param endCommand
     */
    private void customizeIntervalPointExecute(Date startPoint, Date endPoint, Runnable startCommand, Runnable endCommand) {
        long delay = startPoint.getTime() - System.currentTimeMillis();
        long endDelay = endPoint.getTime() - System.currentTimeMillis();
        if (delay < 0) {
            delay = 0;
        }
        if (endDelay <= delay) {
            throw new BusinessException("设置技师上下线时间区间错误，开始时间:" + startPoint.getTime() + "结束时间：" + endPoint.getTime());
        }
        executorService.schedule(startCommand, delay, TimeUnit.MILLISECONDS);
        executorService.schedule(endCommand, endDelay, TimeUnit.MILLISECONDS);
    }

    /**
     * 记录技师上下线日志
     *
     * @param noteId
     * @param serviceStatus
     */
    private void noteOnlineLogRecord(int noteId, int serviceStatus) {
        OnlineLog onlineLog = new OnlineLog();
        onlineLog.setNoteId(noteId);
        onlineLog.setServiceStatus(serviceStatus);
        if (NoteServiceStatusEnums.SERVICEABLE.getValue().equals(serviceStatus)) {
            onlineLog.setServiceStatusRemake("上线操作");
        } else {
            onlineLog.setServiceStatusRemake("下线操作");
        }
        onlineLog.setCreateTime(DateUtil.getCurDate());
        onlineLogMapper.insert(onlineLog);
    }

}

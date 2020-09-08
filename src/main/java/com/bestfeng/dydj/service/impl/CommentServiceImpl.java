package com.bestfeng.dydj.service.impl;

import com.bestfeng.dydj.mbg.mapper.CommentMapper;
import com.bestfeng.dydj.mbg.model.Comment;
import com.bestfeng.dydj.mbg.model.CommentExample;
import com.bestfeng.dydj.service.CommentService;
import org.aurochsframework.boot.commons.service.AbstractGeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author bsetfeng
 * @since 1.0
 **/
@Service
public class CommentServiceImpl extends AbstractGeneralService<Comment> implements CommentService {

    @Autowired
    private CommentMapper mapper;

    @Override
    public Object getMapper() {
        return mapper;
    }

    @Override
    public Object getExample() {
        return new CommentExample();
    }

    @Override
    public Map<Integer, Double> storeGroup() {
        return fetchAll()
                .stream()
                .collect(Collectors.groupingBy(Comment::getStoreid, Collectors.averagingDouble(Comment::getScore)));
    }
}

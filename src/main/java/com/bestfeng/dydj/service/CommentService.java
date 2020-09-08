package com.bestfeng.dydj.service;

import com.bestfeng.dydj.mbg.model.Comment;
import org.aurochsframework.boot.commons.service.GeneralService;

import java.util.Map;

/**
 * 优惠券
 *
 * @author bsetfeng
 * @since 1.0
 **/
public interface CommentService extends GeneralService<Comment> {

    /**
     * 技师ID分组统计
     * @return
     */
    Map<Integer, Double> storeGroup();
}

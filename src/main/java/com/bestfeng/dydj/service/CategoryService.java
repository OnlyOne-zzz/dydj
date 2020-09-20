package com.bestfeng.dydj.service;

import com.bestfeng.dydj.mbg.model.Category;
import com.bestfeng.dydj.mbg.model.Order;
import org.aurochsframework.boot.commons.service.GeneralService;

import java.util.List;
import java.util.Map;

/**
 * @author bsetfeng
 * @since 1.0
 **/
public interface CategoryService extends GeneralService<Category> {

    /**
     *
     * @return
     */
    Map<Integer, List<Category>>  idGroup();
}

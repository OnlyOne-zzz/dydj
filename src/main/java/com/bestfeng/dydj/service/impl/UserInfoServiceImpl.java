package com.bestfeng.dydj.service.impl;

import com.bestfeng.dydj.enums.ApiErrorCodeEnums;
import com.bestfeng.dydj.mbg.mapper.UserInfoMapper;
import com.bestfeng.dydj.mbg.model.UserInfo;
import com.bestfeng.dydj.mbg.model.UserInfoExample;
import com.bestfeng.dydj.service.UserInfoService;
import org.aurochsframework.boot.commons.service.AbstractGeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author bsetfeng
 * @since 1.0
 **/
@Service
public class UserInfoServiceImpl extends AbstractGeneralService<UserInfo> implements UserInfoService {

    @Autowired
    private UserInfoMapper mapper;

    @Override
    public Object getMapper() {
        return mapper;
    }

    @Override
    public Object getExample() {
        return new UserInfoExample();
    }

    @Override
    public UserInfo selectById(Integer id) {
        UserInfo userInfo = mapper.selectByPrimaryKey(id);
        if (userInfo == null) {
            throw ApiErrorCodeEnums.businessException(ApiErrorCodeEnums.USER_NOT_FOUND);
        }
        return userInfo;
    }
}

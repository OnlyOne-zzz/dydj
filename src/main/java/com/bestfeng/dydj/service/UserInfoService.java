package com.bestfeng.dydj.service;

import com.bestfeng.dydj.mbg.model.UserInfo;
import org.aurochsframework.boot.commons.service.GeneralService;

/**
 * @author bsetfeng
 * @since 1.0
 **/
public interface UserInfoService extends GeneralService<UserInfo> {

    UserInfo selectByUId(Integer uid);
}

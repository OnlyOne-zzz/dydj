package com.bestfeng.dydj.manager.request;

import com.bestfeng.dydj.controller.request.BasicRequest;

/**
 * @author bsetfeng
 * @since 1.0
 **/
public interface SignVerifySupport {

    /**
     * 签名密钥
     */
    String SIGN_KEY = "1qaz2wsx";


    /**
     * 验签
     *
     * @see org.aurochsframework.boot.core.exceptions.BusinessException
     * @param t   参数
     * @param <T>
     */
    <T extends BasicRequest> void check(T t);


}

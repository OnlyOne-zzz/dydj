package com.bestfeng.dydj.constants;

/**
 * zh
 */
public class Constants {
    /**技师按摩订单前缀*/
    public static final String ORDER_NO_PRE = "JSAM";
    /**请求header的sign键*/
    public static final String SIGN = "sign";

    /**创建订单重复提交锁*/
    public static final String SAVE_ORDER_LOCK_KEY = "save:order:lock:key:%s";

    /**退款*/
    public static final String REFUND_ORDER_LOCK_KEY = "refund:order:lock:key:%s";

    /**修改订单重复提交锁*/
    public static final String UPDATE_ORDER_LOCK_KEY = "update:order:lock:key:%s";

    /**公众号PID*/
    public static final int WECHAT_PID = 3;
}

package com.bestfeng.dydj.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RedisLock {
    /**锁的前缀名字*/
    String name() default "";
    /**锁的具体key值*/
    String key();

    int retryTimes() default 0;

    long keepMillis() default 30L;

    long sleepMillis() default 1L;
}

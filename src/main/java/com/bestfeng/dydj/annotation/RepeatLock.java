package com.bestfeng.dydj.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RepeatLock {
    /**锁的前缀名字*/
    String name() default "";
    /**锁的具体key值*/
    String key();
}

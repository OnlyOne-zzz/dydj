package com.bestfeng.dydj.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RepeatLock {
    String key() default "";
    String value();
}

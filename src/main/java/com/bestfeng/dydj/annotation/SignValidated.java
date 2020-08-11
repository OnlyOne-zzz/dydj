package com.bestfeng.dydj.annotation;

import java.lang.annotation.*;

/**
 * 标注在class 上 所有的HTTP方法都需要签名
 * 标注在单独在method上 仅标注的方法会验证签名
 * @author zh
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface SignValidated {

}

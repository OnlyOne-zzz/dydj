package com.bestfeng.dydj.aspect;


import com.bestfeng.dydj.annotation.RepeatLock;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.map.LRUMap;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.aurochsframework.boot.core.exceptions.BusinessException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


/***
 * 通过LRUMap防重提交,单机多线程会有问题不？
 * 后面再考虑是否需要redis 实现分布式锁
 */
@Aspect
@Component
@Order(Integer.MAX_VALUE-1)
@Slf4j
public class LruRepeatLockAspect {

    private static LRUMap<String, Object> reqCache = new LRUMap<>(100);
    /**
     * 切点 RepeatLock注解
     */
    @Pointcut(value ="@annotation(com.bestfeng.dydj.annotation.RepeatLock)")
    private void lockPoint(){
    }

    @Around(value = "lockPoint()")
    public void lockAdvice(ProceedingJoinPoint point)throws Throwable{
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        Method method = methodSignature.getMethod();
        RepeatLock repeatLock = method.getAnnotation(RepeatLock.class);
        String key = repeatLock.key();
        String value = repeatLock.value();
        String lockKey = String.format(key,value);
        // 锁当前方法
        synchronized (method){
            if(reqCache.containsKey(lockKey)){
                  throw new BusinessException("请勿重复提交");
            }
            reqCache.put(lockKey,System.currentTimeMillis());
        }
        try {
           point.proceed();
        }catch (Exception e){
            throw e;
        }finally {
            reqCache.remove(lockKey);
        }
    }

}

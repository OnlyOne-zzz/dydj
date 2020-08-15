package com.bestfeng.dydj.aspect;


import com.bestfeng.dydj.annotation.RedisLock;
import com.bestfeng.dydj.utils.RedisDistributedLock;
import com.bestfeng.dydj.utils.RedisUtils;
import com.bestfeng.dydj.utils.SpelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.map.LRUMap;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.aurochsframework.boot.core.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


@Aspect
@Component
@Order(Integer.MAX_VALUE-1)
@Slf4j
public class RedisLockAspect {

    private static LRUMap<String, Object> reqCache = new LRUMap<>(100);

    @Autowired
    private RedisDistributedLock distributedLock;
    /**
     * 切点 RepeatLock注解
     */
    @Pointcut(value ="@annotation(com.bestfeng.dydj.annotation.RedisLock)")
    private void lockPoint(){
    }

    @Around(value = "lockPoint()")
    public void lockAdvice(ProceedingJoinPoint point)throws Throwable{
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        Method method = methodSignature.getMethod();
        RedisLock redisLock = method.getAnnotation(RedisLock.class);
        String name = redisLock.name();
        String key = redisLock.key();
        String lockKey = String.format(name, SpelUtil.parse(key, method, point.getArgs()));
        boolean lock = distributedLock.lock(lockKey, redisLock.keepMillis(), redisLock.retryTimes(),redisLock.sleepMillis());
        if (!lock) {
            log.error("lock failed : " + key);
            throw new BusinessException("lock failed");
        }
        try {
            // 得到锁，执行目标方法，释放锁
            point.proceed();
        } catch (Exception e) {
            log.error("execute locked method exception : {} {}", key,e.getMessage());
            throw e;
        } finally {
            boolean unResult = distributedLock.unLock(key);
            log.debug("unlock : " + key + (unResult ? " success" : " failed"));
        }
    }

}

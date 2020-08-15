package com.bestfeng.dydj.utils;

import com.google.common.base.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


/**
 * redis实现的分布式锁
 *
 * @author zh
 * @date 2019年12月26日
 */
public class RedisDistributedLock {

    private static final Logger logger = LoggerFactory.getLogger(RedisDistributedLock.class);

    private RedisTemplate<String, Object> redisTemplate;

    private ThreadLocal<String> lockFlag = new ThreadLocal<String>();

    private static final StringRedisSerializer stringRedisSerializer = new StringRedisSerializer(Charsets.UTF_8);

    public static final String UNLOCK_LUA = "local current = redis.call('get', KEYS[1]) if current == ARGV[1] then redis.call('del', KEYS[1]) return '1' end return '0'";

    public RedisDistributedLock(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 获取分布式锁
     *
     * @param key         锁的资源标识
     * @param keepMillis  已获取锁后自动释放时间（默认30秒）
     * @param retryTimes  获取锁失败重试次数（默认3次）
     * @param sleepMillis 重试的间隔时间（默认1秒）
     * @return true成功, false失败
     */
    public boolean lock(String key, long keepMillis, int retryTimes, long sleepMillis) {
        boolean result = setRedis(key, keepMillis);
        // 如果获取锁失败，按照传入的重试次数进行重试
        while ((!result) && retryTimes-- > 0) {
            try {
                logger.warn("lock failed {}, retrying... {}", key, retryTimes);
                Thread.sleep(sleepMillis);
            } catch (InterruptedException e) {
                return false;
            }
            result = setRedis(key, keepMillis);
        }
        return result;
    }

    /**
     * 获取锁 如果获取
     *
     * @param key
     * @return
     */
    public boolean lock(String key) {
        // 如果获取锁失败，按照传入的重试次数进行重试
        int retryTimes = 0;
        long sleepMillis = 0;
        long keepMillis = 30;
        return lock(key, keepMillis, retryTimes, sleepMillis);
    }

    /**
     * 释放分布式锁
     *
     * @param key 锁的资源标识
     * @return true成功, false失败
     */
    public boolean unLock(String key) {
        try {
            // 封装参数
            List<String> keys = new ArrayList<>();
            keys.add(key);// 锁资源标识
            //keys.add(lockFlag.get());// 锁的拥有者标记，每个线程释放锁的时候只能释放自己的锁

            // 释放锁的时候，有可能因为持锁之后业务方法执行时间大于锁的有效期，此时有可能已经被另外一个线程持有锁，所以不能直接删除
            // 通过Lua脚本来达到释放锁的原子操作，删除redis中匹配value的key，可以避免由于方法执行时间过长而redis锁自动过期失效的时候误删其他线程的锁
            DefaultRedisScript<String> lockScript = new DefaultRedisScript<>();
            lockScript.setScriptText(UNLOCK_LUA);
            lockScript.setResultType(String.class);
            String result = redisTemplate.execute(lockScript, stringRedisSerializer, stringRedisSerializer, keys, lockFlag.get());
            return "1".equals(result) ? true : false;
        } catch (Exception e) {
            logger.error("release lock occured an exception {}", key, e);
        } finally {
            lockFlag.remove();
        }
        return false;
    }


    private boolean setRedis(String key, long keepMillis) {
        try {
            // 需要保证设置Redis值和过期时间的原子性，避免两次Redis操作期间出现意外而导致的锁不能释放的问题
            // 通过RedisConnection操作多条命令保证原子性
            // 锁必须要有一个拥有者的标记，每个线程释放锁的时候只能释放自己的锁
            String value = UUID.randomUUID().toString().replaceAll("-", "");
            lockFlag.set(value);
            //NX:表示只有当锁定资源不存在的时候才能SET成功。利用Redis的原子性，保证了只有第一个请求线程才能获得锁，而之后的所有线程在锁未被释放之前都不能获得锁
            //EX:expire表示锁定的资源的自动过期时间，单位是毫秒
            return redisTemplate.execute(connection -> connection.set(key.getBytes(), value.getBytes(), Expiration.from(keepMillis, TimeUnit.MILLISECONDS), RedisStringCommands.SetOption.ifAbsent()), true);
        } catch (Exception e) {
            logger.error("set redis occured an exception {}", key, e);
        }
        return false;
    }
}
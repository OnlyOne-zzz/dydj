package com.bestfeng.dydj.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis操作工具
 * @author: zh
 */
@Component
@ConditionalOnProperty(prefix = "spring.redis", name = { "host", "port" })
public class RedisUtils {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private ValueOperations<String, Object> valueOperations;

	@Autowired
	private HashOperations<String, String, Object> hashOperations;

	@Autowired
	private ListOperations<String, Object> listOperations;

	/* @Autowired
	 private SetOperations<String, Object> setOperations;
	
	 @Autowired
	 private ZSetOperations<String, Object> zSetOperations;*/

	public RedisTemplate<String, Object> getRedisTemplate(){
		return redisTemplate;
	}

	/**
	 * valueOperations写入缓存
	 * @param key 键
	 * @param value 值
	 */
	public void set(String key, Object value) {
		valueOperations.set(key, toJson(value));
	}

	/**
	 * valueOperations写入缓存
	 * @param key 键
	 * @param value 值
	 * @param expire 过期时间(s)
	 */
	public void set(String key, Object value, long expire) {
		set(key, toJson(value), expire, TimeUnit.SECONDS);
	}

	/**
	 * valueOperations写入缓存
	 * @param key 键
	 * @param value 值
	 * @param expire 过期时间(s)
	 * @param expire 过期时间(s)
	 */
	public void set(String key, Object value, long expire, TimeUnit unit) {
		valueOperations.set(key, toJson(value), expire, unit);
	}

	/**
	 * hashOperations写入缓存
	 * @param key 键
	 * @param hashKey key中的hashkey
	 * @param value key中的hashkey的值
	 * @param expire 过期时间(s)
	 */
	public void setHash(String key, String hashKey, Object value, long expire) {
		hashOperations.put(key, hashKey, value);
		if(expire>0){
			redisTemplate.expire(key, expire, TimeUnit.SECONDS);
		}
	}
	public void setHash(String key, String hashKey, Object value) {
		setHash(key,hashKey,value,-1);
	}
	/**
	 * listOperations写入列表缓存
	 * @param key 键
	 * @param value 值
	 * @return 位置
	 */
	public Long leftPush(String key, Object value) {
		return listOperations.leftPush(key, value);
	}

	/**
	 * valueOperations读取缓存
	 * @param key 键
	 * @return String 值
	 */
	public String get(String key) {
		Object valueObj = valueOperations.get(key);
		if(Objects.nonNull(valueObj)) {
			return valueObj.toString();
		}
		return null;
		
	}

	/**
	 * valueOperations读取缓存
	 * @param key 键
	 * @param clazz 类型
	 * @return <T> T 指定类型结果
	 */
	public <T> T get(String key, Class<T> clazz) {
		String value = get(key);
		return value == null ? null : fromJson(value, clazz);
	}

	/**
	 * hashOperations读取缓存
	 * @param key 键
	 * @param hashKey key中的hashkey
	 * @return Object key中的hashkey的值
	 */
	public Object getHash(String key, String hashKey) {
		return hashOperations.get(key, hashKey);
	}

	/**
	 * hashOperations读取缓存
	 * @param key 键
	 * @param hashKey key中的hashkey
	 * @param clazz key中的hashkey对应的具体类型
	 * @return Object key中的hashkey的值
	 */
	@SuppressWarnings("unchecked")
	public <T> T getHash(String key, String hashKey, Class<T> clazz) {
		Object object = hashOperations.get(key, hashKey);
		if (clazz.isInstance(object)) {
			return (T) object;
		} else {
			throw new ClassCastException("cannot be cast to " + clazz.getName());
		}
	}

	/**
	 * listOperations读取分页缓存
	 * @param key 键
	 * @param page 当前页码
	 * @param pageSize 每页条数
	 * @return List<Object> 分页对象列表
	 */
	public <T> List<T> getPageList(String key, int page, int pageSize) {
		List<Object> result = listOperations.range(key, (page - 1) * pageSize, page * pageSize - 1);
		return FastJsons.convertJSONToArray(FastJsons.convertObjectToJSON(result));
	}

	/**
	 * listOperations读取总记录数
	 * @param key 键
	 * @return long 对应key的总记录数
	 */
	public Long listSize(String key) {
		return listOperations.size(key);
	}

	/**
	 * 删除缓存
	 * @param key 键
	 */
	public void delete(String key) {
		redisTemplate.delete(key);
	}

	/**
	 * 批量删除缓存
	 * @param pattern 前缀匹配,列如test_*
	 */
	public Long deletePattern(String pattern) {
		Set<String> keys = redisTemplate.keys(pattern);
		if (keys.size() > 0)
			return redisTemplate.delete(keys);
		return 0l;
	}

	/**
	 * hashOperations删除缓存
	 * @param key 键
	 * @param hashKeys key中的hashkey
	 */
	public void delete(String key, Object... hashKeys) {
		hashOperations.delete(key, hashKeys);
	}

	/**
	 * listOperations删除列表中重复的值
	 * @param key 键
	 * @param count 删除数量
	 * @param value 值
	 */
	public Long delete(String key, int count, Object value) {
		return listOperations.remove(key, count, value);
	}

	/**
	 * 判断缓存中是否有对应的value
	 * @param key 键
	 * @return true存在 false不存在
	 */
	public boolean exists(String key) {
		return redisTemplate.hasKey(key);
	}

	/**
	 * hashOperations判断缓存中是否有对应的value
	 * @param key 键
	 * @param hashKey key中的hashkey
	 * @return true存在 false不存在
	 */
	public boolean exists(String key, Object hashKey) throws Exception {
		return hashOperations.hasKey(key, hashKey);
	}

	/**
	 * valueOperations递增
	 * @param key 键
	 * @param delta 每次增加多少(大于0)
	 * @return long 增加的结果
	 */
	public Long increment(String key, long delta) {
		return valueOperations.increment(key, delta);
	}
	/**
	 * 批量删除key
	 *
	 * @param pattern
	 */
	public void removePattern(final String pattern) {
		Set<String> keys = redisTemplate.keys(pattern);
		if (keys.size() > 0)
			redisTemplate.delete(keys);
	}
	
	/**
	 * valueOperations写入缓存设置过期时间
	 * @param key
	 * @param value
	 * @param date
	 */
	public void setAndExpireAt(String key,Object value,Date date) {
		//valueOperations.set(key, toJson(value));
		redisTemplate.opsForValue().set(key, value);
		redisTemplate.expireAt(key, date);
	}
	
	/**
	 * Object转成JSON数据
	 */
	private String toJson(Object object) {
		if (object instanceof Integer || object instanceof Long || object instanceof Float || object instanceof Double || object instanceof Boolean || object instanceof String) {
			return String.valueOf(object);
		}
		return FastJsons.convertObjectToJSON(object);
	}

	/**
	 * JSON数据转成Object
	 */
	private <T> T fromJson(String json, Class<T> clazz) {
		return FastJsons.convertJSONToObject(json, clazz);
	}

}
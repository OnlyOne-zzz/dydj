package com.bestfeng.dydj.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: RedisUtil
 * @Description: redis管理工具
 * @author: hayder
 * @date: 2017年10月10日 下午3:08:07
 */
@Component
@Slf4j
public class RedisUtil {

	@Resource
	private StringRedisTemplate stringRedisTemplate;

	/**
	 * 删除对应的value
	 * 
	 * @param key
	 */
	public void remove(final String key) throws Exception {
		if (exists(key)) {
			stringRedisTemplate.delete(key);
		}
	}
	
	/**
	 * 批量删除key
	 * 
	 * @param pattern
	 */
	public void removePattern(final String pattern) {
		Set<String> keys = stringRedisTemplate.keys(pattern);
		if (keys.size() > 0)
			stringRedisTemplate.delete(keys);
	}

	/**
	 * 删除对应的value
	 * 
	 * @param key
	 * @param fieldKey
	 */
	public void remove(final String key, final String fieldKey)
			throws Exception {
		if (exists(key, fieldKey)) {
			stringRedisTemplate.opsForHash().delete(key, fieldKey);
		}
	}

	/**
	 * 判断缓存中是否有对应的value
	 * 
	 * @param key
	 * @return
	 */
	public boolean exists(final String key) throws Exception {
		return stringRedisTemplate.hasKey(key);
	}

	/**
	 * 判断缓存中是否有对应的value
	 * 
	 * @param key
	 *            hashkey
	 * @param fieldKey
	 *            字段id
	 * @return
	 */
	public boolean exists(final String key, final String fieldKey)
			throws Exception {
		return stringRedisTemplate.opsForHash().hasKey(key, fieldKey);
	}

	/**
	 * 读取缓存
	 * 
	 * @param key
	 * @return
	 */
	public String get(final String key) {
		String result = null;
		try {
			ValueOperations<String, String> operations = stringRedisTemplate
					.opsForValue();
			result = operations.get(key);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return result;
	}

	/**
	 * 写入缓存
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(final String key, String value) {
		boolean result = false;
		try {
			ValueOperations<String, String> operations = stringRedisTemplate
					.opsForValue();
			operations.set(key, value);
			result = true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return result;
	}

	/**
	 * 写入缓存
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(final String key, String value, Long expireTime) {
		boolean result = false;
		try {
			ValueOperations<String, String> operations = stringRedisTemplate
					.opsForValue();
			operations.set(key, value);
			stringRedisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
			result = true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return result;
	}

	/**
	 * 读取缓存
	 * 
	 * @param hash
	 *            , key
	 * @return
	 */
	public String getHash(String key, String fieldKey) {
		String result = null;
		try {
			result = (String) stringRedisTemplate.opsForHash().get(key,
					fieldKey);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return result;
	}

	/**
	 * 写入缓存 为指定key中的fieldKey赋值为value，相当于hash命令HSET
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean setHash(String key, String fieldKey, String value,
			Long expireTime) {
		boolean result = false;
		try {
			if (!this.exists(key)) {
				stringRedisTemplate.opsForHash().put(key, fieldKey, value);
				stringRedisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
			} else {
				stringRedisTemplate.opsForHash().put(key, fieldKey, value);
			}
			result = true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return result;
	}

	/**
	 * 根据key自增
	 * 
	 * @param key
	 * @param delta
	 * @return Long
	 */
	public Long increment(final String key, final long delta) {
		Long result = null;
		try {
			ValueOperations<String, String> operations = stringRedisTemplate
					.opsForValue();
			result = operations.increment(key, delta);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result = null;
		}
		return result;
	}

	public boolean expireTime(final String key, Long expireTime) {
		boolean bool = false;
		try {
			Boolean b = stringRedisTemplate.expire(key, expireTime,
					TimeUnit.SECONDS);
			if (null != b && b.booleanValue()) {
				bool = b.booleanValue();
			} else {
				bool = false;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			bool = false;
		}
		return bool;
	}

	public StringRedisTemplate getRedisTemplate() {
		return stringRedisTemplate;
	}

	/* ==============================List 对象处理=========================== */
	public List<String> getPageList(final String key, int page, int pageSize) {
		List<String> result = null;
		try {
			result = stringRedisTemplate.opsForList().range(key,
					(page - 1) * pageSize, page * pageSize - 1);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return result;
	}

	public Long llen(final String key) {
		Long l = null;
		try {
			l = stringRedisTemplate.opsForList().size(key);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return l;
	}

	public Long remove(final String key, int count, String value) {
		Long l = null;
		try {
			l = stringRedisTemplate.opsForList().remove(key, count, value);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return l;
	}

	public Long lpush(final String key, String value) {
		Long l = null;
		try {
			l = stringRedisTemplate.opsForList().leftPush(key, value);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return l;
	}

	public Long lpushx(final String key, String value) {
		Long l = null;
		try {
			l = stringRedisTemplate.opsForList().leftPushIfPresent(key, value);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return l;
	}

	public boolean Ltrim(final String key, int start, int end) {
		boolean bool = false;
		try {
			stringRedisTemplate.opsForList().trim(key, start, end);
			bool = true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return bool;
	}

	/* ==================================================================== */

	/* ==============================Map 对象处理=========================== */
	public boolean hset(String key, String field, String value) {
		boolean bool = false;
		try {
			stringRedisTemplate.opsForHash().put(key, field, value);
			bool = true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return bool;
	}

	public boolean hdel(String key, String field) {
		boolean bool = false;
		try {
			stringRedisTemplate.opsForHash().delete(key, field);
			bool = true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return bool;
	}

	public String hget(String key, String field) {
		String v = null;
		try {
			HashOperations<String, String, String> ops = stringRedisTemplate
					.opsForHash();
			v = ops.get(key, field);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return v;
	}

	public List<String> hvals(String key) {
		List<String> list = null;
		try {
			HashOperations<String, String, String> ops = stringRedisTemplate
					.opsForHash();
			list = ops.values(key);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}

	/* ==================================================================== */

	/* ==============================sorted set=========================== */
	public boolean zadd(String key, double score, String value) {
		boolean bool = false;
		try {
			Boolean b = stringRedisTemplate.opsForZSet().add(key, value, score);
			if (null != b) {
				bool = b;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return bool;
	}

	public Set<TypedTuple<String>> reverseRangeWithScores(String key, int page,
                                                          int pageSize) {
		Set<TypedTuple<String>> set = new HashSet<TypedTuple<String>>();
		try {
			set = stringRedisTemplate.opsForZSet().reverseRangeWithScores(key,
					(page - 1) * pageSize, page * pageSize - 1);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return set;
	}

	public Long zSetSize(String key) {
		Long count = null;
		try {
			count = stringRedisTemplate.opsForZSet().size(key);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return count;
	}

	public long removeRange(String key, long start, long end) {
		Long count = null;
		try {
			count = stringRedisTemplate.opsForZSet().removeRange(key, start,
					end);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return count;
	}
	
	public long zSetRemove(String key, String values) {
		Long count = null;
		try {
			count = stringRedisTemplate.opsForZSet().remove(key, values);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return count;
	}
	/* ==================================================================== */
}

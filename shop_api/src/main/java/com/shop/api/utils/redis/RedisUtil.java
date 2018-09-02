package com.shop.api.utils.redis;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

/**
 * Redis操作的入口
 */
@Component("redisUtil")
@SuppressWarnings("all")
public class RedisUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(RedisUtil.class);

	@Resource
	private StringRedisTemplate stringRedisTemplate;

	/**
	 * 设置字符串
	 */
	public void set(String key, String value) {
		ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
		valueOperations.set(key, value);
	}

	/**
	 * 设置字符串(含过期时间)
	 */
	public void set(String key, String value, long timeout) {
		ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
		valueOperations.set(key, value, timeout, TimeUnit.SECONDS);
	}

	/**
	 * 设置对象
	 */
	public <T> void set(String key, T t) {
		ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
		String value = JSONObject.toJSONString(t);
		valueOperations.set(key, value);
	}

	/**
	 * 设置对象(含过期时间)
	 */
	public <T> void set(String key, T t, long timeout) {
		ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
		String value = JSONObject.toJSONString(t);
		valueOperations.set(key, value, timeout, TimeUnit.SECONDS);
	}

	/**
	 * 获取字符串
	 */
	public String get(String key) {
		ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
		return valueOperations.get(key);
	}

	/**
	 * 获取对象
	 */
	public <T> T get(String key, Class<T> classz) {
		ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
		String string = valueOperations.get(key);
		if (null == string)
			return null;
		return JSONObject.parseObject(string, classz);
	}

	/**
	 * 获取List对象
	 */
	public <T> List<T> getListObject(String key, Class<T> classz) {
		ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
		String string = valueOperations.get(key);
		if (null == string)
			return null;
		return JSONObject.parseArray(string, classz);
	}

	/**
	 * 根据key列表删除单个对象
	 */
	public void delete(String key) {
		stringRedisTemplate.delete(key);
	}

	/**
	 * 根据key列表删除多个对象
	 */
	public void deleteAll(List<String> keys) {
		stringRedisTemplate.delete(keys);
	}

	/**
	 * 递增 返回加1之前的值
	 */
	public Long incr(String key, long liveTime) {
		RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, stringRedisTemplate.getConnectionFactory());
		long L = entityIdCounter.get();
		Long increment = entityIdCounter.getAndIncrement();
		if ((null == increment || increment.longValue() == 0) && liveTime > 0) {// 初始设置过期时间
			entityIdCounter.expire(liveTime, TimeUnit.SECONDS);
		}
		return L;
	}

}

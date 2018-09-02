package com.shop.api.utils.json;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 */
public class JsonUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

	private JsonUtil() {
	}

	public static <T> List<T> parseArray(String s, Class<T> clz) {
		List<T> list = null;
		try {
			list = JSON.parseArray(s, clz);
		} catch (Exception ex) {
			LOGGER.error("", ex);
		}
		if (list == null) {
			list = new ArrayList<>(0);
		}
		return list;
	}

	public static <T> T parseObject(String json, Class<T> clazz) {
		return JSON.parseObject(json, clazz);
	}

	public static String toJSONString(Object object) {
		return JSON.toJSONString(object);
	}
}

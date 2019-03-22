package com.adachina.common.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * properties加载工具，文件加载时候首先尝试从jar包加载，如果jar包没有，则尝试从外部加载<br>
 * 找不到文件时候，默认不做任何操作<br>
 * 
 * @author fanglin.xiang
 * 
 */
public class Properties {
	private static final Logger logger = LoggerFactory.getLogger(Properties.class);
	private final Map<String, String> properties = new ConcurrentHashMap<String, String>();
	public static Properties get() {
		return new Properties();
	}
	
	public Properties load(String filePath) {
		boolean loaded = false;
		try (InputStream in = Properties.class.getResourceAsStream(filePath)) {
			if (in != null) {
				java.util.Properties p = new java.util.Properties();
				p.load(in);
				put(p);
				loaded = true;
			}
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			loaded = true;
		}
		if(!loaded) {
			logger.info("not found {} in jar,try load from external", filePath);
			File file = new File(filePath);
			if (!file.exists()) {
				logger.warn("not found {} from external", filePath);
				return this;
			}
			try (InputStream in = new FileInputStream(file)) {
				java.util.Properties p = new java.util.Properties();
				p.load(in);
				put(p);
			}catch(Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return this;
	}

	public Properties put(String key, String value) {
		properties.put(key, value);
		return this;
	}

	public Properties put(Map<? extends Object, ? extends Object> map) {
		for (Entry<? extends Object, ? extends Object> entry : map.entrySet()) {
			Object key = entry.getKey();
			Object value = entry.getValue();
			if (key == null) {
				continue;
			}
			properties.put(key.toString().trim(), value == null ? null : value
					.toString().trim());
		}
		return this;
	}

	public String get(String key) {
		return properties.get(key);
	}

	public Integer getInt(String key) {
		String value = get(key);
		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
			logger.info("parser exception", e);
		}
		return null;
	}

	public Map<String, String> getAll() {
		return Collections.unmodifiableMap(properties);
	}

	public Integer getInt(String key, Integer defaultValue) {
		Integer value = getInt(key);
		return value == null ? defaultValue : value;
	}
}

/**
 * @Title: PropConfig.java
 * @Description:
 * @version: V1.0
 */
package com.shop.api.utils.config;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 配置文件
 */
public class PropConfig {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(PropConfig.class);

    private static Properties prop = new Properties();
    private static Properties propDb = new Properties();
    private static Properties propKey = new Properties();
    private static Properties propRal = new Properties();
    static {
		Object[] confs = { "/properties/config.properties", prop, "/properties/jdbc.properties", propDb, "/properties/key.properties", propKey,
				 propRal};
		for (int i = 0; i < confs.length; i += 2) {
			try (InputStream in = PropConfig.class.getResourceAsStream((String) confs[i])) {
				((Properties) confs[i + 1]).load(new InputStreamReader(in, "UTF-8"));
			} catch (Exception e) {
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error(e.getMessage());
				}
			}
		}
    }

    /**
     * Gets the value by key.
     *
     * @param key
     *            the key
     * @return the value by key
     */
    public static String getValue(String key) {
        return prop.getProperty(key);
    }

    public static String getDbValue(String key) {
        return propDb.getProperty(key);
    }
    
    public static String getTableKey(String tableName){
    	return propKey.getProperty(tableName);
    }
    
    public static String getDisValue(String ral){
    	return StringUtils.trim(propRal.getProperty(ral));
    }
}

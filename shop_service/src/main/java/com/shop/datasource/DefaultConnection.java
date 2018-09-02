package com.shop.datasource;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.LoggerFactory;

import com.shop.api.utils.config.PropConfig;
import com.zaxxer.hikari.HikariConfig;

public class DefaultConnection implements IConnection {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DefaultConnection.class);
    private static final Map<String, String> mapSchema = new HashMap<>();

    private String key = "";

    @Override
    public HikariConfig createDataSouce() throws Exception {
    	LOGGER.info("加载数据源配置信息");
        mapSchema.put(key, PropConfig.getDbValue(key + ".dataSource.offline.schema"));

        String dbIp = PropConfig.getDbValue(key + ".dataSource");
        String user = PropConfig.getDbValue(key + ".dataSource.user");
        String password = PropConfig.getDbValue(key + ".dataSource.password");
        // 下面的部分的配置是公用的
        String connectionTestQuery = PropConfig.getDbValue("hikari.dataSource.connectionTestQuery");
        int connectionTimeout = Integer.parseInt(PropConfig.getDbValue("hikari.dataSource.connectionTimeout"));
        Long idleTimeout = Long.parseLong(PropConfig.getDbValue("hikari.dataSource.idleTimeout"));
        Long maxLifetime = Long.parseLong(PropConfig.getDbValue("hikari.dataSource.maxLifetime"));
        int maximumPoolSize = Integer.parseInt(PropConfig.getDbValue("hikari.dataSource.maximumPoolSize"));
        int minimumIdle = Integer.parseInt(PropConfig.getDbValue("hikari.dataSource.minimumIdle"));
        String driverClassName = PropConfig.getDbValue("hikari.dataSource.driverClassName");
        HikariConfig config = new HikariConfig();

        // String realUrl = urlBuilder(dbIp);
        config.setJdbcUrl(urlBuilder(dbIp, mapSchema.get(key)));
        config.setUsername(user);
        config.setPassword(password);
        config.setRegisterMbeans(true);
        config.setConnectionTestQuery(connectionTestQuery);
        config.setConnectionTimeout(connectionTimeout);
        config.setIdleTimeout(idleTimeout);
        config.setMaxLifetime(maxLifetime);
        config.setMaximumPoolSize(maximumPoolSize);
        config.setMinimumIdle(minimumIdle);
        config.setDriverClassName(driverClassName);
        return config;
    
    }


    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }

    private String urlBuilder(String partUrl, String schema) {
        String s = PropConfig.getDbValue("datasource.url.build.str");
        return "jdbc:mysql://" + partUrl + "/" + schema + s;
    }

    public static String getSchema(String key) {
        return mapSchema.get(key);
    }
}

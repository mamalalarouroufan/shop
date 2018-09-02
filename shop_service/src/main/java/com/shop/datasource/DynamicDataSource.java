package com.shop.datasource;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.jdbc.datasource.lookup.DataSourceLookup;

import com.shop.api.utils.config.PropConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * 动态数据源
 */
public class DynamicDataSource extends AbstractRoutingDataSource implements ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicDataSource.class);

    private static ApplicationContext _applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        _applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return _applicationContext;
    }

    public static String getDefaultSchema() {
        return DefaultConnection.getSchema("hikari");
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource#
     * determineCurrentLookupKey()
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DatabaseContextHolder.getCustomerType();
    }

    @Override
    public void afterPropertiesSet() {
        LOGGER.info("初始化多数据源");
        try {
            initailizeMutiDataSource();
        } catch (Exception e) {
            LOGGER.error("---",e);
            System.exit(0);
        }
        LOGGER.info("多数据源加入spring容器中成功!");

        super.afterPropertiesSet();
    }

    private void initailizeMutiDataSource() throws Exception {
        DefaultListableBeanFactory acf = (DefaultListableBeanFactory) _applicationContext
                .getAutowireCapableBeanFactory();

        LOGGER.debug(
                "--------------------------initailizeMutiDataSource start-------------------------------------------");

        // 票务系统数据库
        DefaultConnection hikari = new DefaultConnection();
        String key = PropConfig.getValue("JDBC_KEY");
        if (StringUtils.isBlank(key)){
        	key = "hikari";
        }
        hikari.setKey(key);
        HikariConfig hikariConfig = hikari.createDataSouce();
        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
        acf.registerSingleton("hikariDataSource", hikariDataSource);

        LOGGER.debug("hikariDataSourcUrl：" + hikariDataSource.getJdbcUrl());
        LOGGER.debug("hikariDataSourcUsernamel：" + hikariDataSource.getUsername());
        LOGGER.debug("hikariDataSourcPassword：" + hikariDataSource.getPassword());


        setDefaultTargetDataSource(hikariDataSource);
        Map<Object, Object> dsMap = new HashMap<>();
        dsMap.put("hikariDataSource", hikariDataSource);
        // 这里加入多数据源
        setTargetDataSources(dsMap);

        LOGGER.debug(
                "---------------------------initailizeMutiDataSource end--------------------------------------------");
    }

    @Override
    public void setDataSourceLookup(DataSourceLookup dataSourceLookup) {
        super.setDataSourceLookup(dataSourceLookup);
    }

    @Override
    public void setDefaultTargetDataSource(Object defaultTargetDataSource) {
        super.setDefaultTargetDataSource(defaultTargetDataSource);
    }
}

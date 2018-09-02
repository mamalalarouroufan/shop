package com.shop.datasource;

import com.zaxxer.hikari.HikariConfig;

public interface IConnection {

    void setKey(String key);

    String getKey();

    // 获得一个数据连接
    HikariConfig createDataSouce() throws Exception;
}

package com.java4all.scalog.store.source;

import static com.java4all.scalog.properties.Constants.DEFAULT_MYSQL_DRIVER_CLASS_NAME;
import static com.java4all.scalog.properties.Constants.DEFAULT_MYSQL_URL;
import static com.java4all.scalog.properties.Constants.DEFAULT_PASSWORD;
import static com.java4all.scalog.properties.Constants.DEFAULT_USERNAME;
import static com.java4all.scalog.properties.Constants.DELIMITER;
import static com.java4all.scalog.properties.Constants.MYSQL_DRIVER_CLASS_NAME;
import static com.java4all.scalog.properties.Constants.PASSWORD;
import static com.java4all.scalog.properties.Constants.SPRING_DATASOURCE;
import static com.java4all.scalog.properties.Constants.URL;
import static com.java4all.scalog.properties.Constants.USERNAME;

import com.alibaba.druid.pool.DruidDataSource;
import com.java4all.scalog.annotation.LoadLevel;
import com.java4all.scalog.properties.springcloud.SpringCloudConfig;

/**
 * @author wangzhongxiang
 */
@LoadLevel(name = "mysql")
public class DruidDataSourceGenerator implements SourceGenerator{


    @Override
    public Object generateSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(SpringCloudConfig.getConfig(SPRING_DATASOURCE+DELIMITER+URL,DEFAULT_MYSQL_URL));
        dataSource.setUsername(SpringCloudConfig.getConfig(SPRING_DATASOURCE+DELIMITER+USERNAME,DEFAULT_USERNAME));
        dataSource.setPassword(SpringCloudConfig.getConfig(SPRING_DATASOURCE+DELIMITER+PASSWORD,DEFAULT_PASSWORD));
        dataSource.setDriverClassName(SpringCloudConfig.getConfig(SPRING_DATASOURCE+DELIMITER+MYSQL_DRIVER_CLASS_NAME,DEFAULT_MYSQL_DRIVER_CLASS_NAME));
        return dataSource;
    }
}


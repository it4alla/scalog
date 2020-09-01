package com.java4all.scalog.store.source;

import static com.java4all.scalog.configuration.Constants.DEFAULT_MYSQL_DRIVER_CLASS_NAME;
import static com.java4all.scalog.configuration.Constants.DEFAULT_MYSQL_URL;
import static com.java4all.scalog.configuration.Constants.DEFAULT_PASSWORD;
import static com.java4all.scalog.configuration.Constants.DEFAULT_USERNAME;
import static com.java4all.scalog.configuration.Constants.DELIMITER;
import static com.java4all.scalog.configuration.Constants.DRIVER_CLASS_NAME2;
import static com.java4all.scalog.configuration.Constants.PASSWORD;
import static com.java4all.scalog.configuration.Constants.SCALOG;
import static com.java4all.scalog.configuration.Constants.URL;
import static com.java4all.scalog.configuration.Constants.USERNAME;

import com.alibaba.druid.pool.DruidDataSource;
import com.java4all.scalog.annotation.LoadLevel;
import com.java4all.scalog.configuration.springcloud.SpringCloudConfig;

/**
 * @author wangzhongxiang
 */
@LoadLevel(name = "mysql")
public class MysqlDataSourceGenerator implements SourceGenerator{

    /**
     * generate source
     */
    @SuppressWarnings("all")
    @Override
    public Object generateSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(SpringCloudConfig.getConfig(SCALOG+DELIMITER+URL,DEFAULT_MYSQL_URL));
        dataSource.setUsername(SpringCloudConfig.getConfig(SCALOG+DELIMITER+USERNAME,DEFAULT_USERNAME));
        dataSource.setPassword(SpringCloudConfig.getConfig(SCALOG+DELIMITER+PASSWORD,DEFAULT_PASSWORD));
        dataSource.setDriverClassName(SpringCloudConfig.getConfig(SCALOG+DELIMITER+DRIVER_CLASS_NAME2,DEFAULT_MYSQL_DRIVER_CLASS_NAME));
        return dataSource;
    }
}


package com.java4all.scalog.store.source;

import com.alibaba.druid.pool.DruidDataSource;
import com.java4all.scalog.annotation.LoadLevel;
import com.java4all.scalog.properties.MysqlProperties;

/**
 * @author wangzhongxiang
 */
@LoadLevel(name = "mysql")
public class DruidDataSourceGenerator implements SourceGenerator{

    //TODO 这个需要给处理成单例模式，其他地方好取数据
//    protected MysqlProperties mysqlProperties;

    @Override
    public Object generateSource() {
        DruidDataSource dataSource = new DruidDataSource();
//        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
//        dataSource.setUrl(mysqlProperties.getUrl());
//        dataSource.setUsername(mysqlProperties.getUsername());
//        dataSource.setPassword(mysqlProperties.getPassword());
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://192.168.173.95:3306/credit_server?useUnicode=true&characterEncoding=UTF8&allowMultiQueries=true");
        dataSource.setUsername("root");
        dataSource.setPassword("runlion@123");
        return dataSource;
    }
}

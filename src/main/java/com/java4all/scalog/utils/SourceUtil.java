package com.java4all.scalog.utils;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author wangzhongxiang
 * @date 2020年06月17日 15:27:27
 */
@Component
public class SourceUtil implements ApplicationContextAware {

    @Autowired
    private static DataSource dataSource;
    private static ApplicationContext applicationContext;

    public static Connection getConnection() throws SQLException {
        dataSource = applicationContext.getBean(DataSource.class);
        Connection connection = dataSource.getConnection();
        return connection;
    }

    public static void close(AutoCloseable...sources){
        if(sources != null && sources.length > 0){
            for (AutoCloseable source:sources){
                if(source != null){
                    try {
                        source.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SourceUtil.applicationContext = applicationContext;
    }
}

package com.java4all.scalog.utils;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wangzhongxiang
 * @date 2020年06月17日 15:27:27
 */
@Component
public class SourceUtil {

    @Autowired
    private static DataSource dataSource;

    public static Connection getConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        return connection;
    }

}

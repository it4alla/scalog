package com.java4all.scalog.configuration;

/**
 * @author wangzhongxiang
 */
public class Constants {

    public static final String SPRING = "spring";
    public static final String DATASOURCE = "datasource";
    public static final String SPRING_DATASOURCE = "spring.datasource";
    public static final String DELIMITER = ".";

    public static final String URL = "url";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String DRIVER_CLASS_NAME = "driver-class-name";

    public static final String DEFAULT_MYSQL_URL = "127.0.0.1:3306";
    public static final String DEFAULT_USERNAME = "root";
    public static final String DEFAULT_PASSWORD = "root";

    public static final String DEFAULT_MYSQL_DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
    public static final String DEFAULT_POSTGRESQL_DRIVER_CLASS_NAME = "org.postgresql.Driver";

    public static final String LOG_TABLE = "log_info";

    public static final String SPRING_APPLICATION_CONTEXT = "springApplicationContext";
    public static final String BEAN_NAME_SPRING_APPLICATION_CONTEXT_PROVIDER = "springApplicationContextProvider";

    /** mongodb配置 */
    public static final String DEFAULT_MONGO_URL = "210.22.22.150";
    public static final Integer DEFAULT_MONGO_PORT = 2091;
    public static final String DEFAULT_MONGO_USERNAME = "scalog";
    public static final String DEFAULT_MONGO_PASSWORD = "scalog_NB_$713!";
    public static final String DEFAULT_MONGO_DB = "scalog";
    public static final String DEFAULT_MONGO_DOCUMENT = "mongoLogInfo";
}

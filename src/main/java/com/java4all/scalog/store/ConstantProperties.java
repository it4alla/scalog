package com.java4all.scalog.store;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author wangzhongxiang
 */
@ComponentScan
public class ConstantProperties {

    @Value("${com.java4all.scalog.dbType}")
    private String dbType;

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }
}

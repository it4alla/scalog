package com.java4all.scalog.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author wangzhongxiang
 */
@Component
public class ScalogProperties {

    @Value("${scalog.db.type}")
    private String dbType;

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }
}

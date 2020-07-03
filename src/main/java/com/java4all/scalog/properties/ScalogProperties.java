package com.java4all.scalog.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author wangzhongxiang
 */
@Component
public class ScalogProperties {

    /**
     * the scalog db type:
     * mysql , oracle , elasticsearch , mongodb
     */
    @Value("${scalog.db.type}")
    private String dbType;

    /**
     * the scalog level:
     * no , specified , all
     *
     * no: will record nothing
     * specified: will record the controller's interface which has {@link com.java4all.scalog.annotation.LogInfo} annotation
     * all: will record all of the controller's interface
     *
     */
    @Value(("${scalog.level}"))
    private String level;

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}

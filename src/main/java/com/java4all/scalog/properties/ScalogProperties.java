package com.java4all.scalog.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wangzhongxiang
 */
@Component
@ConfigurationProperties(prefix = "scalog")
public class ScalogProperties {

    /**
     * the scalog db type:
     * mysql , oracle , elasticsearch , mongodb
     */
    private String db;

    /**
     * the scalog level:
     * no , specified , all
     *
     * no: will record nothing
     * specified: will record the controller's interface which has {@link com.java4all.scalog.annotation.LogInfo} annotation
     * all: will record all of the controller's interface
     *
     */
    private String level;

    /**company name*/
    private String companyName;

    /**project name*/
    private String projectName;

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}

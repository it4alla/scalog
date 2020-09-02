package com.java4all.scalog.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wangzhongxiang
 */
@Component
@ConfigurationProperties(prefix = "scalog")
public class ScalogProperties {

    //TODO need to support spi

    /**
     * the scalog db type:
     * mysql , oracle , elasticsearch , mongodb
     */
    private String db;

    private String url;
    private String username;
    private String password;
    private String driverClassName;
    private String type;

    /**
     * the scalog level:
     * nothing , specified , all
     *
     * nothing: will record nothing
     * specified: will record the controller's interface which has {@link com.java4all.scalog.annotation.LogInfo} annotation
     * all: will record all of the controller's interface
     *
     */
    private String level;

    /**country name*/
    private String countryName;

    /**group name*/
    private String groupName;

    /**organization name*/
    private String organizationName;

    /**company name*/
    private String companyName;

    /**project name*/
    private String projectName;

    /**
     * Store results or not
     */
    private Boolean needResult;

    public Boolean getScaEnable() {
        return scaEnable;
    }

    public void setScaEnable(Boolean scaEnable) {
        this.scaEnable = scaEnable;
    }

    /**
     * global scalog enable (true or false)
     */
    private Boolean scaEnable;

    public Boolean getNeedResult() {
        return needResult;
    }

    public void setNeedResult(Boolean needResult) {
        this.needResult = needResult;
    }

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

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

package com.java4all.scalog.store;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * Description:mongodb日志存储对象
 *
 * @author dsl
 * @date 2020/7/13 18:01
 */
@Document(collection = "mongoLogInfo")
public class MongoLogInfo implements Serializable {
    /**id*/
    @Id
    private String id;

    /**国家*/
    @Field("country_name")
    private String countryName;

    /**集团*/
    @Field("group_name")
    private String groupName;

    /**组织*/
    @Field("organization_name")
    private String organizationName;

    /**公司名称*/
    @Field("company_name")
    private String companyName;

    /**项目名称*/
    @Field("project_name")
    private String projectName;

    /**模块名称*/
    @Field("module_name")
    private String moduleName;

    /**功能名称*/
    @Field("function_name")
    private String functionName;

    /**接口所在类名称*/
    @Field("class_name")
    private String className;

    /**方法名称*/
    @Field("method_name")
    @Indexed
    private String methodName;

    /**方法类型*/
    @Field("method_type")
    private String methodType;

    /**接口url*/
    @Field("url")
    private String url;

    /**接口入参*/
    @Field("request_params")
    private String requestParams;

    /**接口返回值*/
    @Field("result")
    private String result;

    /**备注*/
    @Field("remark")
    private String remark;

    /**接口耗时*/
    @Field("cost")
    private Long cost;

    /**用户ip*/
    @Field("ip")
    private String ip;

    /**用户id*/
    @Field("user_id")
    private String userId;

    /**用户名称*/
    @Field("user_name")
    private String userName;

    /**客户端类型*/
    @Field("client_type")
    private String clientType;

    /**客户端信息*/
    @Field("user_agent")
    private String userAgent;

    /**此条操作状态：0 正常  1 异常'*/
    @Field("log_type")
    private Integer logType;

    /**操作开始时间*/
    @Field("gmt_start")
    private String gmtStart;

    /**操作结束时间*/
    @Field("gmt_end")
    private String gmtEnd;

    /**创建时间*/
    @Field("gmt_create")
    private String gmtCreate;

    /**修改时间*/
    @Field("gmt_modified")
    private String gmtModified;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodType() {
        return methodType;
    }

    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(String requestParams) {
        this.requestParams = requestParams;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Integer getLogType() {
        return logType;
    }

    public void setLogType(Integer logType) {
        this.logType = logType;
    }

    public String getGmtStart() {
        return gmtStart;
    }

    public void setGmtStart(String gmtStart) {
        this.gmtStart = gmtStart;
    }

    public String getGmtEnd() {
        return gmtEnd;
    }

    public void setGmtEnd(String gmtEnd) {
        this.gmtEnd = gmtEnd;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(String gmtModified) {
        this.gmtModified = gmtModified;
    }
}

package com.java4all.scalog.store;

/**
 * @author wangzhongxiang
 */
public class LogInfoDto {
    /**id*/
    private String id;
    /**公司名称*/
    private String companyName;
    /**项目名称*/
    private String projectName;
    /**模块名称*/
    private String moduleName;
    /**功能名称*/
    private String functionName;
    /**接口所在类名称*/
    private String className;
    /**方法名称*/
    private String methodName;
    /**方法类型*/
    private String methodType;
    /**接口url*/
    private String url;
    /**接口入参*/
    private String requestParams;
    /**接口返回值*/
    private String result;
    /**备注*/
    private String remark;
    /**接口耗时*/
    private Long cost;
    /**用户ip*/
    private String ip;
    /**用户id*/
    private String userId;
    /**用户名称*/
    private String userName;
    /**此条操作状态：0 正常  1 异常'*/
    private Integer logType;
    /**操作开始时间*/
    private String gmtStart;
    /**操作结束时间*/
    private String gmtEnd;
    /**创建时间*/
    private String gmtCreate;
    /**修改时间*/
    private String gmtModified;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

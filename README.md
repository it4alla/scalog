# scalog
### 1.背景
记录项目中controller层接口的相关基础信息，方面后续做数据统计分析；
目前项目中大多数方案是用切面或者拦截器配合注解来做，但是会存在一个问题：每个web项目都要单独实现或者copy一份此逻辑。
scalog的实现也是基于切面和注解，但是可以打包为jar的形式，项目中直接引入pom依赖即可。

### 2.使用
如果公司有私有仓库，可修改pom.xml中的仓库地址，推送到私有仓库。
#### 2.1创建记录表
在项目数据库中创建数据库表，见最下方脚本。
目前仅支持mysql数据库，后续会拓展。
#### 2.2项目中引入pom依赖
```java
		<dependency>
			<groupId>com.java4all</groupId>
			<artifactId>scalog</artifactId>
			<version>1.0.0-RELEASE</version>
		</dependency>
```
#### 2.3配置扫描
项目启动类中，配置组件扫描路径。
例如，本身项目的扫描路径为：
```java
@ComponentScan("com.runlion.fsp")
```
现在可以新增scalog的扫描
```java
@ComponentScan({"com.runlion.fsp","com.java4all"})
```

#### 2.5配置文件
提供配置项如下：
- scalog.db.type 日志存储的数据库，支持 mysql , (oracle , elasticsearch , mongodb待实现)
- scalog.level 日志的记录级别，支持 no , specified , all

no：关闭日志记录，切面不记录任何日志
specified：仅添加LogInfo注解的接口才会记录
all：记录所有的接口

#### 2.5使用
上述步骤完成后，接口请求记录已经可以正常记录。
但是，以下几个字段，是可选项，切面无法拿到，默认为空：
```java
  `company_name` char(200) DEFAULT NULL COMMENT '公司名称',
  `project_name` char(200) DEFAULT NULL COMMENT '项目名称',
  `module_name` char(200) DEFAULT NULL COMMENT '模块名称',
  `function_name` char(200) DEFAULT NULL COMMENT '功能名称',
  `remark` char(200) DEFAULT NULL COMMENT '备注',
```
由于切面无法拿到，但是项目中需要定制化记录，所以提供了注解来记录，例如：
```java
    /**
     * 返利单价列表
     * web-授信管理-经销商返利单价列表
     * @param pageSize
     * @param pageNum
     * @return
     */
    @LogInfo(companyName = "谷歌杭州分公司",projectName = "金融项目",moduleName = "授信模块",functionName = "返利单价列表",remark = "获取返利单价列表")
    @GetMapping(value = "list")
    public ObjectRestResponse list(Integer pageSize,Integer pageNum){
        FspPageInfo fspPageInfo = rebateServiceImpl.list(pageSize,pageNum);
        return new ObjectRestResponse().data(fspPageInfo);
    }
```
### 3.用户
由于不同项目中用户获取方式不一样，所以，userId,userName的处理，可能需要自行处理一下。


#### 数据库表结构：
```java
CREATE TABLE `log_info` (
  `id` char(50) NOT NULL,
  `company_name` char(200) DEFAULT NULL COMMENT '公司名称',
  `project_name` char(200) DEFAULT NULL COMMENT '项目名称',
  `module_name` char(200) DEFAULT NULL COMMENT '模块名称',
  `function_name` char(200) DEFAULT NULL COMMENT '功能名称',
  `class_name` char(200) DEFAULT NULL COMMENT '接口所在类名称',
  `method_name` char(200) DEFAULT NULL COMMENT '方法名称',
  `method_type` char(200) DEFAULT NULL COMMENT '方法类型',
  `url` varchar(2000) DEFAULT NULL COMMENT '接口url',
  `request_params` varchar(2000) DEFAULT NULL COMMENT '接口入参',
  `result` text COMMENT '接口返回值',
  `remark` char(200) DEFAULT NULL COMMENT '备注',
  `cost` bigint(50) DEFAULT NULL COMMENT '接口耗时',
  `ip` char(200) DEFAULT NULL COMMENT '用户ip',
  `user_id` char(200) DEFAULT NULL COMMENT '用户id',
  `user_name` char(200) DEFAULT NULL COMMENT '用户名称',
  `client_type` varchar(200) DEFAULT NULL COMMENT '客户端类型',
  `user_agent` varchar(200) DEFAULT NULL COMMENT '客户端信息',
  `log_type` tinyint(1) DEFAULT NULL COMMENT '此条操作状态：0 正常  1 异常',
  `gmt_start` datetime DEFAULT NULL COMMENT '操作开始时间',
  `gmt_end` datetime DEFAULT NULL COMMENT '操作结束时间',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='接口日志记录表';



```

# scalog 
### simple communal automatic log
### 1.功能

🔥🔥记录接口请求日志🔥🔥

为项目相关分析提供数据支撑。此日志信息有别于项目中的log日志，这里只是记录web接口的请求日志。

目前项目中大多数方案是用切面或者拦截器配合注解来做，但是会存在一个问题：每个web项目都要单独实现或者copy一份此逻辑。

考虑到现在项目都微服务化，大量的微服务都需要记录请求日志，但是没有找到符合我们需求的轻量级的好的实现方案，因此有了此项目。

收集的数据，我们可以接入可视化工具中，比如Grafana,效果如下：
![](https://github.com/it4alla/scalog/blob/master/src/main/resource/images/1.png)
![](https://github.com/it4alla/scalog/blob/master/src/main/resource/images/2.png)
![](https://github.com/it4alla/scalog/blob/master/src/main/resource/images/3.png)

##### scalog的优势：

- jar推送到仓库后，项目中直接引入maven依赖，进行配置即可使用，对代码0侵入；
- 考虑到日志记录中有些需要有些不需要记录，我们提供灵活的策略配置让用户自己选择记录哪些日志；
- 考虑到用户可能会使用不同的数据库对接口日志进行记录分析，我们提供了多种数据库，用户可以自行配置选择；

一个简单的功能，在使用上，应该也是没有成本的，但是简单的功能，也可以做的更加通用，易用，可靠，可拓展，可选择，0成本，0倾入。

##### 目前支持4种策略：
- specified：仅添加@LogInfo注解的接口才会记录
- all：记录所有的接口
- some：记录所有接口，但是不记录添加了@LogInfoExclude的接口

也可以选择禁用：scalog.enable=false

##### 目前支持3种数据库：
- mysql
- oracle (待实现)
- postgresql 
- mongodb (待实现)
- es (待实现)

### 2.使用
如果公司有私有仓库，可修改pom.xml中的仓库地址，推送到私有仓库。其他项目直接引入依赖即可。（正在考虑直接发布到maven仓库，但是有点麻烦）

#### 2.1创建记录表
在项目数据库中创建数据库表
- [mysql脚本](https://github.com/it4alla/scalog/tree/master/src/main/resource/sql/mysql.sql)
- [oracle脚本](https://github.com/it4alla/scalog/tree/master/src/main/resource/sql/oracle.sql)
- [postgresql脚本](https://github.com/it4alla/scalog/tree/master/src/main/resource/sql/postgresql.sql)
#### 2.2项目中引入pom依赖
```java
		<dependency>
			<groupId>com.java4all</groupId>
			<artifactId>scalog</artifactId>
			<version>1.3.0-RELEASE</version>
		</dependency>
```
⚠⚠⚠：还需要自行引入对应的数据源依赖。比如你打算使用postgresql存储，还需要引入
```java
    <dependency>
      <groupId>org.postgresql</groupId>
      <artifactId>postgresql</artifactId>
      <version>42.2.6</version>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-jdbc</artifactId>
    </dependency>
    <dependency>
      <groupId>com.alibaba</groupId>
      <artifactId>druid</artifactId>
      <version>1.1.10</version>
    </dependency>
```
#### 2.3配置扫描(1.3之后的版本无需单独配置scalog的扫描，可以省略此步骤)
项目启动类中，配置组件扫描路径。
例如，本身项目的扫描路径为：
```java
@ComponentScan("com.runlion.fsp")
```
新增scalog的扫描:com.java4all
```java
@ComponentScan({"com.runlion.fsp","com.java4all"})
```
⚠⚠⚠：如果项目本身没配置这个路径，这里并不能简单的配置为@ComponentScan("com.java4all")，这样你项目所有的组件都会失效，项目接口404。所以项目的基本扫描路径还是要加上的，然后后面再加上com.java4all。⚠⚠⚠

#### 2.5配置文件
提供配置项如下：
- scalog.level 日志的记录策略，支持 nothing , specified , all ,some 
- scalog.countryName 国家名称
- scalog.groupName 集团名称
- scalog.organizationName 组织名称
- scalog.companyName 公司名称
- scalog.projectName 项目名称
- scalog.needResult 是否记录请求响应结果(true 记录 / false 不记录)
- scalog.enable  是否启用全局日志记录(默认true 启用 / false 禁用)
- scalog.db 日志存储的数据库，支持 mysql , postgresql (oracle , mongodb待实现)
- scalog.url 数据库url
- scalog.username 数据库用户名
- scalog.password 数据库密码
- scalog.driver-class-name 数据库驱动
- scalog.type  数据库数据源


示例：
```java
scalog:
  level: all
  countryName: 中国
  groupName: 谷歌集团
  organizationName: 谷歌开源组织
  companyName: 谷歌杭州分公司
  projectName: 棱镜项目
  enable: true
  needResult: false
  db: postgresql
  url: jdbc:postgresql://xxx.xxx.xxx.xxx:5432/xxx?stringtype=unspecified
  username: xxxx
  password: xxxxx
  driver-class-name: org.postgresql.Driver
  # 使用druid数据源
  type: com.alibaba.druid.pool.DruidDataSource
```

#### 2.5使用
上述步骤完成后，接口请求记录已经可以正常记录。
但是，以下几个字段，是可选项，切面无法拿到，默认为空：
```java
  `module_name` char(200) DEFAULT NULL COMMENT '模块名称',
  `function_name` char(500) DEFAULT NULL COMMENT '功能名称',
  `remark` char(200) DEFAULT NULL COMMENT '备注',
```
由于切面无法拿到，但是项目中需要定制化记录，所以提供了@LogInfo注解来记录，例如：
```java
    /**
     * 返利单价列表
     * web-授信管理-经销商返利单价列表
     * @param pageSize
     * @param pageNum
     * @return
     */
    @LogInfo(moduleName = "授信模块",functionName = "返利单价列表",remark = "获取返利单价列表")
    @GetMapping(value = "list")
    public ObjectRestResponse list(Integer pageSize,Integer pageNum){
        FspPageInfo fspPageInfo = rebateServiceImpl.list(pageSize,pageNum);
        return new ObjectRestResponse().data(fspPageInfo);
    }
```
### 3.用户
由于不同项目中用户体系不一样，获取用户信息方式也不一样，所以，userId,userName的处理，需要自行处理。此部分还在考虑更好的方案。



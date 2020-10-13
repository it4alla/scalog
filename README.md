# scalog 
### simple communal automatic log
### 1.功能

🔥🔥记录接口请求日志🔥🔥

为项目相关分析提供数据支撑。此日志信息有别于项目中的log日志，这里只是记录web接口的请求日志。

目前项目中大多数方案是用切面或者拦截器配合注解来做，但是会存在一个问题：每个web项目都要单独实现或者copy一份此逻辑。

考虑到现在项目都微服务化，大量的微服务都需要记录请求日志，但是没有找到符合我们需求的轻量级的好的实现方案，因此有了此项目。

收集的数据，我们可以接入公司开发的相关信息展示系统，也可以找个可视化工具，比如Grafana,效果如下：
![1](https://imgkr2.cn-bj.ufileos.com/d613cad5-07f5-456a-84d6-f4e5ef39de63.jpg?UCloudPublicKey=TOKEN_8d8b72be-579a-4e83-bfd0-5f6ce1546f13&Signature=6g%252FYikgXcVKdbCsU6Siui2tnVFI%253D&Expires=1599404088)

![2](https://imgkr2.cn-bj.ufileos.com/55e81d62-71a5-40ee-8a41-36d4da7bace7.jpg?UCloudPublicKey=TOKEN_8d8b72be-579a-4e83-bfd0-5f6ce1546f13&Signature=aarnyGG2%252BnW%252FvsVzjlt%252BqwFmXZs%253D&Expires=1599404097)

![3](https://imgkr2.cn-bj.ufileos.com/969e7802-22ed-42e0-8735-61f3b9ea6cc2.jpg?UCloudPublicKey=TOKEN_8d8b72be-579a-4e83-bfd0-5f6ce1546f13&Signature=dOJKv2VTgQFDnc3fmGhcIdEb6gA%253D&Expires=1599404103)

##### scalog的优势：

- jar推送到仓库后，项目中直接引入maven依赖，进行配置即可使用，对代码0侵入；
- 考虑到日志记录中有些需要有些不需要记录，我们提供灵活的策略配置让用户自己选择记录哪些日志；
- 考虑到用户可能会使用不同的数据库对接口日志进行记录分析，我们提供了多种数据库，用户可以自行配置选择；

一个简单的功能，在使用上，应该也是没有成本的，但是简单的功能，也可以做的更加通用，易用，可靠，可拓展，可选择，零成本，零侵入。

##### 目前支持4种策略：
- specified：仅添加@LogInfo注解的接口才会记录
- all：记录所有的接口
- some：记录所有接口，但是不记录添加了@LogInfoExclude的接口

也可以选择禁用：scalog.enable=false

##### 目前支持3种数据库：
- mysql
- postgresql 
- oracle (待实现)
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

#### 2.3配置文件
提供配置项如下：
- scalog.level 日志的记录策略，支持 specified , all ,some 
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
  enable: true
  level: all
  needResult: false
  countryName: 中国
  groupName: 谷歌集团
  organizationName: 谷歌开源组织
  companyName: 谷歌杭州分公司
  projectName: 棱镜项目
  db: postgresql
  url: jdbc:postgresql://xxx.xxx.xxx.xxx:5432/xxx?stringtype=unspecified
  username: xxxx
  password: xxxxx
  driver-class-name: org.postgresql.Driver
  # 使用druid数据源
  type: com.alibaba.druid.pool.DruidDataSource
```

#### 2.4使用
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
### 3.相关信息
userId等相关的用户信息，不同微服务获取方式可能不一样。目前处理方式为统一从请求头中获取。
前端或者移动端请求头中统一添加：user_id或者userId。
后期会对请求头信息进行拓展。目前只接收userId。



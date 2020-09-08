package com.java4all.scalog.aspect;

import com.google.gson.Gson;
import com.java4all.scalog.annotation.LogInfo;
import com.java4all.scalog.annotation.LogInfoExclude;
import com.java4all.scalog.configuration.ScalogProperties;
import com.java4all.scalog.store.executor.BaseSqlExecutor;
import com.java4all.scalog.store.LogInfoDto;
import com.java4all.scalog.store.source.SourceGenerator;
import com.java4all.scalog.utils.EnhanceServiceLoader;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import com.java4all.scalog.utils.UserInfoUtil;
import com.mongodb.MongoClient;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @decription LogInfo Aspect
 * @author wangzhongxiang
 */
@Aspect
@Component
public class LogInfoAspect implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogInfoAspect.class);
    private static final String MYSQL_DB = "mysql";
    private static final String ORACLE_DB = "oracle";
    private static final String MONGO_DB = "mongodb";
    private static final String POSTGRESQL_DB = "postgresql";
    private static final String DEFAULT_DB_TYPE = MYSQL_DB;
    private static final String LEVEL_NOTHING = "nothing";
    private static final String LEVEL_ALL = "all";
    private static final String LEVEL_SPECIFIED = "specified";
    private static final String DEFAULT_LEVEL = LEVEL_ALL;

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static ThreadPoolExecutor executor =
            new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
                    Runtime.getRuntime().availableProcessors()*2,10,
            TimeUnit.SECONDS,new LinkedBlockingQueue<>(100000),
            new NameThreadFactory(),new CallerRunsPolicy());
    @Autowired
    private ScalogProperties properties;
    private BaseSqlExecutor sqlExecutor;

    /**
     * *.controller
     * include subclass package
     */
    @Pointcut("execution(* com..controller..*.*(..))")
    public void pointCut(){}

    @Around("pointCut()")
    public Object aroundPointCut(ProceedingJoinPoint joinPoint) throws Throwable {
        Boolean enable = properties.getEnable();
        if(enable != null && enable == false){
            return joinPoint.proceed();
        }
        ServletRequestAttributes attributes =
                (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Class<? extends MethodSignature> clazz = signature.getDeclaringType();
        Method method = signature.getMethod();

        LogInfoDto dto = new LogInfoDto();
        dto.setCountryName(properties.getCountryName());
        dto.setGroupName(properties.getGroupName());
        dto.setOrganizationName(properties.getOrganizationName());
        dto.setCompanyName(properties.getCompanyName());
        dto.setProjectName(properties.getProjectName());
        //Cannot be processed asynchronously，it will lose request attributes
        request2LogInfoDto(request,dto);
        Object proceed =null;
        long startTime = 0L;
        long endTime = 0L;
        String result = null;
        try {
            startTime = System.currentTimeMillis();
            proceed = joinPoint.proceed();
            endTime = System.currentTimeMillis();
            //not a web controller class,skip
            if(!clazz.isAnnotationPresent(Controller.class)
                    && !clazz.isAnnotationPresent(RestController.class)) {
                if(LOGGER.isDebugEnabled()) {
                    LOGGER.debug("{} not a web controller class,skip",clazz.toString());
                }
                return proceed;
            }
            //not a web controller method,skip
            if(!method.isAnnotationPresent(RequestMapping.class)
                    && !method.isAnnotationPresent(GetMapping.class)
                    && !method.isAnnotationPresent(PostMapping.class)
                    && !method.isAnnotationPresent(PutMapping.class)
                    && !method.isAnnotationPresent(DeleteMapping.class)
                    && !method.isAnnotationPresent(PatchMapping.class)) {
                if(LOGGER.isDebugEnabled()) {
                    LOGGER.debug("{}.{} not a web controller method,skip",clazz.toString(),method.getName());
                }
                return proceed;
            }

            String level = properties.getLevel();
            if(StringUtils.isEmpty(level)){
                level  = DEFAULT_LEVEL;
            }
            if(LEVEL_NOTHING.equalsIgnoreCase(level)){
                return proceed;
            }
            boolean logInfoExcludePresent = method.isAnnotationPresent(LogInfoExclude.class);
            if(logInfoExcludePresent){
                return proceed;
            }
            boolean logInfoPresent = method.isAnnotationPresent(LogInfo.class);
            if(LEVEL_SPECIFIED.equals(level)){
                if(!logInfoPresent){
                    return proceed;
                }
            }
            Boolean needResult = properties.getNeedResult();
            String userId = UserInfoUtil.getUserId(request);
            if (!StringUtils.isEmpty(userId)){
                dto.setUserId(userId);
            }
            if (null != needResult && needResult){
                result = new Gson().toJson(proceed);
            }
            return proceed;
        } catch (Exception e) {
            endTime = System.currentTimeMillis();
            dto.setErrorMessage(e.getMessage());
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));
            dto.setErrorStackTrace(sw.toString().substring(0,2000));
            throw e;
        }finally {
            final long startTimeF = startTime;
            final long endTimeF = endTime;
            final String finalResult = result;
            executor.execute(()-> {
                try {
                    this.writeLog(joinPoint, dto,startTimeF,endTimeF, finalResult, clazz, method);
                } catch (Exception e) {
                    LOGGER.warn("{}.{} log info write failed,But it does not affect business logic:{}",
                            clazz.toString(),method.getName(),e.getMessage());
                }
            });

        }
    }

    private void request2LogInfoDto(HttpServletRequest request,LogInfoDto dto){
        String url = request.getRequestURL() == null ? "" : request.getRequestURL().toString();
        dto.setUrl(url);
        dto.setMethodType(request.getMethod());
        dto.setIp(request.getRemoteAddr());
        dto.setUserAgent(request.getHeader("User-Agent"));
        dto.setClientType(request.getHeader("Client-Type"));
    }
    /**
     * write log
     */
    private void writeLog(ProceedingJoinPoint joinPoint, LogInfoDto dto, long startTime, long endTime, String result,
                          Class<? extends MethodSignature> clazz, Method method) {
        LogInfo logInfo = method.getAnnotation(LogInfo.class);
        if(null != logInfo){
            dto.setModuleName(logInfo.moduleName());
            dto.setFunctionName(logInfo.functionName());
            dto.setRemark(logInfo.remark());
        }
        dto.setClassName(clazz.toString());
        dto.setMethodName(method.getName());
        dto.setRequestParams(new Gson().toJson(joinPoint.getArgs()));
        dto.setCost(endTime-startTime);
        dto.setResult(result);
        dto.setGmtStart(FORMAT
                .format(LocalDateTime.ofInstant(Instant.ofEpochMilli(startTime), ZoneId.systemDefault())));
        dto.setGmtEnd(FORMAT
                .format(LocalDateTime.ofInstant(Instant.ofEpochMilli(endTime), ZoneId.systemDefault())));

        //defensive try catch,althought it will not happen in normally
        try {
            sqlExecutor.insert(dto);
        }catch (Exception ex){
            LOGGER.error("The sqlExecutor may be null,please check,{}",ex.getMessage());
        }
    }

    @Override
    public void afterPropertiesSet() {
        String dbType = StringUtils.isEmpty(properties.getDb()) ? DEFAULT_DB_TYPE : properties.getDb();
        LOGGER.info("scalog db type is [{}]",dbType);
        //load data source
        SourceGenerator sourceGenerator = EnhanceServiceLoader.load(SourceGenerator.class, dbType);
        Object source = sourceGenerator.generateSource();

        //load sqlExecutor
        if(MYSQL_DB.equalsIgnoreCase(dbType) ||
                ORACLE_DB.equalsIgnoreCase(dbType) ||
                POSTGRESQL_DB.equalsIgnoreCase(dbType)){
            sqlExecutor = EnhanceServiceLoader.load(BaseSqlExecutor.class,dbType,
                    new Class[]{DataSource.class},new Object[]{(DataSource)source});
        }
        else if(MONGO_DB.equalsIgnoreCase(dbType)){
            sqlExecutor = EnhanceServiceLoader.load(BaseSqlExecutor.class,dbType,
                    new Class[]{MongoClient.class},new Object[]{(MongoClient)source});
        }
    }


    /**
     * name thread factory
     */
    private static final class NameThreadFactory implements ThreadFactory {
        private final ThreadGroup group;
        private final AtomicInteger index = new AtomicInteger(1);

        public NameThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = s !=null ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(group, r, "Java4all-Thread-LogInfo-" + index.getAndIncrement());
            thread.setDaemon(true);
            if (thread.getPriority() != Thread.NORM_PRIORITY) {
                thread.setPriority(Thread.NORM_PRIORITY);
            }
            return thread;
        }
    }



}

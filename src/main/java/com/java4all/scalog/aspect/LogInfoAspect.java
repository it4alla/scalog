package com.java4all.scalog.aspect;

import com.google.gson.Gson;
import com.java4all.scalog.annotation.LoadLevel;
import com.java4all.scalog.annotation.LogInfo;
import com.java4all.scalog.properties.ScalogProperties;
import com.java4all.scalog.store.executor.BaseSqlExecutor;
import com.java4all.scalog.store.LogInfoDto;
import com.runlion.security.server.entity.UserInfo;
import com.runlion.security.server.util.UserInfoUtil;
import java.lang.reflect.Method;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.ServiceLoader;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import javax.servlet.http.HttpServletRequest;
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
    private static final String DEFAULT_DB_TYPE = "mysql";
    private static final String LEVEL_NO = "no";
    private static final String LEVEL_ALL = "all";
    private static final String LEVEL_SPECIFIED = "specified";
    private static final String DEFAULT_LEVEL = LEVEL_ALL;

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
    private static ThreadPoolExecutor executor =
            new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),8,10,
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
        long startTime = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long endTime = System.currentTimeMillis();

        ServletRequestAttributes attributes =
                (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Class<? extends MethodSignature> clazz = signature.getDeclaringType();
        Method method = signature.getMethod();

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
        if(LEVEL_NO.equalsIgnoreCase(level)){
            return proceed;
        }
        final String activeLevel = level;

        String companyName = properties.getCompanyName();
        String projectName = properties.getProjectName();

        //use Gson can resolve the args contains File,FastJson is not support
        String result = new Gson().toJson(proceed);
        executor.execute(()-> {
            try {
                //TODO fix the sub thread lose the request attributes
                this.writeLog(joinPoint, startTime,endTime, result, request, clazz, method, activeLevel,companyName,projectName);
            } catch (Exception e) {
                LOGGER.warn("{}.{} log info write failed,But it does not affect business logic:{}",
                        clazz.toString(),method.getName(),e.getMessage(),e);
            }
        });
        return proceed;
    }

    /**
     * write log
     */
    private void writeLog(ProceedingJoinPoint joinPoint, long startTime,long endTime, String result,
            HttpServletRequest request, Class<? extends MethodSignature> clazz, Method method,String activeLevel,
            String companyName,String projectName) throws Exception{
        final boolean annotationPresent = method.isAnnotationPresent(LogInfo.class);
        if(LEVEL_SPECIFIED.equalsIgnoreCase(activeLevel)){
            if(!annotationPresent){
                return;
            }
        }

        LogInfoDto dto = new LogInfoDto();
        LogInfo logInfo = method.getAnnotation(LogInfo.class);
        dto.setCompanyName(companyName);
        dto.setProjectName(projectName);
        dto.setModuleName(logInfo.moduleName());
        dto.setFunctionName(logInfo.functionName());
        dto.setRemark(logInfo.remark());
        String userId = "";
        try {
            UserInfo currentUser = UserInfoUtil.getCurrentUser(UserInfo.class);
            userId = currentUser.getUserId();
        }catch (Exception ex){
            LOGGER.warn("Get current user failed,But it does not affect business logic,{}",ex.getMessage(),ex);
        }
        String url = request.getRequestURL() == null ? "" : request.getRequestURL().toString();
        dto.setUrl(url);
        dto.setMethodType(request.getMethod());
        dto.setClassName(clazz.toString());
        dto.setMethodName(method.getName());
        dto.setIp(request.getRemoteAddr());
        dto.setRequestParams(new Gson().toJson(joinPoint.getArgs()));
        dto.setUserId(userId);
        dto.setUserAgent(request.getHeader("User-Agent"));
        dto.setClientType(request.getHeader("Client-Type"));
        dto.setCost(endTime-startTime);
        dto.setResult(result);
        dto.setGmtStart(FORMAT
                .format(LocalDateTime.ofInstant(Instant.ofEpochMilli(startTime), ZoneId.systemDefault())));
        dto.setGmtEnd(FORMAT
                .format(LocalDateTime.ofInstant(Instant.ofEpochMilli(endTime), ZoneId.systemDefault())));
        sqlExecutor.insert(dto);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        String dbType = StringUtils.isEmpty(properties.getDb()) ? DEFAULT_DB_TYPE : properties.getDb();
        LOGGER.info("scalog db type is [{}]",dbType);
        ServiceLoader<BaseSqlExecutor> sqlExecutors = ServiceLoader.load(BaseSqlExecutor.class);
        for (BaseSqlExecutor executor : sqlExecutors){
            LoadLevel loadLevel = executor.getClass().getAnnotation(LoadLevel.class);
            if(loadLevel != null && dbType.equalsIgnoreCase(loadLevel.name())){
                sqlExecutor = executor.getClass().newInstance();
                LOGGER.info("load sqlExecutor [{}] ......",sqlExecutor.getClass().getName());
            }
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

package com.java4all.scalog.aspect;

import com.google.gson.Gson;
import com.java4all.scalog.annotation.LogInfo;
import com.java4all.scalog.utils.SourceUtil;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
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
public class LogInfoAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogInfoAspect.class);
    private static final String LOG_TABLE = "log_info";
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    private static final String INSERT_LOG_SQL =
            "insert into " + LOG_TABLE +
                    "(`id`, `company_name`, `project_name`, `module_name`, `function_name`, "
                    + "`class_name`, `method_name`, `method_type`, `url`, `request_params`,"
                    + "`result`, `remark`, `cost`, `ip`, `user_id`, "
                    + "`user_Name`, `log_type`,`gmt_start`, `gmt_end`,`gmt_create`, `gmt_modified`)"
                    + " values "
                    + "(?, ?, ?, ?, ?,"
                    + " ?, ?, ?, ?, ?,"
                    + " ?, ?, ?, ?, ?,"
                    + " ?, ?, ?, ?, now(), now())";

    private static ThreadPoolExecutor executor =
            new ThreadPoolExecutor(4,8,10,
            TimeUnit.SECONDS,new LinkedBlockingQueue<>(100000),
            new NameThreadFactory(),new CallerRunsPolicy());

    /**
     * the dataSource from the application context
     */
    @Autowired
    private DataSource dataSource;

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
        //use Gson can resolve the args contains File,FastJson is not support
        String result = new Gson().toJson(proceed);
        executor.execute(()-> this.writeLog(joinPoint, startTime,endTime, result, request, clazz, method));
        return proceed;
    }

    /**
     * write log
     */
    private void writeLog(ProceedingJoinPoint joinPoint, long startTime,long endTime, String result,
            HttpServletRequest request, Class<? extends MethodSignature> clazz, Method method) {
        String companyName = "";
        String projectName = "";
        String moduleName = "";
        String functionName = "";
        String remark = "";
        if(method.isAnnotationPresent(LogInfo.class)) {
            LogInfo logInfo = method.getAnnotation(LogInfo.class);
            companyName = logInfo.companyName();
            projectName = logInfo.projectName();
            moduleName = logInfo.moduleName();
            functionName = logInfo.functionName();
            remark = logInfo.remark();
        }

        String url = request.getRequestURL() == null ? "" : request.getRequestURL().toString();
        String methodType = request.getMethod();
        String className = clazz.toString();
        String methodName = method.getName();
        String ip = request.getRemoteAddr();
        String requestParams = new Gson().toJson(joinPoint.getArgs());
        String userId = "";

//        try {
//            UserInfo currentUser = UserInfoUtil.getCurrentUser(UserInfo.class);
//            userId = currentUser.getUserId();
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(true);
            PreparedStatement ps = connection.prepareStatement(INSERT_LOG_SQL);
            ps.setString(1,generateId());
            ps.setString(2,companyName);
            ps.setString(3,projectName);
            ps.setString(4,moduleName);
            ps.setString(5,functionName);
            ps.setString(6,className);
            ps.setString(7,methodName);
            ps.setString(8,methodType);
            ps.setString(9,url);
            ps.setString(10,requestParams);
            ps.setString(11,result);
            ps.setString(12,remark);
            ps.setLong(13,endTime - startTime);
            ps.setString(14,ip);
            ps.setString(15,userId);
            ps.setString(16,userId);
            ps.setInt(17,1);
            ps.setString(18,FORMAT.format(new Date(startTime)));
            ps.setString(19,FORMAT.format(new Date(endTime)));
            ps.execute();
        } catch (SQLException e) {
            LOGGER.error("log info insert failed");
            e.printStackTrace();
        } finally {
          SourceUtil.close(connection);
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

    /**
     * generate id
     * 9000 0000 every millisecond
     * @return
     */
    private static String generateId() {
        String time = LocalDateTime.now().toString()
                .replace("-", "")
                .replace("T", "")
                .replace(":", "")
                .replace(".", "");

        return new StringBuffer().append(time).append(ThreadLocalRandom.current()
                .nextLong(10000000,100000000)).toString();
    }

}

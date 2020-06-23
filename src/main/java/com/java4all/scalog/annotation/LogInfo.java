package com.java4all.scalog.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @decription 日志记录
 * @Pointcut("execution(* com..controller..*.*(..))")
 * 默认controller包及子包接口全部会记录
 *
 * @author wangzhongxiang
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogInfo {

    /**公司*/
    String companyName() default "";

    /**项目*/
    String projectName() default "";

    /**模块*/
    String moduleName() default "";

    /**功能*/
    String functionName() default "";

    /**描述*/
    String remark() default "";

}


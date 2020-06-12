package com.java4all.scalog.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @decription 日志记录
 * 默认controller层接口全部会记录，但是部分参数默认为空，如果需要，在注解中表明
 * @author wangzhongxiang
 * @date 2020年05月14日 11:41:48
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogInfo {
    //TODO

}


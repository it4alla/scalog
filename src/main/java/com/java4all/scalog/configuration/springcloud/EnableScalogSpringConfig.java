package com.java4all.scalog.configuration.springcloud;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

/**
 * @author wangzhongxiang
 */
@Deprecated
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({SpringApplicationContextProviderRegistrar.class})
public @interface EnableScalogSpringConfig {

}

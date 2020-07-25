package com.java4all.scalog.properties.springcloud;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

/**
 * @author wangzhongxiang
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({SpringApplicationContextProviderRegistrar.class})
public @interface EnableScalogSpringConfig {

}

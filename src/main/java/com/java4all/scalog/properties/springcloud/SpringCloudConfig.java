package com.java4all.scalog.properties.springcloud;

import com.java4all.scalog.holder.ObjectHolder;
import org.springframework.context.ApplicationContext;

/**
 * @author wangzhongxiang
 */
public class SpringCloudConfig {

    public static String getConfig(String key,String defaultValue){
        ApplicationContext applicationContext = ObjectHolder.INSTANCE.getObject(ApplicationContext.class);
        if(null == applicationContext || null == applicationContext.getEnvironment()){
            return defaultValue;
        }
        String value = applicationContext.getEnvironment().getProperty(key);
        return value;
    }
}

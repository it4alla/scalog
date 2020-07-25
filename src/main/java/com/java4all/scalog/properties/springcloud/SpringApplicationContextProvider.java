package com.java4all.scalog.properties.springcloud;

import com.java4all.scalog.holder.ObjectHolder;
import com.java4all.scalog.properties.Constants;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * @author wangzhongxiang
 */
@Service
public class SpringApplicationContextProvider implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ObjectHolder.INSTANCE.setObject(Constants.SPRING_APPLICATION_CONTEXT,applicationContext);
    }
}

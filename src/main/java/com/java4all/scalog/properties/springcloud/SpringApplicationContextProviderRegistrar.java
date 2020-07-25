package com.java4all.scalog.properties.springcloud;

import com.java4all.scalog.properties.Constants;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author wangzhongxiang
 * @date 2020年07月25日 10:17:44
 */
public class SpringApplicationContextProviderRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
            BeanDefinitionRegistry registry) {
//        if(!registry.containsBeanDefinition(Constants.BEAN_NAME_SPRING_APPLICATION_CONTEXT_PROVIDER)){
////            AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder
////                    .genericBeanDefinition(SpringApplicationContextProvider.class).getBeanDefinition();
////            registry.registerBeanDefinition(Constants.BEAN_NAME_SPRING_APPLICATION_CONTEXT_PROVIDER,beanDefinition);
////        }
    }
}

package com.java4all.scalog.configuration.springcloud;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author wangzhongxiang
 */
@Deprecated
public class SpringApplicationContextProviderRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
            BeanDefinitionRegistry registry) {
//        if(!registry.containsBeanDefinition(Constants.BEAN_NAME_SPRING_APPLICATION_CONTEXT_PROVIDER)){
//            AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder
//                    .genericBeanDefinition(SpringApplicationContextProvider.class).getBeanDefinition();
//            registry.registerBeanDefinition(Constants.BEAN_NAME_SPRING_APPLICATION_CONTEXT_PROVIDER,beanDefinition);
//        }
    }
}

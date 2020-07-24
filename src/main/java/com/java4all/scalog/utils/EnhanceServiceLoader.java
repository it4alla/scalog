package com.java4all.scalog.utils;

import com.java4all.scalog.annotation.LoadLevel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wangzhongxiang
 */
public class EnhanceServiceLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(EnhanceServiceLoader.class);
    private static final String SERVICES_DIRECTORY = "META-INF/services/";

    public static <S>  S load(Class<S> service,String activeName,Class[] argsType,Object[] args){
        List<Class> extentions = findAllExtentions(service, activeName, EnhanceServiceLoader.class.getClassLoader());
        List<Class> activeExtentions = new ArrayList<>();
        for(Class clazz: extentions){
            LoadLevel loadLevel = (LoadLevel) clazz.getAnnotation(LoadLevel.class);
            if(loadLevel != null && activeName.equalsIgnoreCase(loadLevel.name())){
                activeExtentions.add(clazz);
            }
        }
        Class extention = activeExtentions.get(0);

        try {
            S result = initInstance(service,activeExtentions.get(0),argsType,args);
            LOGGER.info("load " + service.getSimpleName() + "[" + activeName + "] extension by class[" + extention
                    .getName() + "]");
            return result;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static <S> S initInstance(Class<S> service, Class implClazz, Class[] argsType, Object[] args)
            throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException   {
        S s = null;
        if(argsType != null && args != null){
            Constructor<S> constructor = implClazz.getDeclaredConstructor(argsType);
            s = service.cast(constructor.newInstance(args));
        }else {
            s = service.cast(implClazz.newInstance());
        }
        return s;
    }

    private static <S> List<Class> findAllExtentions(Class<S> service,String activeName,ClassLoader loader) {
        List<Class> extentions = new ArrayList<>();
        try {
            loadFile(service,SERVICES_DIRECTORY,loader,extentions);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return extentions;
    }


    private static void loadFile(Class<?> service, String dir, ClassLoader classLoader, List<Class> extensions)
            throws IOException {
        String fileName = dir + service.getName();
        Enumeration<URL> urls;
        if (classLoader != null) {
            urls = classLoader.getResources(fileName);
        } else {
            urls = ClassLoader.getSystemResources(fileName);
        }
        if (urls != null) {
            while (urls.hasMoreElements()) {
                java.net.URL url = urls.nextElement();
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new InputStreamReader(url.openStream(),
                            Charset.forName("UTF-8")));
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        final int ci = line.indexOf('#');
                        if (ci >= 0) {
                            line = line.substring(0, ci);
                        }
                        line = line.trim();
                        if (line.length() > 0) {
                            try {
                                extensions.add(Class.forName(line, true, classLoader));
                            } catch (LinkageError | ClassNotFoundException e) {
                                LOGGER.warn("load [{}] class fail. {}", line, e.getMessage());
                            }
                        }
                    }
                } catch (Throwable e) {
                    LOGGER.warn(e.getMessage());
                } finally {
                    SourceUtil.close(reader);
                }
            }
        }
    }
}

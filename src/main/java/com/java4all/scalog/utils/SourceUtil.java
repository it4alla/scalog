package com.java4all.scalog.utils;

import org.springframework.stereotype.Component;

/**
 * @decription Source Util
 * @author wangzhongxiang
 */
@Component
public class SourceUtil {

    public static void close(AutoCloseable...sources){
        if(sources != null && sources.length > 0){
            for (AutoCloseable source:sources){
                if(source != null){
                    try {
                        source.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}

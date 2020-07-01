package com.java4all.scalog.utils;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.stereotype.Component;

/**
 * @decription Source Util
 * @author wangzhongxiang
 */
@Component
public class SourceUtil {

    /**
     * close source
     * @param sources
     */
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

    /**
     * generate id
     * 9000 0000 every millisecond
     * @return
     */
    public static String generateId() {
        String time = LocalDateTime.now().toString()
                .replace("-", "")
                .replace("T", "")
                .replace(":", "")
                .replace(".", "");

        return new StringBuffer().append(time).append(ThreadLocalRandom.current()
                .nextLong(10000000,100000000)).toString();
    }
}

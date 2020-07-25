package com.java4all.scalog.configuration;

/**
 * @author wangzhongxiang
 * @date 2020年07月25日 13:47:19
 */
public interface Configuration {

    /**
     * get config
     * @param key
     * @param defaultValue
     * @return
     */
    String getConfig(String key,String defaultValue);

}

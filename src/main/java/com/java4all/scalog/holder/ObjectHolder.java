package com.java4all.scalog.holder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wangzhongxiang
 */
public enum  ObjectHolder {

    /**singleton*/
    INSTANCE;
    private static final int MAP_SIZE = 8;
    private static final Map<String,Object> OBJECT_MAP = new ConcurrentHashMap<>(MAP_SIZE);

    public <T> T getObject(Class<T> clazz){
        return clazz.cast(OBJECT_MAP.values().stream().filter(clazz::isInstance).findAny()
                .orElseThrow(()-> new RuntimeException("Can't find any object of class " + clazz.getName())));
    }

    public Object setObject(String key,Object value){
        return OBJECT_MAP.putIfAbsent(key,value);
    }
}

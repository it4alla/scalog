package com.java4all.scalog.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:对象copy工具类
 *
 * @author dsl
 */
public class BaseHelper {

    /**
     * @param resource
     * @param target
     * @param <T>
     * @param <R>
     * @return
     * @apiNote resource to target
     */
    @Deprecated
    public static <T, R> T r2t(R resource, T target) {
        if (resource == null) {
            return null;
        }
        BeanUtils.copyProperties(resource, target);
        return target;
    }

    /**
     * @param resource
     * @param target
     * @param <T>
     * @param <R>
     * @return
     * @apiNote resource to target
     */
    public static <T, R> T r2t(R resource, Class<T> target) {
        if (resource == null) {
            return null;
        }
        T tt = null;
        try {
            tt = (T) target.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        BeanUtils.copyProperties(resource, tt);
        return tt;
    }

    /**
     * @param resourceList
     * @param target
     * @param <T>
     * @param <R>
     * @return
     * @apiNote resourceList to target type list
     */
    public static <T, R> List<T> resourceList2target(List<R> resourceList, Class<T> target) {
        List<T> tList = new ArrayList<>();
        if (CollectionUtils.isEmpty(resourceList)) {
            return tList;
        }
        resourceList.forEach(resource -> {
            T t1 = BaseHelper.r2t(resource, target);
            tList.add(t1);
        });
        return tList;
    }
}

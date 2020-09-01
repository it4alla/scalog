package com.java4all.scalog.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:对象copy工具类
 *
 * @author dsl
 */
public class BaseHelper {

    /**
     * @param resource
     * @param target
     * @return
     * @apiNote resource to target
     */
    public static <T, R> T convertTotarget(R resource, Class<T> target) {
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
     * @return
     */
    public static <T, R> List<T> convertTotargetList(List<R> resourceList, Class<T> target) {
        List<T> tList = new ArrayList<>();
        if (CollectionUtils.isEmpty(resourceList)) {
            return tList;
        }
        resourceList.forEach(resource -> {
            T t1 = BaseHelper.convertTotarget(resource, target);
            tList.add(t1);
        });
        return tList;
    }

    /**
     * 对象转map
     * @param obj
     * @return
     */
    public static Map<String, Object> objectToMap(Object obj) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 获取f对象对应类中的所有属性域
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0, len = fields.length; i < len; i++) {
            String varName = fields[i].getName();
            try {
                // 获取原来的访问控制权限
                boolean accessFlag = fields[i].isAccessible();
                // 修改访问控制权限
                fields[i].setAccessible(true);
                // 获取在对象f中属性fields[i]对应的对象中的变量
                Object o = fields[i].get(obj);
                if (o != null){
                    map.put(varName, o.toString());
                }
                // 恢复访问控制权限
                fields[i].setAccessible(accessFlag);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }
        }
        return map;
    }
}

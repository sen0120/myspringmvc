package com.hus.util;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * javabean字段拷贝到其他bean,仅支持字段名相同的拷贝
 */
public class BaseConvert {
    private static final Logger logger = LoggerFactory.getLogger(BaseConvert.class);

    /**
     * @param source 老Object
     * @param target 需要拷贝到的目的Object对象类型
     * @param <T>    任意含非空构造器的javabean
     * @return 新Object
     * @throws Exception
     */
    public static <T> T convert(Object source, Class<T> target) {
        return springBeanConvert(source, target);
    }

    /**
     * @param sources 老Object集合
     * @param target  需要拷贝到的目的Object对象类型
     * @param <T>     任意含非空构造器的javabean
     * @return 新Object集合
     * @throws Exception
     */

    public static <T> List<T> convert(List<?> sources, Class<T> target) {
        return springBatchBeanConvert(sources, target);
    }

    public static <T> T convert(Object source, Class<T> target, String... ignoreProperties) {
        T targetObj = null;
        try {
            targetObj = target.newInstance();
        } catch (InstantiationException e) {
            logger.error("转换出错", e);
        } catch (IllegalAccessException e) {
            logger.error("转换出错", e);
        }
        org.springframework.beans.BeanUtils.copyProperties(source, targetObj, ignoreProperties);

        return targetObj;
    }

    /**
     * Model转Map
     *
     * @param model
     * @return
     */
    /*public static Map convertModelToMap(Model<?> model) {
        Class type = model.getClass();
        Map returnMap = new HashMap();
        BeanInfo beanInfo = null;
        try {
            beanInfo = Introspector.getBeanInfo(type);
        } catch (IntrospectionException e) {
            logger.error(">>> convertModelToMap error:", e);
        }

        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor descriptor : propertyDescriptors) {
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = null;
                try {
                    result = readMethod.invoke(model, new Object[0]);
                } catch (IllegalAccessException e) {
                    logger.error(">>> convertModelToMap error:", e);
                } catch (InvocationTargetException e) {
                    logger.error(">>> convertModelToMap error:", e);
                }
                if (result != null) {
                    returnMap.put(propertyName, result);
                } *//*else {
                    returnMap.put(propertyName, "");
                  }*//*
            }
        }
        return returnMap;
    }*/

    /**
     * @param source 老Object
     * @param target 需要拷贝到的目的Object对象类型
     * @param <T>    任意含非空构造器的javabean
     * @return 新Object
     * @throws Exception
     */
    private static <T> T springBeanConvert(Object source, Class<T> target) {
        T targetObj = null;
        if (source == null) {
            return null;
        }
        try {
            targetObj = target.newInstance();
        } catch (InstantiationException e) {
            logger.error("转换出错", e);
        } catch (IllegalAccessException e) {
            logger.error("转换出错", e);
        }
        BeanUtils.copyProperties(source, targetObj);

        return targetObj;
    }

    /**
     * @param sources 老Object集合
     * @param target  需要拷贝到的目的Object对象类型
     * @param <T>     任意含非空构造器的javabean
     * @return 新Object集合
     * @throws Exception
     */
    private static <T> List<T> springBatchBeanConvert(List<?> sources, Class<T> target) {
        if (sources == null) {
            return null;
        }
        if (CollectionUtils.isEmpty(sources)) {
            return new ArrayList<T>();
        }
        List<T> list = new ArrayList<T>();
        for (Object object : sources) {
            T t = springBeanConvert(object, target);
            list.add(t);
        }
        return list;
    }


}
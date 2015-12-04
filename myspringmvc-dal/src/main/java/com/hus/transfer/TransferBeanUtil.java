package com.hus.transfer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

/**
 * TestTransfer
 *
 * User: duxing //change at Setting Tab
 * Date: 2015-10-26 07:30:04
 * Generate by autoTransfer
 * Powered by duxing@Taobao
 */

public class TransferBeanUtil {

    public static <T> T beanConvert(Object object, Class<T> tClass) throws Exception {
        T t = tClass.newInstance();
        BeanUtils.copyProperties(object, t);
        return t;
    }

    public static <T> List batchBeanConvert(List<Object> objects,
                                            Class<T> tClass) throws Exception {
        List<T> tClasses = new ArrayList<T>();
        for (Object object : objects) {
            T t = beanConvert(object, tClass);
            tClasses.add(t);
        }
        return tClasses;
    }

}
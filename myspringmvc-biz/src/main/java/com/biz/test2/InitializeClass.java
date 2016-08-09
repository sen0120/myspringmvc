package com.biz.test2;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by fanyun on 16/7/28.
 */
public class InitializeClass implements ApplicationContextAware {

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println(11);
        applicationContext.getBean("testBean");
    }
}

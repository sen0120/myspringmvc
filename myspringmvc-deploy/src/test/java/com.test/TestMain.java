package com.test;

import java.io.IOException;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * Created by fanyun on 16/7/12.
 */
public class TestMain {
    public static void main(String[] args) throws IOException {
        ApplicationContext applicationContext = new XmlWebApplicationContext();
        //        applicationContext.isSingleton()

        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();
        //        defaultListableBeanFactory.isSingleton();
    }
}

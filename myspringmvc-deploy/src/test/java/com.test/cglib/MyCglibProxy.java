package com.test.cglib;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class MyCglibProxy implements MethodInterceptor {

    private String name;

    public MyCglibProxy(String name) {
        this.name = name;
    }

    public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("befor");
        Object result = methodProxy.invokeSuper(object, args);
        System.out.println("after");
        System.out.println(object.getClass().getName());
        System.out.println(method.getClass().getName());
        return result;
    }
}
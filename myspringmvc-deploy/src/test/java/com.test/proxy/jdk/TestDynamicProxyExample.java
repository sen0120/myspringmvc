package com.test.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Created by fanyun on 16/7/25.
 */
public class TestDynamicProxyExample {
    public static void main(String[] args) {
        ConcreteClass c = new ConcreteClass();//元对象(被代理对象)  
        InvocationHandler ih = new ProxyHandler(c);//代理实例的调用处理程序。  
        //创建一个实现业务接口的代理类,用于访问业务类(见代理模式)。  
        //返回一个指定接口的代理类实例，该接口可以将方法调用指派到指定的调用处理程序，如ProxyHandler。  
        TargetInterface targetInterface = (TargetInterface) Proxy.newProxyInstance(c.getClass().getClassLoader(), c.getClass().getInterfaces(), ih);
        //调用代理类方法,Java执行InvocationHandler接口的方法.  
        int i = targetInterface.targetMethodA(5);
        System.out.println(i);
        System.out.println();
        int j = targetInterface.targetMethodB(15);
        System.out.println(j);
    }
}

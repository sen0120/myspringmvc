package com.test.bytecode.classloader;

public class A {
    {
        System.out.println("A 初始化块");
    }

    static {
        System.out.println("A 静态块");
    }

    public static int b = 1;
    private static Integer integer = staticVariable();

    public A() {
        System.out.println("A 构造函数");
    }

    public static Integer staticMethod() {
        b = 2;
        System.out.println("A 初始化静态变量");
        return 10000;
    }

    public static Integer staticVariable() {
        b = 2;
        System.out.println("A 初始化静态变量");
        return 10000;
    }

    public void getA() {
        System.out.println("A 的普通函数");
    }
}
 
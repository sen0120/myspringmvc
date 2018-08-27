package com.test.bytecode.classloader;

public class MyClassLoader {
    public static void main(String[] args) throws Exception {
        A a = new A();
        System.err.println(a.b);
        if (1 == 1) return;
        /*
         * 加载 -> 链接 -> 初始化 ->使用 -> 写在
         * 链接:验证 -> 准备 -> 解析
         */
        System.out.println("Class.forName");
        Class<?> aClass = Class.forName("com.test.bytecode.classloader.A");//得到的已经初始化完成的
        System.out.println(aClass);

        System.out.println("classLoader.loadClass");
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        Class<?> bClass = classLoader.loadClass("com.test.bytecode.classloader.A");//还没链接的对象
        System.out.println(bClass);

        System.out.println("手动初始化");
        Object o = bClass.newInstance();
        System.out.println(22);
    }
}

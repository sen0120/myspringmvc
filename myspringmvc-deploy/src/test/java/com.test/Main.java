package com.test;

public class Main {

    public void test(Object o) {
        System.out.println("Object");
    }

    public void test(String s) {
        System.out.println("String");
    }

    public void test(Integer i) {
        System.out.println("Integer");
    }

    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param args
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static void main(String[] args) {
        Main main = new Main();
//        main.test(null);
    }

}
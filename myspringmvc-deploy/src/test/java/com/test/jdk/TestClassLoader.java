package com.test.jdk;

public class TestClassLoader {

    public static void main(String[] args) throws ClassNotFoundException {
        Class c = Class.forName("com.test.jdk.TestClassLoader$UnderTestClass1");

        TestClassLoader.class.getClassLoader().loadClass("com.test.jdk.TestClassLoader$UnderTestClass2");
    }

    public static class UnderTestClass1 {
        public static String testStr;

        static {
            testStr = "test1";
            System.out.println(testStr);
        }
    }

    public static class UnderTestClass2 {
        public static String testStr;

        static {
            testStr = "test2";
            System.out.println(testStr);
        }
    }

}  
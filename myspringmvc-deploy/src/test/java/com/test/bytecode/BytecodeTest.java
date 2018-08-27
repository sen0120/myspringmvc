package com.test.bytecode;

/**
 * 获取字节码的三种方式
 */
public class BytecodeTest {
    public static void main(String[] args) {
        Class<?> aClass = null;
        try {
            aClass = Class.forName("com.test.bytecode.BytecodeTest");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Class<BytecodeTest> bClass = BytecodeTest.class;

        Class<? extends BytecodeTest> cClass = new BytecodeTest().getClass();

        System.out.println(aClass);
        System.out.println(bClass);
        System.out.println(cClass);

        try {
            Class<?> dClass = String.class.getClassLoader().loadClass("java.lang.String");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

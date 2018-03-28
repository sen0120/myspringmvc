package com.test.jvm;

public class StatckTest {
    static int i = 0;

    public static void main(String[] args) {
        test();
    }

    public static void test() {
        i++;
        A a = new A();
        System.out.println(i);
        test();
        System.out.println(a.getB());
    }

    private static class A {
        byte[] b = new byte[1 * 1024 * 1024];

        public byte[] getB() {
            return b;
        }

        public void setB(byte[] b) {
            this.b = b;
        }
    }
}

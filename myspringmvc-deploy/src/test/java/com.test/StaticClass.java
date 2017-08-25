package com.test;

public class StaticClass {
    public static class InnerStaticClass {
        public static int a = 0;
        public int b = 0;

        public static int getA() {
            return a;
        }

        public int getB() {
            return b;
        }
    }
}

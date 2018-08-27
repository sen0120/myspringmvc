package com.test.fuzi;

public class Son extends Father {
    @Override
    public void method1() {
        try {
            super.method1();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

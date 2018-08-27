package com.test.pattern.factory;

public class SingleJiHan2 {
    private static SingleJiHan2 single;//饥汉

    static {
        single = new SingleJiHan2();
    }

    private SingleJiHan2() {
    }

    public static SingleJiHan2 getInstance() {
        return single;
    }

}

package com.test.pattern.factory;

public class SingleJiHan1 {
    private static SingleJiHan1 single = new SingleJiHan1();//饥汉

    private SingleJiHan1() {
    }

    public static SingleJiHan1 getInstance() {
        return single;
    }

}

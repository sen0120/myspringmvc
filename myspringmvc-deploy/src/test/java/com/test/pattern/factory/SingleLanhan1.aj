package com.test.pattern.factory;

public aspect SingleLanhan1 {
    private static SingleLanhan1 single = null;

    private SingleLanhan1() {

    }

    public static SingleLanhan1 newInstance() {
        if (single == null) {
            synchronized (SingleLanhan1.class) {
                single = new SingleLanhan1();
            }
        }
        return single;
    }
}

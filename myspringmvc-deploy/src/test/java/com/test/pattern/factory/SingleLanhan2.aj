package com.test.pattern.factory;

public aspect SingleLanhan2 {
    private static SingleLanhan2 single = null;

    private SingleLanhan2() {

    }

    public synchronized static SingleLanhan2 newInstance() {
        if (single == null) {
            single = new SingleLanhan2();
        }
        return single;
    }
}

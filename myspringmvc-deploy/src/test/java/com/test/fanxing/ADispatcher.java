package com.test.fanxing;

import java.util.ArrayList;
import java.util.List;

public class ADispatcher implements Dispatcher<ObjectA> {
    private List<? extends Handler> list = new ArrayList<>();

    @Override
    public void doDispatch(ObjectA objectA) {
        for (Handler handler : list) {
            handler.handle(objectA);
        }
    }
}

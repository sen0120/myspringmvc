package com.test.concurrent.aqs;

import sun.misc.Unsafe;

public class AQS {
    private Node head;
    private Node tail;
    private int state;
    private static Unsafe unsafe = Unsafe.getUnsafe();
    private static final long waitStatusOffset;

    static {
        try {
            waitStatusOffset = unsafe.objectFieldOffset(AQS.Node.class.getDeclaredField("waitStatus"));
        } catch (NoSuchFieldException e) {
            throw new Error(e);
        }
    }

    class ConditionObject {//等待队列
        Node firstWaiter;
        Node lastWaiter;
    }

    private boolean compareAndSwap(Node node, int expect, int update) {
        return unsafe.compareAndSwapInt(node, waitStatusOffset, expect, update);
    }

    static final class Node {
        Node SHARED = new Node();
        Node exclusive = null;
        int state_cancelled = 1;
        int state_signal = -1;
        int state_condition = -2;
        int state_propagate = -3;
    }
}

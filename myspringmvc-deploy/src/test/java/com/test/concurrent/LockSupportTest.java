package com.test.concurrent;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

// 简易的先进先出非重入锁
class FIFOMutex {
    //
    private final AtomicBoolean locked = new AtomicBoolean(false);
    // 记录等待线程队列
    private final Queue<Thread> waiters = new ConcurrentLinkedQueue<Thread>();

    public void lock() {
        boolean wasInterrupted = false;
        Thread current = Thread.currentThread();
        waiters.add(current);

        // 如果当前线程不是等待线程队列第一个，或者locked状态已经是true，那么当前线程就要等待
        while (waiters.peek() != current || !locked.compareAndSet(false, true)) {
            System.out.println(Thread.currentThread().getName() + "  park start");
            LockSupport.park(this);
            System.out.println(Thread.currentThread().getName() + "  park end");
            // 等待线程的中断线程标志位为true，就设置wasInterrupted为true
            if (Thread.interrupted())
                wasInterrupted = true;
        }

        // 移除第一个元素。当前线程就是第一个元素，因为while判断条件
        waiters.remove();
        // 如果wasInterrupted为true，当前线程发出中断请求
        if (wasInterrupted)
            current.interrupt();
        System.out.println(Thread.currentThread().getName() + " lock end");
    }

    // 唤醒可能等待的线程
    public void unlock() {
        System.out.println(Thread.currentThread().getName() + "  unpark start ");
        // 将locked设置为false
        locked.set(false);
        // 唤醒当前线程队列中第一个元素
        LockSupport.unpark(waiters.peek());
        System.out.println(Thread.currentThread().getName() + "  unpark end ");
    }
}


public class LockSupportTest {

    public static void startThread(String name, final FIFOMutex clock, final CountDownLatch countDownLatch) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                clock.lock();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(Thread.currentThread().getName() + "  finally");
                    countDownLatch.countDown();
                    clock.unlock();
                }
            }
        }, name).start();
    }

    public static void main(String[] args) throws InterruptedException {
        FIFOMutex clock = new FIFOMutex();
        CountDownLatch countDownLatch = new CountDownLatch(3);
        startThread("t111", clock, countDownLatch);
        startThread("t222", clock, countDownLatch);
        startThread("t333", clock, countDownLatch);

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main end");
    }
}
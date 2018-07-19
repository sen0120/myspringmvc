package com.test.concurrent;

import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class MyReentraintLock {
    public static void main(String[] args) throws InterruptedException {
        final ReentrantLock lock = new ReentrantLock();
        LockSupport.unpark(Thread.currentThread());
        LockSupport.park(Thread.currentThread());

        new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.unlock();
            }
        }, "thread1").start();

        Thread.sleep(500L);
        new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                System.out.println(11);
                lock.unlock();
            }
        }, "thread1").start();

    }
}

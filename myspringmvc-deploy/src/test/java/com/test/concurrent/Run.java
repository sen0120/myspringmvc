package com.test.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

public class Run {
    static final CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws InterruptedException {
        final Service service = new Run().new Service(false);  //改为false就为非公平锁了
        Runnable runnable = new Runnable() {
            public void run() {
                System.out.println("**线程： " + Thread.currentThread().getName()
                        + " 运行了 ");
                service.serviceMethod();
            }
        };

        Thread[] threadArray = new Thread[10];

        for (int i = 0; i < 10; i++) {
            threadArray[i] = new Thread(runnable);
        }
        for (int i = 0; i < 10; i++) {
            Thread.sleep(10L);
            threadArray[i].start();
        }
        Thread.sleep(1000L);
        countDownLatch.countDown();
    }

    class Service {
        private ReentrantLock lock;

        public Service(boolean isFair) {
            lock = new ReentrantLock(isFair);
        }

        public void serviceMethod() {
            try {
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.lock();
                System.out.println("ThreadName=" + Thread.currentThread().getName()
                        + " 获得锁定");
            } finally {
                lock.unlock();
            }
        }
    }
}
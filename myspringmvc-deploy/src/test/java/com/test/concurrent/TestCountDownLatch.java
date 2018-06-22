package com.test.concurrent;

import java.util.concurrent.CountDownLatch;

public class TestCountDownLatch {
    static CountDownLatch latch = null;

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    proccess();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    proccess();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public static void proccess() throws InterruptedException {
        latch = new CountDownLatch(1);
        System.out.println(Thread.currentThread().getName() + "start await");
        latch.await();
        System.out.println(Thread.currentThread().getName() + "finish await");
    }
}

package com.test.concurrent;

import java.util.concurrent.CountDownLatch;

public class TestCountDownLatch {
    static CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) throws InterruptedException {
        latch.countDown();
        System.out.println(1);
        latch.await();
        System.out.println(2);
        latch.await();
        System.out.println(3);
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                latch.countDown();


            }
        }).start();

        Thread.sleep(3000L);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    latch.await();
                    System.out.println("finish");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();*/
    }

}

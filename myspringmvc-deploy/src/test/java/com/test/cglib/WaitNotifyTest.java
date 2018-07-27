package com.test.cglib;

public class WaitNotifyTest {
    private static final Object mutx = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (mutx) {
                        mutx.wait();
                        System.out.println(1);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (mutx) {
                        mutx.wait();
                        System.out.println(2);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();

        Thread.sleep(2000L);

        synchronized (mutx) {
//            mutx.notifyAll();
            mutx.notify();
            Thread.sleep(2000L);
            mutx.notify();
        }
    }
}

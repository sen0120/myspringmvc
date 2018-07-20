package com.test.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {
    public static void main(String[] args) throws InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
        System.out.println(cyclicBarrier.getParties());
        System.out.println(cyclicBarrier.getNumberWaiting());

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Thread.sleep(100L);
        System.out.println(cyclicBarrier.getParties());
        System.out.println(cyclicBarrier.getNumberWaiting());

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Thread.sleep(100L);
        System.out.println(cyclicBarrier.getParties());
        System.out.println(cyclicBarrier.getNumberWaiting());

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Thread.sleep(100L);
        System.out.println(cyclicBarrier.getParties());
        System.out.println(cyclicBarrier.getNumberWaiting());

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Thread.sleep(100L);
        System.out.println(cyclicBarrier.getParties());
        System.out.println(cyclicBarrier.getNumberWaiting());

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Thread.sleep(100L);
        System.out.println(cyclicBarrier.getParties());
        System.out.println(cyclicBarrier.getNumberWaiting());


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Thread.sleep(100L);
        System.out.println(cyclicBarrier.getParties());
        System.out.println(cyclicBarrier.getNumberWaiting());


    }
}

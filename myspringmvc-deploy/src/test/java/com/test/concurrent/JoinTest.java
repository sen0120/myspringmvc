package com.test.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

public class JoinTest {
    public static void main(String[] args) {
        final AtomicInteger atomicInteger = new AtomicInteger();
        final long begin = System.currentTimeMillis();
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + e.getMessage());
                }

                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }

                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + e.getMessage());
                    return;
                }

                atomicInteger.incrementAndGet();
            }
        };
        Thread thread = new Thread(runnable);
        Thread thread1 = new Thread(runnable);
        thread.start();
        thread1.start();

//        thread.stop();
//        thread1.stop();


        thread.interrupt();
        thread1.interrupt();

        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

        thread.interrupt();
        thread1.interrupt();


        try {
            thread.join();
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(atomicInteger);

    }
}

package com.test.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MyConditionDemo {
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        MyConditionDemo demo = new MyConditionDemo();
        Thread1 thread1 = demo.new Thread1();
        Thread2 thread2 = demo.new Thread2();
        thread1.start();
        thread2.start();
        try {
            demo.lock.lock();
            System.out.println(1);
            Thread.sleep(2000L);
            demo.condition1.signal();//重新请求锁,但要等unlock后
        } finally {
            demo.lock.unlock();
        }
        Thread.sleep(1000L);
        System.out.println(2);

        try {
            demo.lock.lock();
            System.out.println(3);
            Thread.sleep(2000L);
            demo.condition2.signal();
        } finally {
            demo.lock.unlock();
        }
        Thread.sleep(1000L);
        System.out.println(4);
    }

    class Thread1 extends Thread {
        @Override
        public void run() {
            try {
                lock.lock();
                condition1.await();
                System.out.println("condition1 finish");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    class Thread2 extends Thread {
        @Override
        public void run() {
            try {
                lock.lock();
                condition2.await();//释放锁,加入等待队列
                System.out.println("condition2 finish");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }


}
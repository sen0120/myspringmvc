package com.test.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTime extends Thread {
    static ReentrantLock lock = new ReentrantLock(true);

    public void run() {
        try {
            if (lock.tryLock(5, TimeUnit.SECONDS)) {
                System.out.println("获取成功");
                Thread.sleep(6000L);
            } else {
                System.out.println("获取失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        ReentrantLockTime lock1 = new ReentrantLockTime();
        ReentrantLockTime lock2 = new ReentrantLockTime();

        lock1.start();
        lock2.start();
    }

}
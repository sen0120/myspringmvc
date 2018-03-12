package com.test.concurrent;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockInt implements Runnable {
    static ReentrantLock lock1 = new ReentrantLock();
    static ReentrantLock lock2 = new ReentrantLock();
    int lock;

    public ReentrantLockInt(int lock) {
        this.lock = lock;
    }

    public static void main(String[] args) {
        ReentrantLockInt rli1 = new ReentrantLockInt(1);
        ReentrantLockInt rli2 = new ReentrantLockInt(2);

        Thread thread1 = new Thread(rli1);
        Thread thread2 = new Thread(rli2);

        thread1.start();
        thread2.start();
//        thread1.interrupt();

        DeadlockChecker.check();
    }

    public void run() {
        try {
            if (lock == 1) {
                lock1.lockInterruptibly();
                Thread.sleep(500);
                lock2.lockInterruptibly();
            } else {

                lock2.lockInterruptibly();
                Thread.sleep(500);
                lock1.lockInterruptibly();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (lock1.isHeldByCurrentThread()) {
                lock1.unlock();
            }
            if (lock2.isHeldByCurrentThread()) {
                lock2.unlock();
            }
            System.out.println(Thread.currentThread().getName() + "线程退出");
        }
    }

    /**
     * 检查死锁
     */
    static class DeadlockChecker {
        private final static ThreadMXBean mbean = ManagementFactory.getThreadMXBean();

        final static Runnable deadLockChecker = new Runnable() {

            public void run() {
                while (true) {
                    long[] deadLockedThreadIds = mbean.findDeadlockedThreads();
                    if (deadLockedThreadIds != null) {
                        ThreadInfo[] threadInfos = mbean.getThreadInfo(deadLockedThreadIds);
                        for (Thread t : Thread.getAllStackTraces().keySet()) {
                            for (ThreadInfo ti : threadInfos) {
                                if (ti.getThreadId() == t.getId()) {
                                    t.interrupt();
                                }
                            }
                        }
                    }
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        };

        public static void check() {
            Thread t = new Thread(deadLockChecker);
            t.setDaemon(true);
            t.start();
        }
    }
}
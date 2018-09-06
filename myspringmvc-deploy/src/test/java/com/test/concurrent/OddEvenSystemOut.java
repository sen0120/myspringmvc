package com.test.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class OddEvenSystemOut {
    private static final Logger logger = LoggerFactory.getLogger(OddEvenSystemOut.class);
    private static final Object objLock = new Object();
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();
    private static AtomicBoolean oddThreadRun = new AtomicBoolean(false);
    private static AtomicInteger atomicInteger = new AtomicInteger(1);

    public static void main(String[] args) {
        impl1();
        impl2();
    }

    private static void impl1() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                soutOdd1(atomicInteger);
//                soutOdd2(atomicInteger);
            }
        }, "基数线程").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                soutEven1(atomicInteger);
//                soutEven2(atomicInteger);
            }
        }, "偶数线程").start();
    }

    private static void impl2() {
        new Thread(new Runnable() {
            @Override
            public void run() {
//                soutOdd1(atomicInteger);
                soutOdd2(atomicInteger);
            }
        }, "基数线程").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
//                soutEven1(atomicInteger);
                soutEven2(atomicInteger);
            }
        }, "偶数线程").start();
    }

    private static void soutOdd1(AtomicInteger atomicInteger) {
        while (atomicInteger.get() <= 100) {
            lock.lock();
            try {
                if (!oddThreadRun.get()) {
                    oddThreadRun.set(true);
                }

                if (atomicInteger.get() % 2 == 1) {
                    sout(atomicInteger.getAndIncrement());
                    condition.signal();
                } else {
                    condition.await();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    private static void soutEven1(AtomicInteger atomicInteger) {
        while (atomicInteger.get() <= 100) {
            lock.lock();
            try {
                if (!oddThreadRun.get()) {
                    condition.await();
                }
                if (atomicInteger.get() % 2 == 0) {
                    sout(atomicInteger.getAndIncrement());
                    condition.signal();
                } else {
                    condition.await();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    private static void soutOdd2(AtomicInteger atomicInteger) {
        while (atomicInteger.get() <= 100) {
            synchronized (objLock) {
                if (!oddThreadRun.get()) {
                    oddThreadRun.set(true);
                }

                if (atomicInteger.get() % 2 == 1) {
                    sout(atomicInteger.getAndIncrement());
                    objLock.notify();
                } else {
                    try {
                        objLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static void soutEven2(AtomicInteger atomicInteger) {
        while (atomicInteger.get() <= 100) {
            synchronized (objLock) {
                if (!oddThreadRun.get()) {
                    try {
                        objLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (atomicInteger.get() % 2 == 0) {
                    sout(atomicInteger.getAndIncrement());
                    objLock.notify();
                } else {
                    try {
                        objLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static synchronized void sout(int a) {
        if (a % 2 == 1) {
            logger.info(Thread.currentThread().getName() + "打印" + a);
        } else {
            logger.error(Thread.currentThread().getName() + "打印" + a);
        }

    }

}

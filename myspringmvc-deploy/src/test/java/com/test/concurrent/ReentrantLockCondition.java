package com.test.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockCondition extends Thread {
    static ReentrantLock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();

    public static void main(String[] args) throws Exception {
        ReentrantLockCondition reen = new ReentrantLockCondition();
        reen.start();

        Thread.sleep(2000L);

        lock.lock();

        long signalStart = System.currentTimeMillis();

        condition.signal();//不会释放锁,而是在unlock后才通知其他线程 竞争锁

        lock.unlock();
        System.out.println("main unlock");
    }

    @Override
    public void run() {
        lock.lock();//申请锁
        try {
            System.out.println("thread await");
            condition.await();//释放锁并加入等待队列,当其他线程signal时会重新请求锁,请求到锁后执行完代码后记得释放锁
            System.out.println("thread get lock");
        } catch (InterruptedException e) {

        } finally {
            System.out.println("finally");
            if (lock.isHeldByCurrentThread()) {
                System.out.println("unlock");
                lock.unlock();//释放锁
            }
        }
    }
}
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

        condition.signal();
        Thread.sleep(50000L);

        lock.unlock();
    }

    @Override
    public void run() {
        lock.lock();//申请锁
        try {
            condition.await();//释放锁并加入等待队列,当其他线程signal时会重新请求锁,请求到锁后执行完代码后记得释放锁

            System.out.println(1);
        } catch (InterruptedException e) {

        } finally {
            System.out.println("finally");
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();//释放锁
            }
        }
    }
}
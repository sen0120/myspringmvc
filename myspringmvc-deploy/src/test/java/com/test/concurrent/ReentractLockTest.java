package com.test.concurrent;

import org.apache.commons.lang3.time.StopWatch;

import java.util.concurrent.locks.ReentrantLock;

public class ReentractLockTest implements Runnable {
    ReentrantLock lock = new ReentrantLock();
    int j = 0;

    public void run() {
        lock.lock();
        try {
            for (int i = 0; i < 10; i++) {
                Thread.sleep(200L);
                j++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
//            System.out.println(j);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentractLockTest rl = new ReentractLockTest();
        Thread thread1 = new Thread(rl);
        Thread thread2 = new Thread(rl);

//        thread2.setDaemon(true);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        thread1.start();
        thread2.start();


//        Thread.sleep(2000L);
//        System.out.println("thread1.isAlive()" + thread1.isAlive());
//        System.out.println("thread2.isAlive()" + thread2.isAlive());

//        thread1.join();//1同步线程1如果没有join同步,那么线程1和线程2还没执行完3就立马打印rl.j的值,而我们希望看到线程1和线程2执行完后再打印rl.j的最终的值,因此需要对线程1和2进行等待同步操作
//        thread2.join();//2同步线程2

        stopWatch.stop();
        System.out.println("stopWatch.getTime():" + stopWatch.getTime());
        System.out.println(rl.j);//3打印j的值

    }
}

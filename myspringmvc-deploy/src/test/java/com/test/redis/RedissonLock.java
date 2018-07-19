package com.test.redis;

import org.redisson.Redisson;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RFuture;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * lock(),redisson使用hash数据类型实现锁,key field value,key为锁的名称,field为uuid+线程id,value为锁的计数器(每加一次锁+1,没释放一次锁-1,当value为0时删除field,当expire过期是删除field),
 * 每个客户端生成一个唯一uuid,作为区分客户端的标识,同一客户端不同线程通过线程id区分,因此唯一标识是uuid+线程id.
 * <p>
 * lockAsync()是异步拿锁,返回对象RFuture..isFinish()RFuture.get()方法阻塞获取结果
 */
public class RedissonLock {
    private static final String LOCK_TITLE = "redisLock_";
    private static final String RAtomicName = "genId_";

    private static Config config = new Config();
    private static RedissonClient redisson = null;

    public static void main(String[] args) throws Exception {
        init();
//        lock1.lock(10, TimeUnit.SECONDS);
        /*RFuture<Void> voidRFuture0 = lock1.lockAsync();
        RFuture<Void> voidRFuture = lock1.lockAsync(15, TimeUnit.SECONDS);
        while (!voidRFuture.isSuccess()) {

        }
        System.out.println(voidRFuture.isSuccess());*/
//        lock1.lock(1, TimeUnit.MINUTES);
//        testAsyncReentrantLock();
//        testlock();
//        testAsyncLock();

    }

    private static void testAsyncLock() throws InterruptedException {
        RLock lock = redisson.getLock("anyLock");

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                RFuture<Boolean> voidRFuture = lock.tryLockAsync(1, 1, TimeUnit.MINUTES);
                System.err.println("mythread1 lockAsync");
                try {
                    Thread.sleep(5000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.unlock();
                System.err.println("mythread1 unlock");
            }
        }, "thread1");
        thread1.start();

        Thread.sleep(100L);
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                RFuture<Boolean> voidRFuture = lock.tryLockAsync(1, 1, TimeUnit.MINUTES);
                System.err.println("mythread2 lockAsync");
                try {
                    voidRFuture.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                lock.unlock();
                System.err.println("mythread2 unlock");
            }
        }, "thread2");

        thread2.start();
    }

    private static void testlock() throws InterruptedException {
        final String lock1 = "lock1";
       /* acquire(lock1);
        System.err.println("lock executing");
        release(lock1);*/
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.err.println("Thread 1 acquire lock start 1");
                acquire(lock1);
                System.err.println("Thread 1 acquire lock success 1");
                /*try {
                    Thread.sleep(3000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*//*
                System.err.println("Thread 1 acquire lock start 2");
                acquire(lock1);
                System.err.println("Thread 1 acquire lock success 2");
                try {
                    Thread.sleep(40000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                release(lock1);
                System.err.println("Thread 1 release lock success 1");
                release(lock1);
                System.err.println("Thread 1 release lock success 2");*/
            }
        }, "thread1");
        thread1.start();

        /*Thread.sleep(1000L);
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
//                release(lock1);
//                System.err.println("!Thread 2 release lock success");
//                release(lock1);
//                System.err.println("!Thread 2 release lock success");
                try {
                    Thread.sleep(60000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.err.println("Thread 2 acquire lock start");
                acquire(lock1);
                System.err.println("Thread 2 acquire lock success");
                release(lock1);
                System.err.println("Thread 2 release lock success");
            }
        }, "thread2");
        thread2.start();*/
        thread1.join();
//        thread2.join();
//        redisson.getBucket()

        redisson.shutdown();
    }

    public static void init() {
        try {
/*            config.useClusterServers() //这是用的集群server
                    .setScanInterval(2000) //设置集群状态扫描时间
                    .setMasterConnectionPoolSize(10000) //设置连接数
                    .setSlaveConnectionPoolSize(10000)
                    .addNodeAddress("redis://127.0.0.1:30001");
            redisson = Redisson.create(config);*/
            config.useClusterServers().addNodeAddress(
                    "redis://127.0.0.1:7001",
                    "redis://127.0.0.1:7002",
                    "redis://127.0.0.1:7003",
                    "redis://127.0.0.1:7004",
                    "redis://127.0.0.1:7005");
            redisson = Redisson.create(config);
            //清空自增的ID数字
            RAtomicLong atomicLong = redisson.getAtomicLong(RAtomicName);
            atomicLong.set(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void acquire(String lockName) {
//        String key = LOCK_TITLE + lockName;
        RLock mylock = redisson.getLock(lockName);
//        mylock.lock(1, TimeUnit.MINUTES); //lock提供带timeout参数，timeout结束强制解锁，防止死锁
        try {
            mylock.tryLock(30, 30, TimeUnit.SECONDS); //lock提供带timeout参数，timeout结束强制解锁，防止死锁
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.err.println("======lock======" + Thread.currentThread().getName());
    }

    public static void release(String lockName) {
//        String key = LOCK_TITLE + lockName;
        RLock mylock = redisson.getLock(lockName);
        mylock.unlock();
        System.err.println("======unlock======" + Thread.currentThread().getName());
    }
}

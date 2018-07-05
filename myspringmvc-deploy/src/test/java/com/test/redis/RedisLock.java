package com.test.redis;

import org.redisson.Redisson;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

public class RedisLock {
    private static final String LOCK_TITLE = "redisLock_";
    private static final String RAtomicName = "genId_";

    private static Config config = new Config();
    private static RedissonClient redisson = null;

    public static void main(String[] args) {
        init();
        final String lock1 = "lock1";
        acquire(lock1);
        System.out.println("lock executing");
        release(lock1);
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread 2 acquire lock start 1");
                acquire(lock1);
                System.out.println("Thread 2 acquire lock success 1");
                System.out.println("Thread 2 acquire lock start 2");
                acquire(lock1);
                System.out.println("Thread 2 acquire lock success 2");
                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                release(lock1);
                System.out.println("Thread 2 release lock success 1");
                release(lock1);
                System.out.println("Thread 2 release lock success 2");
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread 2 acquire lock start");
                acquire(lock1);
                System.out.println("Thread 2 acquire lock success");
                release(lock1);
                System.out.println("Thread 2 release lock success");
            }
        }).start();*/
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
            redisson = Redisson.create();
            //清空自增的ID数字
            RAtomicLong atomicLong = redisson.getAtomicLong(RAtomicName);
            atomicLong.set(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void acquire(String lockName) {
        String key = LOCK_TITLE + lockName;
        RLock mylock = redisson.getLock(key);
        mylock.lock(2, TimeUnit.MINUTES); //lock提供带timeout参数，timeout结束强制解锁，防止死锁
        System.err.println("======lock======" + Thread.currentThread().getName());
    }

    public static void release(String lockName) {
        String key = LOCK_TITLE + lockName;
        RLock mylock = redisson.getLock(key);
        mylock.unlock();
        System.err.println("======unlock======" + Thread.currentThread().getName());
    }
}

package com.test.redis;

import com.google.common.hash.BloomFilter;
import org.redisson.Redisson;
import org.redisson.RedissonMultiLock;
import org.redisson.RedissonRedLock;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RCountDownLatch;
import org.redisson.api.RFuture;
import org.redisson.api.RLock;
import org.redisson.api.RPermitExpirableSemaphore;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 0独占/公平锁/可中断/可限时/读写锁/可过期可续约;
 * 1lock(),redisson使用hash数据类型实现锁,key field value,key为锁的名称,field为uuid+线程id,value为锁的计数器(每加一次锁+1,没释放一次锁-1,当value为0时删除field,当expire过期是删除field),
 * 每个客户端生成一个唯一uuid,作为区分客户端的标识,同一客户端不同线程通过线程id区分,因此唯一标识是uuid+线程id.
 * <p>
 * 2lock()
 * <p>
 * 3lockAsync()是异步拿锁,返回对象RFuture..isFinish()RFuture.get()方法阻塞获取结果.
 * <p/>
 * 4红锁,new RedissonRedLock(lock1,lock2,lock3)尝试多个锁多个redissionClient实例,获得半以上数锁算成功.
 * <p/>
 * 5联锁（MultiLock）,new RedissonMultiLock(lock1, lock2, lock3),必须所有锁都获得才算成功
 * <p/>
 * 6 读写锁（RReadWriteLock）,通concurrent包中的读写锁,redisson.getReadWriteLock("anyRWLock");
 * <p/>
 * 7信号量 RSemaphore,redisson.getSemaphore("semaphore1");
 * <p/>
 * 8可过期信号量 RPermitExpirableSemaphore,在RSemaphore的基础上加了过期时间
 * <p/>
 * 9计数器RCountDownLatch
 */
public class RedissonLock {
    private static final String LOCK_TITLE = "redisLock_";
    private static final String RAtomicName = "genId_";

    private static Config config = new Config();
    private static RedissonClient redisson = null;

    public static void main(String[] args) throws Exception {
        init();

       /* RBloomFilter<Object> getBloomFilter = redisson.getBloomFilter("getBloomFilter5");
        boolean b = getBloomFilter.tryInit(4, 0.5d);
        boolean add = getBloomFilter.add(1);
        boolean add1 = getBloomFilter.add(2);
        boolean add2 = getBloomFilter.add(3);
        boolean add3 = getBloomFilter.add(4);
        long count = getBloomFilter.count();
        boolean contains = getBloomFilter.contains(1);
        long expectedInsertions = getBloomFilter.getExpectedInsertions();
        double falseProbability = getBloomFilter.getFalseProbability();
        int hashIterations = getBloomFilter.getHashIterations();
        long size = getBloomFilter.getSize();
        System.out.println(1);*/
        /*
        RLock fair = redisson.getFairLock("fair");
        fair.lock();
        RSemaphore semaphore = redisson.getSemaphore("semaphore");
        semaphore.acquire();

        RPermitExpirableSemaphore rPermitExpirableSemaphore = redisson.getPermitExpirableSemaphore("含过期时间的信号量");
        rPermitExpirableSemaphore.acquire(1, TimeUnit.MINUTES);

        RCountDownLatch countDownLatch = redisson.getCountDownLatch("countDownLatch");
        countDownLatch.countDown();
        countDownLatch.await();*/
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
//        testRedLock(redisson, null, null);

    }

    public static void readwriteLock() throws InterruptedException {
        RReadWriteLock rwlock = redisson.getReadWriteLock("anyRWLock");
        RLock readLock = rwlock.readLock();
        RLock writeLock = rwlock.writeLock();
        readLock.lock(10, TimeUnit.SECONDS);
        writeLock.lock(10, TimeUnit.SECONDS);
        // 尝试加锁，最多等待100秒，上锁以后10秒自动解锁
        boolean readLcokSuccess = readLock.tryLock(100, 10, TimeUnit.SECONDS);
        boolean writeLockSuccess = writeLock.tryLock(100, 10, TimeUnit.SECONDS);
        readLock.unlock();
        writeLock.unlock();
    }

    public static void testRedLock(RedissonClient redisson1, RedissonClient redisson2, RedissonClient redisson3) throws InterruptedException {
        RLock lock1 = redisson1.getLock("lock1");
        RLock lock2 = redisson2.getLock("lock2");
        RLock lock3 = redisson3.getLock("lock3");
        RedissonRedLock lock = new RedissonRedLock(lock1, lock2, lock3);

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 同时加锁：lock1 lock2 lock3, 红锁在大部分节点上加锁成功就算成功。
//            lock.lock();
                    // 尝试加锁，最多等待100秒，上锁以后10秒自动解锁
                    boolean res = lock.tryLock(10, 60 * 2, TimeUnit.SECONDS);
                    Thread.sleep(60000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        });
        thread1.start();

        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 同时加锁：lock1 lock2 lock3, 红锁在大部分节点上加锁成功就算成功。
//            lock.lock();
                    // 尝试加锁，最多等待100秒，上锁以后10秒自动解锁
                    boolean res = lock.tryLock(10, 60 * 2, TimeUnit.SECONDS);
                    Thread.sleep(100000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        });
        thread2.start();
        thread1.join();
        thread2.join();
        redisson.shutdown();

    }

    /**
     * 联锁（MultiLock）
     *
     * @param redisson1
     * @param redisson2
     * @param redisson3
     */
    public static void testMultiLock(RedissonClient redisson1, RedissonClient redisson2, RedissonClient redisson3) {
        RLock lock1 = redisson1.getLock("lock1");
        RLock lock2 = redisson2.getLock("lock2");
        RLock lock3 = redisson3.getLock("lock3");
        RedissonMultiLock lock = new RedissonMultiLock(lock1, lock2, lock3);
        try {
            // 同时加锁：lock1 lock2 lock3, 所有的锁都上锁成功才算成功。
//            lock.lock();
            // 尝试加锁，最多等待100秒，上锁以后10秒自动解锁
            boolean res = lock.tryLock(100, 10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
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
        final String lock1 = "mylock4";
       /* acquire(lock1);
        System.err.println("lock executing");
        release(lock1);*/
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.err.println("myThread 1 acquire lock befor 1");
                acquire(lock1);
                System.err.println("myThread 1 acquire lock end 1");
                try {
                    Thread.sleep(60000000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                /*System.err.println("myThread 1 acquire lock befor 2");
                acquire(lock1);
                System.err.println("myThread 1 acquire lock end 2");
                try {
                    Thread.sleep(40000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                release(lock1);
                System.err.println("myThread 1 release lock end 1");*/
                release(lock1);
                System.err.println("myThread 1 release lock end 2");
            }
        }, "thread1");

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
//                release(lock1);
//                System.err.println("!Thread 2 release lock befor");
//                release(lock1);
//                System.err.println("!Thread 2 release lock end");
                /*try {
                    Thread.sleep(60000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                System.err.println("myThread 2 acquire lock befor");
                acquire(lock1);
                System.err.println("myThread 2 acquire lock end");
                release(lock1);
                System.err.println("myThread 2 release lock end");
            }
        }, "thread2");

        thread1.start();
//        Thread.sleep(1000L);
//        thread2.start();
//        Thread.sleep(1000L);

        Thread.sleep(5000L);
//        thread1.interrupt();

        thread1.join();
//        thread2.join();

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
        mylock.lock();
//        mylock.lock(1, TimeUnit.MINUTES); //lock提供带timeout参数，timeout结束强制解锁，防止死锁
        /*try {
            mylock.tryLock(3, 60, TimeUnit.SECONDS); //lock提供带timeout参数，timeout结束强制解锁，防止死锁
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        System.err.println("======lock======" + Thread.currentThread().getName());
    }

    public static void release(String lockName) {
//        String key = LOCK_TITLE + lockName;
        RLock mylock = redisson.getLock(lockName);
        mylock.unlock();
        System.err.println("======unlock======" + Thread.currentThread().getName());
    }
}

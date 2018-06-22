package com.test.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ThreadPoolExecutorTest {
    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolExecutorTest.class);

    public static void main1(String[] args) throws Exception {
        final AtomicInteger threadNumbers = new AtomicInteger(10);
        final ConcurrentHashMap<Integer, String> result = new ConcurrentHashMap<>();
        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "" + threadNumbers.incrementAndGet());
            }
        };

        final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 4, 0L, TimeUnit.MINUTES,
//                new SynchronousQueue<Runnable>(), threadFactory, new ThreadPoolExecutor.CallerRunsPolicy());
                new LinkedBlockingQueue<Runnable>(95), new ThreadPoolExecutor.CallerRunsPolicy());

        final BlockingQueue<Integer> blockingQueue = new LinkedBlockingQueue<>();

        for (int i = 1; i <= 100; i++) {
            blockingQueue.put(i);
        }

        /*threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                result.put(1, Thread.currentThread().getName());
            }
        });

        Thread.sleep(2000L);
        System.out.println(result);

        if (1 == 1) return;*/

        /*final AtomicInteger atomicInteger = new AtomicInteger(4);

        for (int i = 0; i < 4; i++) {
            threadPoolExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (blockingQueue.size() != 0) {
                            final Integer num = blockingQueue.take();
                            result.put(num, Thread.currentThread().getName());
                            Thread.sleep(100L);
                            System.out.println(Thread.currentThread().getName() + "execute" + num);
                        }
                        atomicInteger.decrementAndGet();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        while (true) {
            if (atomicInteger.get() == 0) {
                break;
            }
        }

        System.out.println(result);

        Set<Map.Entry<Integer, String>> entries = result.entrySet();
        Iterator<Map.Entry<Integer, String>> iterator = entries.iterator();

        Map<String, Integer> map = new Hashtable<>();
        while (iterator.hasNext()) {
            Map.Entry<Integer, String> next = iterator.next();
            String value = next.getValue();
            if (map.containsKey(value)) {
                map.put(value, map.get(value).intValue() + 1);
            } else {
                map.put(value, 1);
            }
        }

        System.out.println(11);

        System.out.println(threadPoolExecutor.getTaskCount());
        System.out.println(threadPoolExecutor.getActiveCount());
        System.out.println(threadPoolExecutor.getCompletedTaskCount());
        System.out.println(Thread.currentThread().getName());
        System.out.println(map);


        if (1 == 1) return;*/
        final List<Future> futureList = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (blockingQueue.size() != 0) {
                    final AtomicInteger num = new AtomicInteger();
                    try {
                        num.addAndGet(blockingQueue.take());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//                        Future<?> future = threadPoolExecutor.submit(new Runnable() {
                    threadPoolExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                                @Override
                                public void uncaughtException(Thread t, Throwable e) {
                                    System.err.println(11111);
                                }
                            });

                            if (1 == 1) throw new IllegalArgumentException("asdasd");
                            try {
                                if (Thread.currentThread().getName().equals("qwe1")) {
                                    Thread.sleep(100L);
                                } else {
                                    Thread.sleep(10000L);
                                }

                            } catch (Exception e) {
                                System.out.println(Thread.currentThread().getId() + Thread.currentThread().getName() + e.getStackTrace());
                            }
                            result.put(num.get(), Thread.currentThread().getName());
                            System.out.println(Thread.currentThread().getName() + "execute" + num);
                        }
                    });
//                        futureList.add(future);

                }
                threadPoolExecutor.shutdown();

            }
        }, "qwe").start();

        threadPoolExecutor.awaitTermination(1, TimeUnit.HOURS);

        for (Future future : futureList) {
            Object obj = null;
            try {
                obj = future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            System.out.println(obj);
        }


        Set<Map.Entry<Integer, String>> entries = result.entrySet();
        Iterator<Map.Entry<Integer, String>> iterator = entries.iterator();

        Map<String, Integer> map = new Hashtable<>();
        while (iterator.hasNext()) {
            Map.Entry<Integer, String> next = iterator.next();
            String value = next.getValue();
            if (map.containsKey(value)) {
                map.put(value, map.get(value).intValue() + 1);
            } else {
                map.put(value, 1);
            }
        }

        System.out.println(threadPoolExecutor.getTaskCount());
        System.out.println(threadPoolExecutor.getActiveCount());
        System.out.println(threadPoolExecutor.getCompletedTaskCount());
//        System.out.println(Thread.currentThread().getName());

        System.out.println(result);
        System.out.println(map);

    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        final LinkedBlockingQueue<String> strings = new LinkedBlockingQueue<>();
        strings.add("1");
        strings.add("2");
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                executeSql(new AtomicLong(), new AtomicBoolean(), strings);
            }
        });
        System.out.println(11);
    }


    private static void executeSql(AtomicLong successCount, AtomicBoolean readFileFinish, BlockingQueue<String> insertQueue) {
        try {
            String insertSql = null;
            while (true) {
                boolean allFinish = readFileFinish.get() && insertQueue.isEmpty();////文件读取完 且 队列数据处理完,执行完毕
                if (allFinish) break;

                    /*
                    //并发下使用isEmpty和size两个方法会出现 不可重复读 ,1种解决方式是加锁：synchronize、lock等,2是直接取出数据,根据数据是否为空判断是否执行下一步操作,类似乐观锁;
                    //生产者会关闭,不使用阻塞方法take()    防止当前线程永久阻塞
                     */
                insertSql = insertQueue.poll();
                if (insertSql != null) {
//                    successCount.addAndGet(jdbcTemplate.update(insertSql));//执行sql并记录成功数
                    throw new IllegalArgumentException(insertSql);
                }
            }
        } catch (Exception e) {
            logger.error(String.format("successCount=%s,readFileFinish=%s,insertQueue=%s", successCount, readFileFinish, insertQueue), e);
        }
    }

}

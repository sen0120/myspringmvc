package com.test.guava.xianliu;

import com.google.common.util.concurrent.RateLimiter;
import org.apache.commons.lang3.time.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by wuwf on 17/7/11.
 * 有很多个任务，但希望每秒不超过X个，可用此类
 */
public class RateLimiterTest {
    public static void main(String[] args) throws InterruptedException {
        RateLimiter rateLimiter = RateLimiter.create(10);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        while (true) {
            if (stopWatch.getTime() > 3000)
                break;
        }
        int countLarge = 0;
        int countSmall = 0;
        List<Double> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            double acquire = rateLimiter.acquire();
            list.add(acquire);
            if (acquire > 0.08) {
                countLarge++;
            } else {
                countSmall++;
            }

        }
        System.out.println(countLarge);
        System.out.println(countSmall);
    }

    public static void main2(String[] args) throws Exception {
        //0.5代表一秒最多多少个,且最大容量为0.5
        RateLimiter rateLimiter = RateLimiter.create(0.5);
        List<Runnable> tasks = new ArrayList<Runnable>();
        for (int i = 0; i < 5; i++) {
            tasks.add(new UserRequest(i));
        }
        ExecutorService threadPool = Executors.newFixedThreadPool(1);

        /*long beginTime = System.currentTimeMillis();
        while (true) {
            if (System.currentTimeMillis() - beginTime > 5000L) {
                break;
            }
        }*/
        Thread.sleep(3000L);


        for (Runnable runnable : tasks) {
            System.out.println("等待时间：" + rateLimiter.acquire());
            threadPool.execute(runnable);
        }

        /*for (Runnable runnable : tasks) {
            //判断能否在1秒内得到令牌，如果不能则立即返回false，不会阻塞程序
            if (!rateLimiter.tryAcquire(1000, TimeUnit.MILLISECONDS)) {
                System.out.println(((UserRequest) runnable).getId() + "短期无法获取令牌，真不幸，排队也瞎排");
                Thread.sleep(1000L);
            } else {
                threadPool.execute(runnable);
            }
        }
*/
        threadPool.shutdown();
//        List<Runnable> runnables = threadPool.shutdownNow();
//        System.out.println(runnables);

    }

    public static void main1(String[] args) {
        ThreadFactory threadFactory = new ThreadFactory() {
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        };
        new ThreadPoolExecutor(4, 8, 3, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>(100),
                new ThreadPoolExecutor.CallerRunsPolicy());

    }

    private static class UserRequest implements Runnable {
        private int id;

        public UserRequest(int id) {
            this.id = id;
        }

        public void run() {
            System.out.println(id);
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public int getId() {
            return id;
        }
    }

}  
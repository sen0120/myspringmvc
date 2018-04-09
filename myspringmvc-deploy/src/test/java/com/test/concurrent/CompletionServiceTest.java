package com.test.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

public class CompletionServiceTest {
    public static void main(String[] args) {
        ExecutorService executor = new ThreadPoolExecutor(3, 3, 3, TimeUnit.MINUTES,
                new ArrayBlockingQueue<Runnable>(3), new ThreadPoolExecutor.CallerRunsPolicy());
        CompletionService completionService = new ExecutorCompletionService(executor);
        final AtomicInteger atomicInteger = new AtomicInteger();
        List<Future> list = new ArrayList();
        for (int j = 0; j < 3; j++) {
            Future future = completionService.submit(new Callable() {
                @Override
                public Object call() throws Exception {
                    Thread.sleep(10000L);
                    if(1==1)throw new IllegalArgumentException("sdf");
                    return atomicInteger.incrementAndGet();
                }
            });
            list.add(future);
        }

        Future future = list.get(0);

        try {
            Object o = future.get(1, TimeUnit.SECONDS);
            System.out.println(o);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        System.out.println(future.isDone());
        System.out.println("end");

        executor.shutdown();

    }
}

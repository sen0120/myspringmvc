package com.test.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 模拟1个线程先被notiry,后线程执行wait,看线程是否被唤醒
 */
public class WaitNotifyTest {
    private int sleepTime;
    private Thread thread;

    public static void main(String[] args) throws InterruptedException {
        List list = new ArrayList();
        Thread consumer = new Consumer(list);
//        Thread thread2 = new WaitNotifyTest().new Thread2(list);
        Thread producer = new Producer1(list);

        producer.start();

        Thread.sleep(2000L);

        consumer.start();

    }

    public static class Producer1 extends Thread {
        private List list;
        int i = 0;

        public Producer1(List list) {
            this.list = list;
        }

        public synchronized void run() {
            while (true) {
                try {
                    Thread.sleep(200L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (list) {
                    try {
                        while (list.size() >= 10) {
                            System.out.println("队列满了,等待消费者消费");
                            list.wait();
                        }
                        i++;
                        System.out.println("生产了:" + i);
                        list.add(i);
                        list.notifyAll();
                        Thread.yield();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static class Consumer extends Thread {
        private List list;

        public Consumer(List list) {
            this.list = list;
        }

        public void run() {
            while (true) {
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (list) {
                    try {
                        while (list.size() == 0) {
                            System.out.println("队列为空,等待生产者生产");
                            list.wait();
                        }
//                            Thread.sleep(1000L);
                        System.out.println("消费了:" + list.remove(0));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    list.notifyAll();
                }
            }
        }
    }

}


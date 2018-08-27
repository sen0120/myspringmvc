package com.test.concurrent;

import java.util.Hashtable;

public class ThreadLocalTest {
    private static final ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
    private static final ThreadLocal<Integer> threadLocal2 = new ThreadLocal<>();

    public static void main(String[] args) {
        Long a = 1000L;
        Long b = 1000L;
        Long c = 100L;
        Long d = 100L;
        long e = 1000;
        long f = 100;
        long g = 100;
        boolean x = (a == e);
        boolean y = (c == f);
        boolean aEqualse = a.equals(e);
        boolean aEqualsB = a.equals(b);
        System.out.println(1);
    }

    public static void main3(String[] args) {
//        Integer a = 1000, b = 1000, c = 100, d = 100;
        Integer a = 1000;
        Integer b = 1000;
        Integer c = 100;
        Integer d = 100;
        int e = 1000;
        int f = 100;
        int g = 100;
        boolean x = (a == e);
        boolean y = (c == f);
        boolean aEqualse = a.equals(e);
        boolean aEqualsB = a.equals(b);
        System.out.println(1);
    }

    public static void main2(String[] args) throws InterruptedException {
        Hashtable<Object, Object> hashtable = new Hashtable<>();
        hashtable.put("1", "2");
        System.out.println(1);
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                threadLocal.set(11);
                threadLocal2.set(22);
                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "thread1");

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                threadLocal.set(21);
                threadLocal2.set(22);
                threadLocal.remove();
                threadLocal2.remove();
            }
        }, "thread2");

        thread1.start();
        thread2.start();
    }
}

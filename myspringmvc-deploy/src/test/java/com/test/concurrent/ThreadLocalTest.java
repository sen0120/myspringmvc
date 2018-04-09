package com.test.concurrent;

public class ThreadLocalTest {
    private static final ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        threadLocal.set(1);
        Integer integer = threadLocal.get();
        System.out.println(integer);

        threadLocal.remove();
        threadLocal.remove();

        System.out.println(threadLocal.get());
    }
}

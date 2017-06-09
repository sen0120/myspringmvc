package com.hus.biz.test;

class MyThread extends Thread {

    public void run() {
//        test1();
//        test2();
//        test3();
    }

    public static void test1() {
        System.out.println("test开始..");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("test结束..");
    }

    public synchronized static void test2() {
        System.out.println("test开始..");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("test结束..");
    }

    private static final String aab = "123";

    public static void test3() {
        synchronized (aab) {
            System.out.println("test开始..");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("test结束..");
        }
    }
}

class Stringclass {
    public static String QWE = null;

    private Stringclass() {
    }

    public static Stringclass newInstance(String inString) {
        QWE = inString;
        System.out.println(QWE);
        return new Stringclass();
    }
}

public class TestSynchronized {

    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            Thread thread = new MyThread();
            thread.start();

            Stringclass.newInstance(i + "");
        }
        System.out.println(Stringclass.QWE);
    }

}

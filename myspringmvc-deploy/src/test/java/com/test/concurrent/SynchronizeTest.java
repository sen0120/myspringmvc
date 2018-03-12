package com.test.concurrent;

public class SynchronizeTest {
    public static void main(String[] args) throws Exception {

        final SynchronizeClass1 synchronizeClass1 = new SynchronizeClass1();
        final SynchronizeClass1 synchronizeClass2 = new SynchronizeClass1();
        //synchronize需要对象锁或者类锁,如果是非静态sync方法则锁的是当前对象,如果是static sync方法,锁的则是这个类,那么这个类的所有new对象在多线程内执行方法时 都会申请类锁而的进入等待队列;

        Thread thread1 = new Thread(new Runnable() {
            public void run() {
                try {
                    synchronizeClass1.sout1();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            public void run() {
                try {
                    synchronizeClass2.sout2();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread3 = new Thread(new Runnable() {
            public void run() {
                try {
                    SynchronizeClass2.sout3();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread4 = new Thread(new Runnable() {
            public void run() {
                try {
                    new SynchronizeClass2().sout4();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


//        thread1.start();
//        thread2.start();
        thread3.start();
        thread4.start();

    }

    public static class SynchronizeClass1 {
        public synchronized void sout1() throws InterruptedException {
            System.out.println("sout1 start");
            Thread.sleep(5000L);
            System.out.println("sout1 finish");
        }

        public synchronized void sout2() throws InterruptedException {
            System.out.println("sout2 start");
            Thread.sleep(5000L);
            System.out.println("sout2 finish");
        }
    }

    public static class SynchronizeClass2 {
        public static synchronized void sout3() throws InterruptedException {
            System.out.println("sout3 start");
            Thread.sleep(5000L);
            System.out.println("sout3 finish");
        }

        public /*static*/ synchronized void sout4() throws InterruptedException {
            System.out.println("sout4 start");
            Thread.sleep(5000L);
            System.out.println("sout3 finish");
        }
    }
}


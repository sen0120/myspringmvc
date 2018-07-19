package com.test;

import java.util.concurrent.atomic.AtomicReference;


public class test3 {
    public static void main(String[] args) {

        //在这里使用AtomicReference
        final AtomicReference<Integer> money = new AtomicReference<Integer>();

        // 初始卡余额小于20
        money.set(19);

        // 模拟多个线程更新数据库，为用户充值
        for (int i = 0; i < 3; i++) {
            new Thread() {
                public void run() {
                    while (true) {
                        while (true) {
                            Integer m = money.get();
                            if (m < 20) {
                                if (money.compareAndSet(m, m + 20)) {
                                    System.out.println("余额小于20，充值成功。余额："
                                            + money.get() + "元");
                                    break;
                                }
                            } else {
                                System.out.println("余额大于20，无需充值！");
                                break;
                            }
                        }
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        }

        // 用户消费进程，模拟消费行为
        new Thread() {
            @Override
            public void run() {
                //在这里的for循环，太快很容易看不到结果
                for (int i = 0; i < 1000; i++) {
                    while (true) {
                        Integer m = money.get();
                        if (m > 10) {
                            System.out.println("大于10元");
                            if (money.compareAndSet(m, m - 10)) {
                                System.out.println("成功消费10，卡余额：" + money.get());
                                break;
                            }
                        } else {
                            System.out.println("余额不足！");
                            break;
                        }
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }


        }.start();
    }
}
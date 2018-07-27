package com.test.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.math.BigDecimal;

public class ZoopeeperBarrier {
    public static void main(String[] args) {
        int size = 10;
        ZoopeeperBarrier zoopeeperBarrier = new ZoopeeperBarrier(size);
        for (int i = 0; i < size; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    zoopeeperBarrier.await();
                }
            }, "mythread" + i).start();
        }
    }

    public ZoopeeperBarrier(int size) {
        this.size = size;
    }

    private static int size;
    private static String rootPath = "/myRootPatha";
    final private Object mutx = new Object();

    public void await() {
        try {
            ZooKeeper zk = new ZooKeeper("192.168.92.3:2181", 120000, null);
            //创建根节点
            synchronized (this) {
                Stat stat = zk.exists(rootPath, false);
                if (stat == null) {
                    zk.create(rootPath, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                }
            }

            //创建临时节点
            String threadNodePath = rootPath + "/" + Thread.currentThread().getName();
            int sleep = new BigDecimal(Math.random()).multiply(new BigDecimal(5000)).intValue();
            Thread.sleep(sleep);
            zk.create(threadNodePath, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);


            int waitSize = zk.getChildren(rootPath, false).size();
            if (waitSize != ZoopeeperBarrier.size) {
                try {
                    System.out.println(Thread.currentThread().getName() + " wait,need wait " + (ZoopeeperBarrier.size - waitSize) + " thread");
                    synchronized (mutx) {
                        mutx.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                synchronized (mutx) {
                    mutx.notifyAll();
                }
            }
            System.out.println(Thread.currentThread().getName() + " continue");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

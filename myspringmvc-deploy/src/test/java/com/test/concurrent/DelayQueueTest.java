package com.test.concurrent;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayQueueTest {
    public static void main(String[] args) throws InterruptedException {
        DelayQueue<DelayedEle> delayQueue = new DelayQueue<DelayedEle>();

        long begin = System.currentTimeMillis();

        delayQueue.offer(new DelayedEle(1000, "1000"));
        delayQueue.offer(new DelayedEle(2000, "2000"));
        delayQueue.offer(new DelayedEle(3000, "3000"));
        delayQueue.offer(new DelayedEle(4000, "4000"));
        delayQueue.offer(new DelayedEle(5000, "5000"));

        System.out.println(System.currentTimeMillis() - begin);
        System.out.println(delayQueue.take());
        System.out.println(System.currentTimeMillis() - begin);
        System.out.println(delayQueue.take());
        System.out.println(System.currentTimeMillis() - begin);
        System.out.println(delayQueue.take());
        System.out.println(System.currentTimeMillis() - begin);
        System.out.println(delayQueue.take());
        System.out.println(System.currentTimeMillis() - begin);
        System.out.println(delayQueue.take());
        System.out.println(System.currentTimeMillis() - begin);


    }

    public static class DelayedEle implements Delayed {

        private final long delayTime; //延迟时间
        private final long expire;  //到期时间
        private String data;   //数据

        public DelayedEle(long delay, String data) {
            delayTime = delay;
            this.data = data;
            expire = System.currentTimeMillis() + delay;
        }

        /**
         * 剩余时间=到期时间-当前时间
         */
        @Override
        public long getDelay(TimeUnit unit) {
//            System.out.println(unit.convert(this.expire - System.currentTimeMillis(), TimeUnit.MILLISECONDS) == this.expire - System.currentTimeMillis());
            return unit.convert(this.expire - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        /**
         * 优先队列里面优先级规则
         */
        @Override
        public int compareTo(Delayed o) {
//            return -1;
            return (int) (o.getDelay(TimeUnit.MILLISECONDS) - (this.getDelay(TimeUnit.MILLISECONDS)));
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("DelayedElement{");
            sb.append("delay=").append(delayTime);
            sb.append(", expire=").append(expire);
            sb.append(", data='").append(data).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }
}

package com.test.concurrent;

import java.util.Comparator;
import java.util.PriorityQueue;

public class PriorityQueueTest {
    public static void main(String[] args) {
        PriorityQueue<PriorityEle> priorityQueue = new PriorityQueue<PriorityEle>(10, new Comparator<PriorityEle>() {
            @Override
            public int compare(PriorityEle o1, PriorityEle o2) {
                return o1.getAge() - o2.getAge();
            }
        });
        priorityQueue.offer(new PriorityEle(1));
        priorityQueue.offer(new PriorityEle(3));
        priorityQueue.offer(new PriorityEle(2));

        PriorityEle ele = priorityQueue.poll();
        System.out.println(ele.getAge());

        ele = priorityQueue.poll();
        System.out.println(ele.getAge());

        ele = priorityQueue.poll();
        System.out.println(ele.getAge());
    }

    public static class PriorityEle {
        private int age;

        public PriorityEle(int age) {
            this.age = age;
        }

        public int getAge() {
            return age;
        }
    }
}

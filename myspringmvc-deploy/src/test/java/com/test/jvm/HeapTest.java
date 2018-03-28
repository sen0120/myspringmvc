package com.test.jvm;

public class HeapTest {
    static int i = 0;

    public static void main(String[] args) {
        byte[] b;
        while (true) {
            b = new byte[1 * 1024 * 1024];//几M
            i++;
            System.out.println(i);
            main(args);
            if (i > 10) {
                break;
            }
        }
        System.err.println(i);
        System.err.println(b.length);
    }

    public static void main1(String[] args) {
        for (int i = 0; i < 77; i++) {
            byte[] b = new byte[1 * 1024 * 1024];//几M
        }
    }
}

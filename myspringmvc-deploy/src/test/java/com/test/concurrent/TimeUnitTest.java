package com.test.concurrent;

import java.util.concurrent.TimeUnit;

public class TimeUnitTest {
    public static void main(String[] args) {
        System.out.println(TimeUnit.SECONDS.toString());

        System.out.println(TimeUnit.SECONDS.toMillis(3));

        System.out.println(TimeUnit.MINUTES.convert(1,TimeUnit.HOURS));

    }
}

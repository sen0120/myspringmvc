package com.hus;

import org.springframework.stereotype.Component;

/**
 * Created by user on 2015/10/19.
 */
@Component
public class Student {
    private int    age;
    private String name;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package com.test.validation;

import javax.validation.constraints.NotNull;

/**
 * Created by fanyun on 16/12/23.
 */
public class Person {
    @NotNull
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

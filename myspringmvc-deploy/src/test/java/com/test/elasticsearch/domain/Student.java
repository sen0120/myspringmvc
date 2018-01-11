package com.test.elasticsearch.domain;

import java.util.Date;

/**
 * Created by fanyun on 16/8/2.
 */
public class Student {
    private String name;
    private int age;
    private boolean tPlanTrade;//t计划交易
    private Date tPlanTradeCreateDate;//t计划交易
    private boolean isMale;

    public Student() {
    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean istPlanTrade() {
        return tPlanTrade;
    }

    public void settPlanTrade(boolean tPlanTrade) {
        this.tPlanTrade = tPlanTrade;
    }

    public Date gettPlanTradeCreateDate() {
        return tPlanTradeCreateDate;
    }

    public void settPlanTradeCreateDate(Date tPlanTradeCreateDate) {
        this.tPlanTradeCreateDate = tPlanTradeCreateDate;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }
}

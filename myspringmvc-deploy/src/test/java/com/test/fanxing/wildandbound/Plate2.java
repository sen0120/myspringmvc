package com.test.fanxing.wildandbound;

class Plate2<T>{
    private T item;
    public Plate2(T t){item=t;}
    public void set(T t){item=t;}
    public T get(){return item;}
}
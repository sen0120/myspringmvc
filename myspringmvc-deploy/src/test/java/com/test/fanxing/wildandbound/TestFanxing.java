package com.test.fanxing.wildandbound;

public class TestFanxing {
    public static void main(String[] args) {
//        Plate<Fruit> super1 = new Plate<Apple>(new Apple());//会报错,泛型容器内无法维护继承关系

        //上界 通配符,只能放Fruit的子类
        Plate<? extends Fruit> extends1 = new Plate<Apple>(new Apple());//Fruit加上? extend就不会把报错,但是可以get出Fruit,无法set(Fruit)
        Fruit fruit = extends1.get();
//        extends1.set(new Fruit());//error
//        extends1.set(new Apple());//error
//        extends1.set(new RedApple());//error


        Plate<? super Fruit> super1 = new Plate<Food>(new Food());//下界 通配符,只能放Fruit的父类
        Object object = super1.get();//n
        super1.set(new Apple());
//        super1.set(new Food());//error
//        super1.set(new Food());//error

    }
}

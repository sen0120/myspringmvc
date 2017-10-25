package com.test.cglib;

public class TestClient {

    public static void main(String[] args) {
        BookServiceBean service = BookServiceFactory.getProxyInstance(new MyCglibProxy("boss"));
        service.create();
    }

    public static void doMethod(BookServiceBean service) {
        service.create();
        service.update();
        service.query();
        service.delete();
    }
}
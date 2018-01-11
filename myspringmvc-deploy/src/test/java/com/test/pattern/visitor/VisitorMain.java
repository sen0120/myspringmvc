package com.test.pattern.visitor;

public class VisitorMain {
    public static void main(String[] args) {
        Visitor visitor = new DisplayingVisitor();

        AElement aElement = new AElement();
        BElement bElement = new BElement();

        aElement.accept(visitor);
        bElement.accept(visitor);
    }
}

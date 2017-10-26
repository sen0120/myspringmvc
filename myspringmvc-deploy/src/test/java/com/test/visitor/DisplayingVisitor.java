package com.test.visitor;

public class DisplayingVisitor implements Visitor {

    public void visit(AElement element) {
        System.out.println(element.getName());
    }

    public void visit(BElement element) {
        System.out.println(element.getName());
    }
}

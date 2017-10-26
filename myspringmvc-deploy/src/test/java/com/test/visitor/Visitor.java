package com.test.visitor;

public interface Visitor {
    void visit(AElement element);

    void visit(BElement element);
}

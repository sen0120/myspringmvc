package com.test.pattern.visitor;

public interface Visitor {
    void visit(AElement element);

    void visit(BElement element);
}

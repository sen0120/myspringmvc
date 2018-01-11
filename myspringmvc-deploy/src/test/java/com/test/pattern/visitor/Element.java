package com.test.pattern.visitor;

public interface Element {
    void accept(Visitor visitor);
}

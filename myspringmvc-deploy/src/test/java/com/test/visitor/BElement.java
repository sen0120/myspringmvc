package com.test.visitor;

public class BElement implements Element {
    private String name = "BElement";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

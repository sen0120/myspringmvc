package com.test.pattern.visitor;

public class AElement implements Element {
    private String name = "AElement";

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

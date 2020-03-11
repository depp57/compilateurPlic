package plic.repint;

import plic.exception.ErreurSemantique;

public abstract class Expression {

    abstract void verifier() throws ErreurSemantique;

    public String toString() {
        return "Expression";
    }

    abstract String toMips();
}
package plic.repint;

import plic.exception.ErreurSemantique;

public abstract class Expression {

    abstract void verifier() throws ErreurSemantique;

    public abstract String toString();

    abstract String toMips();
}
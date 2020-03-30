package plic.repint;

import plic.exception.ErreurSemantique;

public abstract class Expression {

    public abstract void verifier() throws ErreurSemantique;

    public abstract String toMips();

    public abstract String getType();

    public abstract String toString();
}
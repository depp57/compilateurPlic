package plic.repint;

import plic.exception.ErreurSemantique;

public abstract class Instruction {

    abstract void verifier() throws ErreurSemantique;

    abstract String toMips();

    @Override
    public String toString() {
        return "Instruction";
    }
}
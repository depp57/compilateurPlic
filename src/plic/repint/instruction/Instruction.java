package plic.repint.instruction;

import plic.exception.ErreurSemantique;

public abstract class Instruction {

    public abstract void verifier() throws ErreurSemantique;

    public abstract String toMips();

    @Override
    public String toString() {
        return "";
    }
}
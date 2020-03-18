package plic.repint;

import plic.exception.ErreurSemantique;

public class Nombre extends Expression {

    private int val;

    public Nombre(int val) {
        this.val = val;
    }

    @Override
    void verifier() throws ErreurSemantique {}

    @Override
    public String toString() {
        return String.valueOf(val);
    }

    @Override
    String toMips() {
        return "li $v0, " +  val;
    }
}
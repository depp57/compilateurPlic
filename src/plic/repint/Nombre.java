package plic.repint;

import plic.exception.ErreurSemantique;

public class Nombre extends Expression {

    private int val;

    public Nombre(int val) {
        this.val = val;
    }

    @Override
    public void verifier() throws ErreurSemantique {}

    @Override
    public String toString() {
        return String.valueOf(val);
    }

    @Override
    public String toMips() {
        return "li $v0, " +  val;
    }

    @Override
    public String getType() {
        return "entier";
    }
}
package plic.repint;

import plic.exception.ErreurSemantique;

public class Nombre extends Expression {

    private int val;

    public Nombre(int val) {
        this.val = val;
    }

    @Override
    void verifier() throws ErreurSemantique {

    }

    @Override
    public String toString() {
        return super.toString() + " nombre : " + val;
    }
}
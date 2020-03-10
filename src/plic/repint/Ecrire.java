package plic.repint;

import plic.exception.ErreurSemantique;

public class Ecrire extends Instruction {

    private Expression expression;

    public Ecrire(Expression expression) {
        this.expression = expression;
    }

    @Override
    void verifier() throws ErreurSemantique {

    }

    @Override
    String toMips() {
        return null;
    }

    @Override
    public String toString() {
        return super.toString() + " ecrire : " + expression;
    }
}
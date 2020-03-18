package plic.repint;

import plic.exception.ErreurSemantique;

public class Affectation extends Instruction {

    private Expression expression;
    private Acces acces;

    public Affectation(Acces acces, Expression expression) {
        this.expression = expression;
        this.acces = acces;
    }

    @Override
    void verifier() throws ErreurSemantique {
        acces.verifier();
        expression.verifier();
    }

    @Override
    String toMips() {
        return "\t#" + acces + " = " + expression +
                "\n\t" + acces.getAdresse() +
                "\n\t" + expression.toMips() +
                "\n\tsw $v0, ($a0)\n";
    }

    public String toString() {
        return super.toString() + " affectation : " + acces + " := " +  expression;
    }
}
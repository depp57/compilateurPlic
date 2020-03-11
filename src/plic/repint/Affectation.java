package plic.repint;

import plic.exception.ErreurSemantique;

public class Affectation extends Instruction {

    private Expression expression;
    private Idf idf;

    public Affectation(Expression expression, Idf idf) {
        this.expression = expression;
        this.idf = idf;
    }

    @Override
    void verifier() throws ErreurSemantique {
        idf.verifier();
        expression.verifier();
    }

    @Override
    String toMips() {
        return "\t#" + idf.toMips() + " = " + expression.toMips() +
                "\n\tli $t0, " + expression.toMips() +
                "\n\tsw $t0, " + idf.getDeplacement() + "($s7)\n";
    }

    public String toString() {
        return super.toString() + " affectation : " + idf + " := " +  expression;
    }
}
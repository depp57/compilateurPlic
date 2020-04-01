package plic.repint.instruction;

import plic.exception.ErreurSemantique;
import plic.repint.Acces;
import plic.repint.Expression;
import plic.repint.Outils;

public class Affectation extends Instruction {

    private Expression expression;
    private Acces acces;

    public Affectation(Acces acces, Expression expression) {
        this.expression = expression;
        this.acces = acces;
    }

    @Override
    public void verifier() throws ErreurSemantique {
        acces.verifier();
        expression.verifier();

        if (!acces.getType().equals("entier") || !expression.getType().equals("entier"))
            throw new ErreurSemantique("Affectation de type non entier | '" + acces + " := " + expression + "'");
    }

    @Override
    public String toMips() {
        return "\t#" + acces + " = " + expression +
                "\n\t" + expression.toMips() +
                "\n\t" + Outils.empilerV0() +
                "\n\t" + acces.getAdresse() +
                "\n\t" + Outils.depiler("$v0") +
                "\n\tsw $v0, ($a0)\n";
    }

    public String toString() {
        return super.toString() + " affectation : " + acces + " := " +  expression;
    }
}
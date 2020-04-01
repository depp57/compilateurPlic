package plic.repint.instruction;

import plic.exception.ErreurSemantique;
import plic.repint.Bloc;
import plic.repint.Expression;
import plic.repint.Outils;

public class ConditionAlors extends Instruction {

    protected Expression expression;
    protected Bloc alors;

    protected int cptBranch;

    public ConditionAlors(Expression expression, Bloc alors) {
        this.expression = expression;
        this.alors = alors;
    }

    @Override
    public void verifier() throws ErreurSemantique {
        expression.verifier();
        if (!expression.getType().equals("booleen"))
            throw new ErreurSemantique("Expression booléenne attendue pour une condition : '" + expression + "'");

        alors.verifier();
    }

    @Override
    public String toMips() {
        cptBranch = Outils.getCptBranch();

        return "#" + this +
                "\n\t" + expression.toMips() +
                "\n\tbeq $v0, 0, endif" + cptBranch +
                "\n\t" + alors.toMips() +
                "\n\tendif" + cptBranch + ":";
    }

    @Override
    public String toString() {
        return "si (" + expression + ") alors " + alors;
    }
}
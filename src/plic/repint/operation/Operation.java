package plic.repint.operation;

import plic.exception.ErreurSemantique;
import plic.repint.Expression;
import plic.repint.Outils;

public abstract class Operation extends Expression {

    protected Expression exp1, exp2;

    protected Operation(Expression exp1, Expression exp2) {
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    @Override
    public void verifier() throws ErreurSemantique {
        exp1.verifier();
        exp2.verifier();
    }

    @Override
    public String toMips() {
        return  exp1.toMips() +
                "\n\t" + Outils.empilerV0() +
                "\n\t" + exp2.toMips() +
                "\n\t" + Outils.depiler("$v1");
    }
}
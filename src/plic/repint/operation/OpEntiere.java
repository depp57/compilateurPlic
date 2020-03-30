package plic.repint.operation;

import plic.exception.ErreurSemantique;
import plic.repint.Expression;

public abstract class OpEntiere extends Operation {

    protected OpEntiere(Expression exp1, Expression exp2) {
        super(exp1, exp2);
    }

    @Override
    public void verifier() throws ErreurSemantique {
        super.verifier();

        if (!exp1.getType().equals("entier") || ! exp2.getType().equals("entier"))
            throw new ErreurSemantique("Les opérandes doivent être entiers | '" + exp1 + " _ " + exp2 + "'");
    }

    public String getType() { return "entier"; }
}
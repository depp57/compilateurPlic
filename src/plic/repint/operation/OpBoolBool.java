package plic.repint.operation;

import plic.exception.ErreurSemantique;
import plic.repint.Expression;

public abstract class OpBoolBool extends OpBool {

    protected OpBoolBool(Expression exp1, Expression exp2) {
        super(exp1, exp2);
    }

    @Override
    public void verifier() throws ErreurSemantique {
        super.verifier();

        if (!exp1.getType().equals("booleen") || !exp2.getType().equals("booleen"))
            throw new ErreurSemantique("Les 2 expressions doivent être booléennes | '" + exp1 + " _ " + exp2 + "'");
    }

    @Override
    public String getType() {
        return "booleen";
    }
}
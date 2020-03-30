package plic.repint.operation;

import plic.exception.ErreurSemantique;
import plic.repint.Expression;
import plic.repint.Outils;

public abstract class OpEntierBool extends OpBool {

    protected OpEntierBool(Expression exp1, Expression exp2) {
        super(exp1, exp2);
    }

    @Override
    public void verifier() throws ErreurSemantique {
        super.verifier();

        if (!exp1.getType().equals("entier") || !exp2.getType().equals("entier"))
            throw new ErreurSemantique("'" +exp1 + "' n'est pas entière OU '" + exp2 + "' n'est pas entière");
    }

    @Override
    public String getType() {
        return "booleen";
    }
}
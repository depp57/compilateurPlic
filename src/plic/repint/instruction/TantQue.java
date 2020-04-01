package plic.repint.instruction;

import plic.exception.ErreurSemantique;
import plic.repint.Bloc;
import plic.repint.Expression;
import plic.repint.Outils;

public class TantQue extends Iteration {

    private Expression expression;

    public TantQue(Bloc bloc, Expression expression) {
        super(bloc);
        this.expression = expression;
    }

    @Override
    public void verifier() throws ErreurSemantique {
        super.verifier();
        expression.verifier();

        if (!expression.getType().equals("booleen"))
            throw new ErreurSemantique("Expression booléenne attendue : '" + expression);
    }

    @Override
    public String toMips() {
        cptBranch = Outils.getCptBranch();

        return "#tant que " + expression + " faire : " + bloc +
                "\n\ttantQue" + cptBranch + ":" +
                "\n\t" + expression.toMips() +
                "\n\tbeq $v0, 0, finTantQue" + cptBranch +
                "\n\t" + bloc.toMips() +
                "\n\tj tantQue" + cptBranch +
                "\n\tfinTantQue" + cptBranch + ":";
    }

    @Override
    public String toString() {
        return "tant que ( " + expression + " ) repeter " + bloc;
    }
}
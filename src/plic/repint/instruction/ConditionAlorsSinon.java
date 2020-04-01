package plic.repint.instruction;

import plic.exception.ErreurSemantique;
import plic.repint.Bloc;
import plic.repint.Expression;
import plic.repint.Outils;

public class ConditionAlorsSinon extends ConditionAlors {

    private Bloc sinon;

    public ConditionAlorsSinon(Expression expression, Bloc alors, Bloc sinon) {
        super(expression, alors);
        this.sinon = sinon;
    }

    @Override
    public void verifier() throws ErreurSemantique {
        super.verifier();
        sinon.verifier();
    }

    @Override
    public String toMips() {
        cptBranch = Outils.getCptBranch();

        return "#" + this +
                "\n\t" + expression.toMips() +
                "\n\tbeq $v0, 0, else" + cptBranch +
                "\n\t" + alors.toMips() +
                "\n\tj endif" + cptBranch +
                "\n\telse" + cptBranch + ":" +
                "\n\t" + sinon.toMips() +
                "\n\tendif" + cptBranch + ":";
    }

    @Override
    public String toString() {
        return super.toString() + " sinon " + sinon;
    }
}

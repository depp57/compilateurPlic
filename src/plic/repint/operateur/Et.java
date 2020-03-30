package plic.repint.operateur;

import plic.repint.Expression;
import plic.repint.operation.OpBoolBool;

public class Et extends OpBoolBool {

    public Et(Expression exp1, Expression exp2) {
        super(exp1, exp2);
    }

    @Override
    public String toMips() {
        return super.toMips() +
                "\n\tand $v0, $v1, $v0  #Si " + exp1 + " et " + exp2 + " alors $v0 = 1, sinon $v0 = 0";
    }

    @Override
    public String toString() {
        return "(" + exp1 + " et " + exp2 + ")";
    }
}

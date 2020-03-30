package plic.repint.operateur;

import plic.repint.Expression;
import plic.repint.operation.OpEntiere;

public class Multiplication extends OpEntiere {

    public Multiplication(Expression exp1, Expression exp2) {
        super(exp1, exp2);
    }

    @Override
    public String toString() {
        return "(" + exp1 + " * " + exp2 + ")";
    }

    @Override
    public String toMips() {
        return super.toMips() +
                "\n\tmulu $v0, $v0, $v1";
    }
}
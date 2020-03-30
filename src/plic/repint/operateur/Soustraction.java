package plic.repint.operateur;

import plic.repint.Expression;
import plic.repint.operation.OpEntiere;

public class Soustraction extends OpEntiere {

    public Soustraction(Expression exp1, Expression exp2) {
        super(exp1, exp2);
    }

    @Override
    public String toMips() {
        return super.toMips() +
                "\n\tsub $v0, $v1, $v0";
    }

    @Override
    public String toString() {
        return "(" + exp1 + " - " + exp2 + ")";
    }
}
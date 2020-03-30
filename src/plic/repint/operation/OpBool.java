package plic.repint.operation;


import plic.repint.Expression;

public abstract class OpBool extends Operation {

    protected OpBool(Expression exp1, Expression exp2) {
        super(exp1, exp2);
    }

    public String getType() { return "booleen"; }
}
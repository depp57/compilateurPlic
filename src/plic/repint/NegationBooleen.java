package plic.repint;

import plic.exception.ErreurSemantique;

public class NegationBooleen extends Expression {

    private Expression expression;

    public NegationBooleen(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void verifier() throws ErreurSemantique {
        expression.verifier();
        if (!expression.getType().equals("booleen"))
            throw new ErreurSemantique("Expression booléenne attendue | " + expression);
    }

    @Override
    public String toMips() {
        return expression.toMips() + "" +
                "\n\txori $v0, $v0, 1  # inverse le resultat booleen";
    }

    @Override
    public String getType() {
        return "booleen";
    }

    @Override
    public String toString() {
        return " non " + expression;
    }
}

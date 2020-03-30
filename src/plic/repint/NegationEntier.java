package plic.repint;

import plic.exception.ErreurSemantique;

public class NegationEntier extends Expression {

    private Expression expression;

    public NegationEntier(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void verifier() throws ErreurSemantique {
        expression.verifier();
        if (!expression.getType().equals("entier"))
            throw new ErreurSemantique("Expression entière attendue | " + expression);
    }

    @Override
    public String toMips() {
        return expression.toMips() + "" +
                "\n\tmulu $v0, $v0, -1  # inverse le resultat entier";
    }

    @Override
    public String getType() {
        return "entier";
    }

    @Override
    public String toString() {
        return " - " + expression;
    }
}

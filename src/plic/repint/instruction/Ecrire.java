package plic.repint.instruction;

import plic.exception.ErreurSemantique;
import plic.repint.Expression;

public class Ecrire extends EntreeSortie {

    private Expression expression;

    public Ecrire(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void verifier() throws ErreurSemantique {
        expression.verifier();
    }

    @Override
    public String toMips() {
        final String NEW_LINE = "\t#retour a la ligne\n" +
                                "\tli $v0, 11\n" +
                                "\tli $a0, '\\n'\n" +
                                "\tsyscall";

        return  "\t#ecrire " + expression +
                "\n\t" + expression.toMips() +
                "\n\tmove $a0, $v0" +
                "\n\tli $v0, 1" +
                "\n\tsyscall\n\n" + NEW_LINE +"\n";
    }

    @Override
    public String toString() {
        return "ecrire : " + expression;
    }
}
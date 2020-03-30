package plic.repint;

import plic.exception.ErreurSemantique;

public class Ecrire extends Instruction {

    private Expression expression;

    public Ecrire(Expression expression) {
        this.expression = expression;
    }

    @Override
    void verifier() throws ErreurSemantique {
        expression.verifier();
    }

    @Override
    String toMips() {
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
        return super.toString() + " ecrire : " + expression;
    }
}
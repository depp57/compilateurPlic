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
        final String NEW_LINE = "\t#retour à la ligne\n" +
                                "\tli $v0, 4\n" +
                                "\tla $a0, newline\n" +
                                "\tsyscall";
        int deplacement = TDS.getInstance().identifier(new Entree(expression.toMips())).getDeplacement();
        return  "\t#ecrire " + expression.toMips() +
                "\n\tli $v0, 1" +
                "\n\tlw $t0, " + deplacement + "($s7)" +
                "\n\tmove $a0, $t0" +
                "\n\tsyscall\n\n" + NEW_LINE +"\n";
    }

    @Override
    public String toString() {
        return super.toString() + " ecrire : " + expression;
    }
}
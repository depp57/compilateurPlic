package plic.repint.instruction;

import plic.exception.ErreurSemantique;
import plic.repint.Bloc;
import plic.repint.Expression;
import plic.repint.Idf;
import plic.repint.Outils;

public class Pour extends Iteration {

    private Idf idf;
    private Expression expressionG, expressionD;

    public Pour(Bloc bloc, Idf idf, Expression expressionG, Expression expressionD) {
        super(bloc);
        this.expressionG = expressionG;
        this.expressionD = expressionD;
        this.idf = idf;
    }

    @Override
    public void verifier() throws ErreurSemantique {
        super.verifier();
        idf.verifier();
        if (!idf.getType().equals("entier"))
            throw new ErreurSemantique("Idf entier attendu : '" + idf + "' (ligne:" + idf.getLigne() + ")");

        expressionG.verifier();
        if (!expressionG.getType().equals("entier"))
            throw new ErreurSemantique("Expression entière attendue : '" + expressionG + "' (ligne:" + idf.getLigne() + ")");

        expressionD.verifier();
        if (!expressionD.getType().equals("entier"))
            throw new ErreurSemantique("Expression entière attendue : '" + expressionD + "' (ligne:" + idf.getLigne() + ")");
    }

    @Override
    public String toMips() {
        cptBranch = Outils.getCptBranch();

        return "#pour " + idf + " de " + expressionG + " .. " + expressionD + " repeter " + bloc +
                "\n\t" + expressionG.toMips() +
                "\n\t" + Outils.empilerV0() +
                "\n\t" + expressionD.toMips() +
                "\n\t" + Outils.depiler("$t0") +
                "\n\tmove $t1, $v0" +
                "\n\tbgt $t0, $t1, finPour" + cptBranch +

                "\n\t" + idf.getAdresse() +
                "\n\tsw $t0, ($a0)" +
                "\n\tmove $v0, $t1" +
                "\n\t" + Outils.empilerV0() +

                "\n\tdebutPour" + cptBranch + ":" +
                "\n\tlw $t0, ($a0)" +
                "\n\t" + Outils.depiler("$v0") +
                "\n\tbgt $t0, $v0, finPour" + cptBranch +
                "\n\t" + Outils.empilerV0() +

                "\n\t" + bloc.toMips() +

                "\n\t" + idf.getAdresse() +
                "\n\tlw $t0, ($a0)" +
                "\n\taddi $t0, $t0, 1" +
                "\n\tsw $t0, ($a0)" +
                "\n\tj debutPour" + cptBranch +

                "\n\tfinPour" + cptBranch + ":" +

                "\n\tmove $t1, $v0" +
                "\n\t" + expressionG.toMips() +
                "\n\tbgt $v0, $t1, jamaisDansPour" + cptBranch +
                "\n\t" + idf.getAdresse() +
                "\n\tlw $t0, ($a0)" +
                "\n\tsubi $t0, $t0, 1 #Soustrait 1 à " + idf + " car le pour va 1 itération trop loin" +
                "\n\tsw $t0, ($a0)" +
                "\n\tjamaisDansPour" + cptBranch + ":";
    }

    @Override
    public String toString() {
        return "pour " + idf + " dans " + expressionG + " .. " + expressionD + " repeter " + bloc;
    }
}
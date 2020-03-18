package plic.repint;

import plic.exception.ErreurSemantique;

public class AccesTableau extends Acces {

    private Expression expression;
    private Idf idf;

    public AccesTableau(Idf idf, Expression expression) {
        this.idf = idf;
        this.expression = expression;
    }

    @Override
    void verifier() throws ErreurSemantique {
        idf.verifier();
        expression.verifier();

        if (!TDS.getInstance().identifier(new Entree(idf.toString())).getType().equals("tableau"))
            throw new ErreurSemantique("Accès à une variable non tableau, comme si elle était un tableau | '" + idf + "' (ligne:" + idf.getLigne() + ")");
    }

    @Override
    public String toString() {
        return idf + "[" + expression + "]";
    }

    @Override
    String toMips() {
        return getAdresse() +
                "\n\tlw $v0, ($a0)";
    }

    @Override
    public int getDeplacement() {
        return TDS.getInstance().identifier(new Entree(idf.toString())).getDeplacement(); //Attention renvoie l'adresse de la premiere case du tableau
    }

    @Override
    public String getAdresse() {
        return "la $a0, " + getDeplacement() + "($s7)" +
                "\n\t" + expression.toMips() +
                "\n\tli $a1, 4      #" +
                "\n\tmult $v0, $a1  # Multiplication par 4 de l'indice | adresse de tab[2] est 2*4 en dessous de la 1ère variable du tab (tab[0]) " +
                "\n\tmflo $v0       #" +
                "\n\tsub $a0, $a0, $v0";
    }
}
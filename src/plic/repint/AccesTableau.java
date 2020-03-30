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
    public void verifier() throws ErreurSemantique {
        idf.verifier();
        expression.verifier();

        if (!expression.getType().equals("entier"))
            throw new ErreurSemantique("L'indice d'un accès tableau doit être de type entier | '" + idf + "' (ligne:" + idf.getLigne() + ")");

        if (!TDS.getInstance().identifier(new Entree(idf.toString())).getType().equals("tableau"))
            throw new ErreurSemantique("Accès à une variable non tableau, comme si elle était un tableau | '" + idf + "' (ligne:" + idf.getLigne() + ")");
    }

    @Override
    public String toString() {
        return idf + "[" + expression + "]";
    }

    @Override
    public String toMips() {
        return getAdresse() +
                "\n\tlw $v0, ($a0)";
    }

    @Override
    public int getDeplacement() {
        return TDS.getInstance().identifier(new Entree(idf.toString())).getDeplacement(); //Attention renvoie l'adresse de la premiere case du tableau
    }

    @Override
    public String getAdresse() {
        final String CHECK_IN_BOUNDS =
            "\n\t# Verifie si l acces est bien dans les bornes du tableau : " + idf +
            "\n\tli $t2, " + idf.getLigne() +
            "\n\tli $v1, " + getTailleTableau() +
            "\n\tbge $v0, $v1, arrayOutOfBounds  # si indice > tableau.length -> erreur" +
            "\n\tli $v1, 0" +
            "\n\tblt $v0, $v1, arrayOutOfBounds  # si indice <= 0 -> erreur";

        return  expression.toMips() + "\n" +
                "\n\tla $a0, " + getDeplacement() + "($s7)" +
                CHECK_IN_BOUNDS +
                "\n\tli $a1, 4      #" +
                "\n\tmulu $v0, $v0, $a1  # Calcul de l indice" +
                "\n\tsub $a0, $a0, $v0";
    }

    private int getTailleTableau() {
        return ((SymboleTableauEntier)TDS.getInstance().identifier(new Entree(idf.toString()))).getTaille();
    }

    @Override
    public String getType() {
        return "entier";
    }
}
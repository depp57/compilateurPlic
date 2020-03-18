package plic.repint;

import plic.analyse.Token;
import plic.exception.ErreurSemantique;

public class Idf extends Acces {

    private String nom;
    private int ligne;

    public Idf(Token token) {
        nom = token.getWord();
        ligne = token.getLine();
    }

    @Override
    void verifier() throws ErreurSemantique {
        if (TDS.getInstance().identifier(new Entree(nom)) == null)
            throw new ErreurSemantique("Idf non déclaré : '" + nom + "' (ligne:" + ligne + ")");
    }

    @Override
    public int getDeplacement() {
        return TDS.getInstance().identifier(new Entree(nom)).getDeplacement();
    }

    @Override
    public String getAdresse() {
        return "la $a0, " + getDeplacement() + "($s7)";
    }

    @Override
    public String toString() {
        return nom;
    }

    @Override
    String toMips() {
        return "lw $v0, " + getDeplacement() + "($s7)";
    }

    public int getLigne() {
        return ligne;
    }
}
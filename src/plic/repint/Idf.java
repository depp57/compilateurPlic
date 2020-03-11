package plic.repint;

import plic.analyse.Token;
import plic.exception.ErreurSemantique;

public class Idf extends Expression {

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

    int getDeplacement() {
        return TDS.getInstance().identifier(new Entree(nom)).getDeplacement();
    }

    @Override
    public String toString() {
        return super.toString() + " idf : " + nom;
    }

    @Override
    String toMips() {
        return nom;
    }
}
package plic.repint;

import plic.analyse.Token;
import plic.exception.ErreurSemantique;

public class Idf extends Expression {

    private String nom;

    public Idf(String nom) {
        this.nom = nom;
    }

    @Override
    void verifier() throws ErreurSemantique {
        if (TDS.getInstance().identifier(new Entree(nom)) == null)
            throw new ErreurSemantique("Idf non déclaré : " + nom);
    }

    @Override
    public String toString() {
        return super.toString() + " idf : " + nom;
    }
}
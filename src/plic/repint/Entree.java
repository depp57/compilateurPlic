package plic.repint;

import plic.analyse.Token;

import java.util.Objects;

public class Entree {

    private Token token;

    public Entree(Token idf) {
        this.token = idf;
    }

    public Entree(String idf) {
        token = new Token(idf, 0);
    }

    public String getIdf() {
        return token.getWord();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entree entree = (Entree) o;
        return token.getWord().equals(entree.token.getWord());
    }

    @Override
    public int hashCode() {
        return Objects.hash(token.getWord());
    }

    @Override
    public String toString() {
        return "Entrée : " + token;
    }
}
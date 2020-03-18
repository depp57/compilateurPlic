package plic.repint;

public class SymboleTableauEntier extends Symbole {

    private int taille;

    public SymboleTableauEntier(int taille) {
        super("tableau");
        this.taille = taille;
    }

    public int getTaille() {
        return taille;
    }

    @Override
    public int tailleAllocation() {
        return 4 * taille;
    }
}
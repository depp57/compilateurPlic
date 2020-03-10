package plic.repint;

import plic.exception.DoubleDeclaration;

import java.util.HashMap;

public class TDS {

    private int cptDepl;
    private HashMap<Entree, Symbole> map;
    private static final TDS instance = new TDS();

    private TDS() {
        map = new HashMap<>();
        cptDepl = 0;
    }

    public static synchronized TDS getInstance() {
        return instance;
    }

    public void ajouter(Entree entree, Symbole symbole) throws DoubleDeclaration {
        if (map.containsKey(entree))
            throw new DoubleDeclaration(entree +  " est déjà déclarée");

        allouerMemoire(symbole);
        map.put(entree, symbole);
    }

    public Symbole identifier(Entree entree) {
        return map.get(entree);
    }

    private void allouerMemoire(Symbole symbole) {
        symbole.setDeplacement(cptDepl);
        cptDepl -= 4; //TODO à changer quand il yaura les tableaux
    }

    public int nbDeclarations() {
        return map.size();
    }

    public String toString() {
        return map.toString();
    }
}
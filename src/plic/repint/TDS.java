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

    public void ajouter(Entree entree, Symbole symbole, int ligne) throws DoubleDeclaration {
        if (map.containsKey(entree))
            throw new DoubleDeclaration("Entrée déjà déclarée : '" + entree + "' (ligne:" + ligne + ")");

        allouerMemoire(symbole);
        map.put(entree, symbole);
    }

    public Symbole identifier(Entree entree) {
        return map.get(entree);
    }

    private void allouerMemoire(Symbole symbole) {
        symbole.setDeplacement(cptDepl);
        cptDepl -= symbole.tailleAllocation();
    }

    public int nbDeclarations() {
        return map.size();
    }

    public int getCptDepl() {
        return cptDepl;
    }

    public String toString() {
        return map.toString();
    }
}
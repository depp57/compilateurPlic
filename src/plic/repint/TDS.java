package plic.repint;

import plic.exception.DoubleDeclaration;

import java.util.HashMap;

public class TDS {

    private HashMap<Entree, Symbole> map;
    private static final TDS instance = new TDS();

    private TDS() {
        map = new HashMap<>();
    }

    public static synchronized TDS getInstance() {
        return instance;
    }

    public void ajouter(Entree entree, Symbole symbole) throws DoubleDeclaration {
        if (map.containsKey(entree))
            throw new DoubleDeclaration();
        map.put(entree, symbole);
    }
}
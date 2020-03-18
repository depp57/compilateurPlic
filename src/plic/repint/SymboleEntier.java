package plic.repint;

public class SymboleEntier extends Symbole {

    public SymboleEntier() {
        super("entier");
    }

    @Override
    public int tailleAllocation() {
        return 4;
    }
}
package plic.repint;

public abstract class Symbole {

    private String type;
    private int deplacement;

    public Symbole(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public int getDeplacement() {
        return deplacement;
    }

    public void setDeplacement(int deplacement) {
        this.deplacement = deplacement;
    }

    public abstract int tailleAllocation();

    public String toString() {
        return "Symbole : " + type + " deplacement : " + deplacement;
    }
}
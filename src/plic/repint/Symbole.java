package plic.repint;

import java.util.Objects;

public class Symbole {

    private String type;
    private int deplacement;

    public Symbole(String type, int deplacement) {
        this.type = type;
        this.deplacement = deplacement;
    }

    public String getType() {
        return type;
    }

    public int getDeplacement() {
        return deplacement;
    }
}

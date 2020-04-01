package plic.repint.instruction;

import plic.exception.ErreurSemantique;
import plic.repint.Idf;

public class Lire extends EntreeSortie {

    private Idf idf;

    public Lire(Idf idf) {
        this.idf = idf;
    }

    @Override
    public void verifier() throws ErreurSemantique {
        idf.verifier();

        if (!idf.getType().equals("entier"))
            throw new ErreurSemantique("Lecture d'un type non entier : '" + idf + "' (ligne:" + idf.getLigne() + ")");
    }

    @Override
    public String toMips() {
        return  "\n\t#lire " + idf +
                "\n\tli $v0, 4" +
                "\n\tla $a0, ask_user_input" +
                "\n\tsyscall" +
                "\n\tli $v0, 5" +
                "\n\tsyscall" +
                "\n\t" + idf.getAdresse() +
                "\n\tsw $v0, ($a0)\n";
    }

    @Override
    public String toString() {
        return "lire : " + idf;
    }
}
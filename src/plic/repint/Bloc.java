package plic.repint;

import plic.exception.ErreurSemantique;

import java.util.ArrayList;

public class Bloc {

    private ArrayList<Instruction> instructions;

    public Bloc() {
        instructions = new ArrayList<>();
    }

    public void ajouter(Instruction instruction) {
        instructions.add(instruction);
    }

    public void verifier() throws ErreurSemantique {
        for (Instruction i : instructions) i.verifier();
    }

    public String toMips() {
        StringBuilder mips = new StringBuilder();
        mips.append(mipsInit());
        for (Instruction i : instructions) mips.append(i.toMips()).append("\n");
        return mips.toString();
    }

    private String mipsInit() {
        return ".data\n\tnewline : .asciiz \"\\n\" #caractère ascii représentant le retour à la ligne\n" +
                ".text\n" +
                "main :\n" +
                "\tmove $s7, $sp\n" +
                "\t#allocation de la mémoire pour les déclarations de variables\n" +
                "\tadd $sp, $sp, " + TDS.getInstance().getCptDepl() + "\n\n";
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("Bloc {\n");
        instructions.forEach((i -> res.append("    ").append(i).append("\n")));
        res.append('}');
        return res.toString();
    }
}
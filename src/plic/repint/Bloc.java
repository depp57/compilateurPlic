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
        final String OUT_OF_BOUNDS = //TODO check si pas de duplication au cas ou y'aurait plusieurs blocs faudrait peut etre le mettre avec un id ? (arrayOutOfBounds1..2..3..)
                "\n\n\t# Erreur quand on tente d acceder a un indice non comprit dans les bornes d un tableau" +
                "\n\tarrayOutOfBounds : " +
                "\n\t\tli $v0, 4" +
                "\n\t\tla $a0, error_arrayOutOfBounds" +
                "\n\t\tsyscall" +
                "\n\t\tli $v0, 1" +
                "\n\t\tmove $a0, $t2" +
                "\n\t\tsyscall";

        final String EXIT_CODE =
                "\n\n#code de fin" +
                "\nend :" +
                "\n\tli $v0, 10" +
                "\n\tsyscall";
        StringBuilder mips = new StringBuilder();
        mips.append(mipsInit());
        for (Instruction i : instructions) mips.append(i.toMips()).append("\n");
        return mips.toString() +
                "\tb end" + //TODO voir pour le skip de OUT_OF_BOUNDS si plusieurs blocs ?
                OUT_OF_BOUNDS +
                EXIT_CODE;
    }

    private String mipsInit() {
        return ".data\n\terror_arrayOutOfBounds : .asciiz \"ERREUR: arrayOutOfBounds lors de l acces a un tableau, ligne : \"\n" +
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
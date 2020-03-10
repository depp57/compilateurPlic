package plic.repint;

import plic.exception.ErreurSemantique;
import plic.exception.ErreurSyntaxique;

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
        for (Instruction i : instructions) mips.append(i.toMips());
        return mips.toString();
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("Bloc {\n");
        instructions.forEach((i -> res.append("    ").append(i).append("\n")));
        res.append('}');
        return res.toString();
    }
}
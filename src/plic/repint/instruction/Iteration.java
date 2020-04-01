package plic.repint.instruction;

import plic.exception.ErreurSemantique;
import plic.repint.Bloc;

public abstract class Iteration extends Instruction {

    protected Bloc bloc;
    protected int cptBranch;

    public Iteration(Bloc bloc) {
        this.bloc = bloc;
    }

    @Override
    public void verifier() throws ErreurSemantique {
        bloc.verifier();
    }

    @Override
    public String toMips() {
        return null;
    }
}
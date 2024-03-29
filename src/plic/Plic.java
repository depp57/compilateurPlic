package plic;

import plic.analyse.AnalyseurSyntaxique;
import plic.exception.DoubleDeclaration;
import plic.exception.ErreurSemantique;
import plic.exception.ErreurSyntaxique;
import plic.repint.Bloc;

import java.io.File;

public class Plic {

    public Plic(String fileName) {
        try {
            File file = new File(fileName);
            if (!file.exists()) throw new ErreurSyntaxique(fileName + " n'a pas été trouvé");
            Bloc bloc = new AnalyseurSyntaxique(file).analyse();
            bloc.verifier();

            StringBuilder mips = new StringBuilder(bloc.initMips());
            mips.append(bloc.toMips());
            mips.append(bloc.endMips());

            System.out.print(mips);
        }
        catch (ErreurSyntaxique | DoubleDeclaration | ErreurSemantique e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        if (args.length == 1 && args[0].endsWith(".plic"))
            new Plic(args[0]);
        else
            System.out.println("ERREUR: Veuillez passer le chemin d'un fichier.plic en argument");
    }
}
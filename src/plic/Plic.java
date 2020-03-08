package plic;

import plic.analyse.AnalyseurSyntaxique;
import plic.exception.ErreurSyntaxique;

import java.io.File;
import java.io.FileNotFoundException;

public class Plic {

    public Plic(String fileName) {
        try {
            File file = new File(fileName);
            if (!file.exists()) throw new ErreurSyntaxique(fileName + " n'a pas été trouvé");
            new AnalyseurSyntaxique(file).analyse();
        }
        catch (ErreurSyntaxique e) {
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
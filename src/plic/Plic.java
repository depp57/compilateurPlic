package plic;

import plic.analyse.AnalyseurSyntaxique;
import plic.exception.ErreurSyntaxique;

import java.io.File;

public class Plic {

    public Plic(String fileName) {
        AnalyseurSyntaxique as = new AnalyseurSyntaxique(new File(fileName));
        try {
            as.analyse();
        }
        catch (ErreurSyntaxique e) {
            e.printStackTrace();
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
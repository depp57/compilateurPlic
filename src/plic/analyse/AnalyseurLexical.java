package plic.analyse;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Permet d'effectuer une analyse lexicale sur un fichier.plic
 */
public class AnalyseurLexical {

    /**
     * Scanner permettant de lire le fichier
     */
    private Scanner scanner;

    /**
     * Unique constructeur de l'analyseur
     * @param file File Fichier.plic à analyser
     */
    public AnalyseurLexical(File file) {
        try {
            scanner = new Scanner(file);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Renvoie le mot suivant du fichier
     * Ignore les commentaires
     * @return String Mot suivant
     */
    public String next() {
         if (scanner.hasNext()) {
             String next = scanner.next();
             return isComment(next) ? next() : next;
         }
         else
             return "EOF";
    }

    /**
     * Vérifie si un mot est un commentaire
     * Si c'est un commentaire, saute toute la ligne (=tout le commentaire)
     * @param word String Mot à vérifier
     * @return Boolean true si c'est un commentaire, false sinon
     */
    private boolean isComment(String word) {
        if (word.startsWith("//")) {
            if (scanner.hasNext())
                scanner.nextLine();

            return true;
        }
        return false;
    }
}
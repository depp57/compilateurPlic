package plic.analyse;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Permet d'effectuer une analyse lexicale sur un fichier.plic
 */
public class AnalyseurLexical {

    /**
     * Scanner permettant de lire le fichier ligne par ligne
     */
    private Scanner lineScanner;

    /**
     * Scanner permettant de lire une ligne mot par mot
     */
    private Scanner wordScanner;

    /**
     * Ligne courante à analyser
     */
    private String line;

    /**
     * Compteur de ligne
     */
    private int lineCounter = 0;

    /**
     * Unique constructeur de l'analyseur
     * @param file File Fichier.plic à analyser
     */
    public AnalyseurLexical(File file) {
        try {
            lineScanner = new Scanner(file);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Renvoie le token suivant (mot + ligne) du fichier
     * Ignore les commentaires
     * @return Token Token suivant contenant le mot et la ligne
     */
    public Token next() {
        if (line == null)
            if (!nextLine()) return new Token("EOF", lineCounter); //Si il n'y a plus de ligne retourne EOF

        Token nextWord = nextWord();
        if (nextWord == null)
            return next();
        else {
            boolean isComment = isComment(nextWord.getWord());
            if (isComment)
                return nextLine() ? next() : new Token("EOF", lineCounter);
            return nextWord;
        }

    }

    /**
     * Renvoie le token suivant de la ligne courante.
     * @return Token Token suivant.
     */
    private Token nextWord() {
        if (wordScanner.hasNext()) {
            Token token = new Token(wordScanner.next(), lineCounter);
            if (!wordScanner.hasNext()) line = null; //Si on est arrivé à la fin de la ligne on la reset
            return token;
        }
        else {
            line = null;
            return null;
        }
    }

    /**
     * Passe à la ligne suivante du fichier.
     * @return Boolean true si la méthode a pu passer une ligne, false sinon (ne reste plus de ligne)
     */
    private boolean nextLine() {
        if (lineScanner.hasNextLine()) {
            line = lineScanner.nextLine();
            lineCounter++;
            wordScanner = new Scanner(line);
            return true;
        }
        return false;
    }

    /**
     * Vérifie si un mot est un commentaire
     * @param word String Mot à vérifier
     * @return Boolean true si c'est un commentaire, false sinon
     */
    private boolean isComment(String word) {
        return word.startsWith("//");
    }
}
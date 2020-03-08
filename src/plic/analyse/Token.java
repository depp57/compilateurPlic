package plic.analyse;

/**
 * Mémorise un mot et le numéro de la ligne où il apparait dans le fichier
 */
public class Token {

    /**
     * Le mot
     */
    private String word;

    /**
     * La ligne où il apparait dans le fichier
     */
    private int line;

    /**
     * Constructeur unique
     * @param word String Le mot
     * @param line int La ligne
     */
    public Token(String word, int line) {
        this.word = word;
        this.line = line;
    }

    /**
     * Getteur du mot
     * @return String le mot
     */
    public String getWord() {
        return word;
    }

    /**
     * Affiche de manière visible le Token
     * @return String contient l'affichage de manière visible
     */
    public String toString() {
        return "'" + word + "' (ligne:" + line + ")";
    }
}
package plic.analyse;

import java.util.Objects;

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
     * Getteur de la ligne
     * @return int La ligne à laquelle apparait le token dans le fichier
     */
    public int getLine() { return line; }

    /**
     * Affiche de manière visible le Token
     * @return String contient l'affichage de manière visible
     */
    public String toString() {
        return "'" + word + "' (ligne:" + line + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return line == token.line &&
                word.equals(token.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, line);
    }
}
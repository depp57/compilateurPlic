package plic.exception;

/**
 * Classe d'erreurs lancées lorsqu'une erreur de syntaxe apparait
 */
public class ErreurSyntaxique extends Exception {

    /**
     * Constructeur unique
     * @param message String Le message d'erreur
     */
    public ErreurSyntaxique(String message) {
        super("ERREUR: " + message);
    }
}
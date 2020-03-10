package plic.exception;

/**
 * Classe d'erreurs lancées lorsque l'on déclare deux fois une même variable
 */
public class DoubleDeclaration extends Exception {

    /**
     * Constructeur unique
     * @param message String Le message d'erreur
     */
    public DoubleDeclaration(String message) {
        super("ERREUR: " + message);
    }
}
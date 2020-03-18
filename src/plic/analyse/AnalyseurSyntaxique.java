package plic.analyse;

import plic.exception.DoubleDeclaration;
import plic.exception.ErreurSemantique;
import plic.exception.ErreurSyntaxique;
import plic.repint.*;

import java.io.File;
import java.util.Arrays;

/**
 * Analyseur syntaxique du compilateur
 */
public class AnalyseurSyntaxique {

    /**
     * Analyseur lexical permettant d'obtenir les mots du fichiers .plic
     */
    private final AnalyseurLexical analex;

    /**
     * Terminaux propres et réservés au langage
     */
    private static final String[] terminaux = {"programme", "entier", "tableau", "ecrire", "lire", "si",
                                                "alors", "sinon", "pour", "dans", "repeter", "tantque", "non"};

    /**
     * Token (mot + ligne) courant lu
     */
    private Token uniteCourante;

    public AnalyseurSyntaxique(File file) {
        analex = new AnalyseurLexical(file);
    }

    public Bloc analyse() throws ErreurSyntaxique, DoubleDeclaration, ErreurSemantique {
        uniteCourante = analex.next();
        Bloc bloc = analyseProg();
        if (!uniteCourante.getWord().equals("EOF"))
            throw new ErreurSyntaxique("EOF attendu | uniteCourante : " + uniteCourante);
        return bloc;
    }

    private Bloc analyseProg() throws ErreurSyntaxique, DoubleDeclaration, ErreurSemantique {
        if (!uniteCourante.getWord().equals("programme"))
            throw new ErreurSyntaxique("programme attendu | uniteCourante : " + uniteCourante);
        uniteCourante = analex.next();

        analyseIDF();

        return analyseBloc();
    }

    private Instruction analyseInstruction() throws ErreurSyntaxique {
        //TODO à modifier : CONDITION | ITERATION
        if (estEcrire()) //TODO quand on pourra lire
            return analyseEcrire();
        else if (estAffectation())
            return analyseAffectation();
        else
            throw new ErreurSyntaxique("Instruction attendue | uniteCourante : " + uniteCourante);
    }

    private Ecrire analyseEcrire() throws ErreurSyntaxique {
        if (!estEcrire())
            throw new ErreurSyntaxique("'ecrire' attendu | uniteCourante : " + uniteCourante);
        uniteCourante = analex.next();
        Ecrire instruction = new Ecrire(analyseOperande());
        analyseTerminal(";");
        return instruction;
    }

    private Affectation analyseAffectation() throws ErreurSyntaxique {
        Acces acces = analyseAcces();
        analyseTerminal(":=");
        Expression exp = analyseExpression();
        analyseTerminal(";");
        return new Affectation(acces, exp);
    }

    private Acces analyseAcces() throws ErreurSyntaxique {
        if (!estIdf())
            throw new ErreurSyntaxique("Identificateur attendu pour un accès | uniteCourante : " + uniteCourante);

        Token idf = uniteCourante;
        uniteCourante = analex.next();

        if (uniteCourante.getWord().equals("[")) { //Si c'est un tableau
            analyseTerminal("[");
            Expression expression = analyseExpression();
            analyseTerminal("]");
            return new AccesTableau(new Idf(idf), expression);
        }
        else {
            return new Idf(idf);
        }
    }

    private Expression analyseExpression() throws ErreurSyntaxique {
        //TODO à modifier quand on pourra affecter avec un opérateur(s)
        return analyseOperande();
    }

    private Expression analyseOperande() throws ErreurSyntaxique {
        //TODO à modifier :  | ACCES | - EXPRESSION | non EXPRESSION | ( EXPRESSION )
        Expression expression;

        if (estCsteEntiere()) {
            expression = new Nombre(Integer.parseInt(uniteCourante.getWord()));
            uniteCourante = analex.next();
        }
        else if (estIdf())
            expression = analyseAcces();
        else
            throw new ErreurSyntaxique("Mauvais opérande : " + uniteCourante);

        return expression;
    }

    private Bloc analyseBloc() throws ErreurSyntaxique, DoubleDeclaration, ErreurSemantique {
        Bloc bloc = new Bloc();
        analyseTerminal("{");

        while (estDeclaration())
            analyseDeclaration();

        bloc.ajouter(analyseInstruction());
        while (estInstruction())
            bloc.ajouter(analyseInstruction());

        analyseTerminal("}");
        return bloc;
    }

    private void analyseDeclaration() throws ErreurSyntaxique, DoubleDeclaration, ErreurSemantique {
        Symbole symbole = analyseType();
        String idf = uniteCourante.getWord();
        int ligne = uniteCourante.getLine();
        analyseIDF();
        analyseTerminal(";");
        TDS.getInstance().ajouter(new Entree(idf), symbole, ligne);
    }

    private Idf analyseIDF() throws ErreurSyntaxique {
        if (estIdf()) {
            Idf idf = new Idf(uniteCourante);
            uniteCourante = analex.next();
            return idf;
        }
        else
            throw new ErreurSyntaxique("Mauvais identificateur : " + uniteCourante);
    }

    private Symbole analyseType() throws ErreurSyntaxique, ErreurSemantique {
        if (estDeclaration()) {
            Symbole symbole;
            if (uniteCourante.getWord().equals("entier")) {
                symbole = new SymboleEntier();
                uniteCourante = analex.next();
            }
            else {
                uniteCourante = analex.next();

                analyseTerminal("[");
                if (!estCsteEntiere())
                    throw new ErreurSyntaxique("Constante entière attendue pour taille du tableau | uniteCourante : " + uniteCourante);
                int taille = Integer.parseInt(uniteCourante.getWord());
                if (taille <= 0)
                    throw new ErreurSemantique("Taille de tableau <= 0 | uniteCourante : " + uniteCourante);
                uniteCourante = analex.next();
                analyseTerminal("]");

                symbole = new SymboleTableauEntier(taille);
            }

            return symbole;
        }
        else
            throw new ErreurSyntaxique("Mauvais type : " + uniteCourante);
    }

    private void analyseTerminal(String mot) throws ErreurSyntaxique {
        if (!uniteCourante.getWord().equals(mot))
            throw new ErreurSyntaxique("Terminal " + mot + " attendu | uniteCourante : " + uniteCourante);

        uniteCourante = analex.next();
    }

    /* ----------------------------------------------------------
       |   METHODES POUR VERIFIER LE TYPE DE L'UNITE COURANTE   |
       ---------------------------------------------------------- */
    private boolean estInstruction() {
        //TODO à modifier : CONDITION | ITERATION
        //TODO quand on pourra lire
        return estEcrire() || estAffectation();
    }

    private boolean estAffectation() {
        return estIdf(); //TODO quand il y'aura les tableaux idf [ EXPRESSION ]
    }

    private boolean estEcrire() {
        return uniteCourante.getWord().equals("ecrire");
    }

    private boolean estDeclaration() {
        String motCourant = uniteCourante.getWord();
        return motCourant.equals("entier") || motCourant.equals("tableau");
    }

    private boolean estIdf() {
        return (uniteCourante.getWord().matches("[a-zA-Z]+") && !estTerminal());
    }

    private boolean estTerminal() {
        return Arrays.asList(terminaux).contains(uniteCourante.getWord());
    }

    private boolean estCsteEntiere() {
        return uniteCourante.getWord().matches("[0-9]+");
    }
}
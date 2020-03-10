package plic.analyse;

import plic.exception.DoubleDeclaration;
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

    public Bloc analyse() throws ErreurSyntaxique, DoubleDeclaration {
        uniteCourante = analex.next();
        Bloc bloc = analyseProg();
        if (!uniteCourante.getWord().equals("EOF"))
            throw new ErreurSyntaxique("EOF attendu | uniteCourante : " + uniteCourante);
        return bloc;
    }

    private Bloc analyseProg() throws ErreurSyntaxique, DoubleDeclaration {
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
        Ecrire instruction = new Ecrire(analyseIDF());
        analyseTerminal(";");
        return instruction;
    }

    private Affectation analyseAffectation() throws ErreurSyntaxique {
        Idf idf = analyseIDF();
        analyseTerminal(":=");
        Expression exp = analyseExpression();
        analyseTerminal(";");
        return new Affectation(exp, idf);
    }

    private void analyseAcces() throws ErreurSyntaxique {
        //TODO à modifier quand il y'aura les tableaux : idf [ EXPRESSION ]
        if (!estIdf())
            throw new ErreurSyntaxique("Identificateur attendu pour un accès | uniteCourante : " + uniteCourante);
        uniteCourante = analex.next();
    }

    private Expression analyseExpression() throws ErreurSyntaxique {
        //TODO à modifier quand on pourra affecter avec un opérateur(s)
        return analyseOperande();
    }

    private Expression analyseOperande() throws ErreurSyntaxique {
        //TODO à modifier :  | ACCES | - EXPRESSION | non EXPRESSION | ( EXPRESSION )
        if (!estCsteEntiere())
            throw new ErreurSyntaxique("Mauvais opérande : " + uniteCourante);
        Nombre nombre = new Nombre(Integer.parseInt(uniteCourante.getWord()));
        uniteCourante = analex.next();
        return nombre;
    }

    private Bloc analyseBloc() throws ErreurSyntaxique, DoubleDeclaration {
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

    private void analyseDeclaration() throws ErreurSyntaxique, DoubleDeclaration {
        String type = uniteCourante.getWord();
        analyseType();
        Token idf = uniteCourante;
        analyseIDF();
        analyseTerminal(";");
        TDS.getInstance().ajouter(new Entree(idf), new Symbole(type));
    }

    private Idf analyseIDF() throws ErreurSyntaxique {
        if (estIdf()) {
            Idf idf = new Idf(uniteCourante.getWord());
            uniteCourante = analex.next();
            return idf;
        }
        else
            throw new ErreurSyntaxique("Mauvais identificateur : " + uniteCourante);
    }

    private void analyseType() throws ErreurSyntaxique {
        if (estDeclaration())
            uniteCourante = analex.next();
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
        return uniteCourante.getWord().equals("entier"); //TODO adapter avec les tableaux
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
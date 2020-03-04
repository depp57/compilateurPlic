package plic.analyse;

import plic.exception.ErreurSyntaxique;

import java.io.File;

public class AnalyseurSyntaxique {

    private AnalyseurLexical analex;
    private String uniteCourante;

    public AnalyseurSyntaxique(File file) {
        analex = new AnalyseurLexical(file);
    }

    public void analyse() throws ErreurSyntaxique {
        uniteCourante = analex.next();
        analyseProg();
        if (!uniteCourante.equals("EOF"))
            throw new ErreurSyntaxique("EOF attendu");
    }

    private void analyseProg() throws ErreurSyntaxique {
        if (!uniteCourante.equals("programme"))
            throw new ErreurSyntaxique("programme attendu");
        uniteCourante = analex.next();

        analyseIDF();

        analyseBloc();
    }

    private void analyseInstruction() throws ErreurSyntaxique {
        //TODO à modifier : CONDITION | ITERATION
        if (estEcrire()) //TODO quand on pourra lire
            analyseEcrire();
        else if (estAffectation())
            analyseAffectation();
        else
            throw new ErreurSyntaxique("Instruction attendue, unitéCourante : " + uniteCourante);
    }

    private void analyseEcrire() throws ErreurSyntaxique {
        if (!estEcrire())
            throw new ErreurSyntaxique("'ecrire' attendu, uniteCourante : " + uniteCourante);
        uniteCourante = analex.next();
        analyseIDF();
        analyseTerminal(";");
    }

    private void analyseAffectation() throws ErreurSyntaxique {
        analyseIDF();
        if (!uniteCourante.equals(":="))
            throw new ErreurSyntaxique("':=' attendu pour une affectation");
        uniteCourante = analex.next();
        analyseExpression();
        analyseTerminal(";");
    }

    private void analyseAcces() throws ErreurSyntaxique {
        //TODO à modifier quand il y'aura les tableaux : idf [ EXPRESSION ]
        if (!estIdf())
            throw new ErreurSyntaxique("Identificateur attendu pour un accès");
        uniteCourante = analex.next();
    }

    private void analyseExpression() throws ErreurSyntaxique {
        //TODO à modifier quand on pourra affecter avec un opérateur(s)
        analyseOperande();
    }

    private void analyseOperande() throws ErreurSyntaxique {
        //TODO à modifier :  | ACCES | - EXPRESSION | non EXPRESSION | ( EXPRESSION )
        if (!estCsteEntiere())
            throw new ErreurSyntaxique("Mauvais opérande : " + uniteCourante);
        uniteCourante = analex.next();
    }

    private void analyseBloc() throws ErreurSyntaxique {
        analyseTerminal("{");

        while (estDeclaration())
            analyseDeclaration();

        analyseInstruction();
        while (estInstruction())
            analyseInstruction();

        analyseTerminal("}");
    }

    private boolean estInstruction() {
        //TODO à modifier : CONDITION | ITERATION
        //TODO quand on pourra lire
        return estEcrire() || estAffectation();
    }

    private boolean estAffectation() {
        return estIdf(); //TODO quand il y'aura les tableaux idf [ EXPRESSION ]
    }

    private boolean estEcrire() {
        return uniteCourante.equals("ecrire");
    }

    private void analyseDeclaration() throws ErreurSyntaxique {
        analyseType();
        analyseIDF();
        analyseTerminal(";");
    }

    private void analyseIDF() throws ErreurSyntaxique {
        if (estIdf())
            uniteCourante = analex.next();
        else
            throw new ErreurSyntaxique("Mauvais identificateur : " + uniteCourante);
    }

    private void analyseType() throws ErreurSyntaxique {
        if (estDeclaration())
            uniteCourante = analex.next();
        else
            throw new ErreurSyntaxique("Mauvais type : " + uniteCourante);
    }

    private boolean estDeclaration() {
        return uniteCourante.equals("entier"); //TODO adapter avec les tableaux
    }

    private void analyseTerminal(String mot) throws ErreurSyntaxique {
        if (!uniteCourante.equals(mot))
            throw new ErreurSyntaxique("Terminal " + mot + " attendu, unitéCourante : " + uniteCourante);

        uniteCourante = analex.next();
    }

    private boolean estIdf() {
        return (uniteCourante.matches("[a-zA-Z]+"));
    }

    private boolean estCsteEntiere() {
        return uniteCourante.matches("[0-9]+");
    }
}
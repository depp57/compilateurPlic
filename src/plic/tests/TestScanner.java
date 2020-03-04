package plic.tests;

import plic.analyse.AnalyseurLexical;

import java.io.File;

public class TestScanner {

    final static String path = "src/plic/sources/plic0_1.plic";

    public static void main(String[] args) {
        File file;
        if (args.length == 1)
            file = new File(args[0]);
        else
            file = new File(path);

        AnalyseurLexical sc = new AnalyseurLexical(file);
        String currentWord;

        while (!(currentWord = sc.next()).equals("EOF"))
            System.out.println(currentWord);
    }
}
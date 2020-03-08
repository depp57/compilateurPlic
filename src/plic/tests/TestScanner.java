package plic.tests;

import plic.analyse.AnalyseurLexical;
import plic.analyse.Token;

import java.io.File;

public class TestScanner {

    final static String path = "src/plic/sources/source_0_20.plic";

    public static void main(String[] args) {
        File file;
        if (args.length == 1)
            file = new File(args[0]);
        else
            file = new File(path);

        AnalyseurLexical sc = new AnalyseurLexical(file);
        Token currentWord;

        while (!(currentWord = sc.next()).getWord().equals("EOF"))
            System.out.println(currentWord);
    }
}
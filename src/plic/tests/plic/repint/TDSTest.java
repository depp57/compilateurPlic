package plic.repint;

import org.junit.Before;
import org.junit.Test;
import plic.analyse.Token;
import plic.exception.DoubleDeclaration;

public class TDSTest {

    private TDS instance;

    @Before
    public void setUp() throws Exception {
        instance = TDS.getInstance();
    }

    @Test(expected = DoubleDeclaration.class)
    public void testAjouterDoubleDeclaration() throws DoubleDeclaration {
        instance.ajouter(new Entree(new Token("test", 0)), new Symbole("entier"));
        instance.ajouter(new Entree(new Token("test", 0)), new Symbole("entier"));
    }

    @Test
    public void testAjouter() throws DoubleDeclaration {
        instance.ajouter(new Entree(new Token("test", 0)), new Symbole("entier"));
        instance.ajouter(new Entree(new Token("test1", 0)), new Symbole("entier"));

        assert instance.nbDeclarations() == 2;
    }
}
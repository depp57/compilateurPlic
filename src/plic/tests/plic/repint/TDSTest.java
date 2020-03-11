package plic.repint;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import plic.exception.DoubleDeclaration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TDSTest {

    @BeforeAll
    public static void setUp() throws DoubleDeclaration {
        TDS.getInstance().ajouter(new Entree("test"), new Symbole("entier"), 0);
    }

    @Test
    public void testAjouterDoubleDeclaration() {
        assertThrows(DoubleDeclaration.class, () ->
                TDS.getInstance().ajouter(new Entree("test"), new Symbole("entier"), 0));
    }

    @Test
    public void testAjouter() throws DoubleDeclaration {
        TDS.getInstance().ajouter(new Entree("test2"), new Symbole("entier"), 0);

        assertEquals(2, TDS.getInstance().nbDeclarations());
    }
}
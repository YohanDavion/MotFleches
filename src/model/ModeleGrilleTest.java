package model;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ModeleGrilleTest {

    private ModeleGrille modele;

    @Before
    public void setUp() {
        int n = 5;
        int m = 5;
        modele = new ModeleGrille(n, m);
    }

    @Test
    public void testInitialisationMotsDisponibles() {
        List<String> motsDisponibles = modele.getMotsDisponibles();
        assertEquals(0, motsDisponibles.size());
    }

    @Test
    public void testInitialisationGrilleVirtuelle() {
        char[][] grilleVirtuelle = modele.getGrilleVirtuelle();
        assertEquals(5, grilleVirtuelle.length);
        assertEquals(5, grilleVirtuelle[0].length);
    }
}

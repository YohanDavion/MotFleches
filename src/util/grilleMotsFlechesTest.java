package util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class grilleMotsFlechesTest {

    private GrilleMotsFleches grilleMotsFleches;

    @Before
    public void setUp() {
        int n = 5;
        int m = 5;
        grilleMotsFleches = new GrilleMotsFleches(n, m, "liste_français.txt");
    }

    @Test
    public void testInitialisationGrilleMotsFleches() {
        assertEquals(5, grilleMotsFleches.getNbLignes());
        assertEquals(5, grilleMotsFleches.getNbColonnes());
        
        assertNotNull(grilleMotsFleches.getMotsDisponibles());

        char[][] grille = grilleMotsFleches.getGrille();
        for (int i = 0; i < grilleMotsFleches.getNbLignes(); i++) {
            for (int j = 0; j < grilleMotsFleches.getNbColonnes(); j++) {
                assertEquals(' ', grille[i][j]);
            }
        }
    }
}

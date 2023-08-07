package model;

import java.util.ArrayList;
import java.util.List;

public class ModeleGrille {
    private List<String> motsDisponibles;
    private char[][] grilleVirtuelle;

    public ModeleGrille(int n, int m) {
        motsDisponibles = new ArrayList<>();
        grilleVirtuelle = new char[n][m];
    }

	public List<String> getMotsDisponibles() {
		return motsDisponibles;
	}

	public void setMotsDisponibles(List<String> motsDisponibles) {
		this.motsDisponibles = motsDisponibles;
	}

	public char[][] getGrilleVirtuelle() {
		return grilleVirtuelle;
	}

	public void setGrilleVirtuelle(char[][] grilleVirtuelle) {
		this.grilleVirtuelle = grilleVirtuelle;
	}

}

package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GrilleMotsFleches {
    private char[][] grille;
    private boolean[][] casesDefinitions;
    private boolean[][] orientationHorizontale;
    private String[][] definitions;
    private List<String> mots;
    private int nbLignes;
    private int nbColonnes;

    private List<String> motsDisponibles;

    public GrilleMotsFleches(int nbLignes, String motsFile) {
        this(nbLignes, nbLignes, motsFile);
    }

    /**
     * @version 1.0
     * @author Yohan Davion
     * @param nbLignes : Nombres de lignes présent dans la grille
     * @param nbColonnes : Nombres de colonnes présent dans la grille
     * @param motsFile : Donner le fichier sources avec tout les mots
     */
    public GrilleMotsFleches(int nbLignes, int nbColonnes, String motsFile) {
        grille = new char[nbLignes][nbColonnes];
        casesDefinitions = new boolean[nbLignes][nbColonnes];
        orientationHorizontale = new boolean[nbLignes][nbColonnes];
        definitions = new String[nbLignes][nbColonnes];
        mots = new ArrayList<>();
        chargerMots(motsFile);
        this.motsDisponibles = mots;
    	this.nbLignes = nbLignes;
        this.nbColonnes = nbColonnes;
        this.grille = new char[nbLignes][nbColonnes];
        initializeGrille();
    }
    
    /**
     * Cette méthode permet de placer un mot dans la grille, pour ce faire il suffit de spécifier sa position X et Y
     * @param startX : Début de la position X
     * @param startY : Début de la position Y
     * @param mot : Mot présent sur les cases
     * @param horizontal : Dire si le mot est horizontal
     * @param definition : Donner la définition du mot
     */
    public void placerMot(int startX, int startY, String mot, boolean horizontal, String definition) {
        int len = mot.length();
        int dx = horizontal ? 1 : 0;
        int dy = horizontal ? 0 : 1;

        if (startX + dx * len >= grille.length || startY + dy * len >= grille[0].length) {
            throw new IllegalArgumentException("Le mot ne rentre pas dans la grille.");
        }

        for (int i = 0; i < len; i++) {
            int x = startX + i * dx;
            int y = startY + i * dy;

            if (grille[x][y] != '\0' && grille[x][y] != mot.charAt(i)) {
                throw new IllegalArgumentException("Le mot ne peut pas être placé ici, mot déjà présent");
            }

            grille[x][y] = mot.charAt(i);
            casesDefinitions[x][y] = true;
            orientationHorizontale[x][y] = horizontal;
            definitions[x][y] = definition;
        }
    }

    /**
     * Méthode utilisé pour effectué des tests sur la grille, la méthode n'est pas utilisé pour la version finale
     */
    public void remplirGrilleAleatoire() {
        Random rand = new Random();

        for (int i = 0; i < grille.length; i++) {
            for (int j = 0; j < grille[0].length; j++) {
                if (grille[i][j] == '\0') {
                    grille[i][j] = (char) (rand.nextInt(26) + 'A');
                }
            }
        }
    }

    /**
     * Méthode utilisé au début du projet pour effectuer les tests en console de la génération de la grille
     */
    public void afficherGrille() {
        for (int i = 0; i < grille.length; i++) {
            for (int j = 0; j < grille[0].length; j++) {
                if (casesDefinitions[i][j]) {
                    System.out.print("(" + grille[i][j] + ")");
                } else {
                    System.out.print(" " + grille[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Méthode utilisé au début du projet pour effectuer les tests sur l'affichage des définitions pour chaque mot
     */
    public void afficherDefinitions() {
        for (int i = 0; i < grille.length; i++) {
            for (int j = 0; j < grille[0].length; j++) {
                if (casesDefinitions[i][j]) {
                    if (orientationHorizontale[i][j]) {
                        System.out.println("Horizontal (" + i + ", " + j + "): " + definitions[i][j]);
                    } else {
                        System.out.println("Vertical (" + i + ", " + j + "): " + definitions[i][j]);
                    }
                }
            }
        }
    }
    
    
    public void ajouterDefinition(int x, int y, String definition) {
        if (x < 0 || x >= grille.length || y < 0 || y >= grille[0].length) {
            throw new IllegalArgumentException("Les coordonnées de la case sont hors de la grille.");
        }

        if (casesDefinitions[x][y]) {
            definitions[x][y] += "\n" + definition;
        } else {
            casesDefinitions[x][y] = true;
            definitions[x][y] = definition;
        }
    }

    public void supprimerDefinition(int x, int y) {
        if (x < 0 || x >= grille.length || y < 0 || y >= grille[0].length) {
            throw new IllegalArgumentException("Les coordonnées de la case sont hors de la grille.");
        }

        casesDefinitions[x][y] = false;
        definitions[x][y] = null;
    }

    /**
     * Détermine avec une position données si les cases alentours sont déjà remplis
     * @param x
     * @param y
     * @return
     */
    private boolean aVoisinsRemplis(int x, int y) {
        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            if (nx >= 0 && nx < grille.length && ny >= 0 && ny < grille[0].length) {
                if (grille[nx][ny] != '\0') {
                    return true;
                }
            }
        }

        return false;
    }

    public void mettreAJourDefinitions() {
        for (int i = 0; i < grille.length; i++) {
            for (int j = 0; j < grille[0].length; j++) {
                if (casesDefinitions[i][j] && !aVoisinsRemplis(i, j)) {
                    casesDefinitions[i][j] = false;
                    definitions[i][j] = null;
                }
            }
        }
    }
    
    private void chargerMots(String motsFile) {
        Fichier fichier = new Fichier();
        mots = fichier.getListeDeMots();
    }

    public List<String> rechercherMotParTaille(int taille) {
        List<String> resultats = new ArrayList<>();
        for (String mot : mots) {
            if (mot.length() == taille) {
                resultats.add(mot);
            }
        }
        return resultats;
    }

    public List<String> rechercherMotParLettreDebut(char lettre) {
        List<String> resultats = new ArrayList<>();
        lettre = Character.toUpperCase(lettre);
        for (String mot : mots) {
            if (mot.charAt(0) == lettre) {
                resultats.add(mot);
            }
        }
        return resultats;
    }

    public List<String> rechercherMotParLettreEnPosition(char lettre, int position) {
        List<String> resultats = new ArrayList<>();
        lettre = Character.toUpperCase(lettre);
        for (String mot : mots) {
            if (position > 0 && position <= mot.length() && mot.charAt(position - 1) == lettre) {
                resultats.add(mot);
            }
        }
        return resultats;
    }

    public List<String> rechercherMotParLettresEnPositions(char[] lettres, int[] positions) {
        List<String> resultats = new ArrayList<>();
        for (String mot : mots) {
            boolean match = true;
            for (int i = 0; i < lettres.length; i++) {
                int position = positions[i];
                char lettre = Character.toUpperCase(lettres[i]);
                if (position > 0 && position <= mot.length() && mot.charAt(position - 1) != lettre) {
                    match = false;
                    break;
                }
            }
            if (match) {
                resultats.add(mot);
            }
        }
        return resultats;
    }

	public boolean[][] getCasesDefinitions() {
		return casesDefinitions;
	}

	public void setCasesDefinitions(boolean[][] casesDefinitions) {
		this.casesDefinitions = casesDefinitions;
	}

	public boolean[][] getOrientationHorizontale() {
		return orientationHorizontale;
	}

	public void setOrientationHorizontale(boolean[][] orientationHorizontale) {
		this.orientationHorizontale = orientationHorizontale;
	}

	public String[][] getDefinitions() {
		return definitions;
	}

	public void setDefinitions(String[][] definitions) {
		this.definitions = definitions;
	}
	
	private void initializeGrille() {
        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {
                grille[i][j] = ' ';
            }
        }
    }

    public int getNbLignes() {
        return nbLignes;
    }

    public int getNbColonnes() {
        return nbColonnes;
    }

    public List<String> getMotsDisponibles() {
        return motsDisponibles;
    }

    public char[][] getGrille() {
        return grille;
    }

    public boolean ajouterMot(String mot, int ligne, int colonne, boolean orientationHorizontale) {
        if (mot == null || mot.isEmpty() || ligne < 0 || ligne >= nbLignes || colonne < 0 || colonne >= nbColonnes) {
            return false;
        }

        int longueurMot = mot.length();

        if (orientationHorizontale) {
            if (colonne + longueurMot > nbColonnes) {
                return false;
            }

            for (int i = 0; i < longueurMot; i++) {
                if (grille[ligne][colonne + i] != ' ') {
                    return false;
                }
                grille[ligne][colonne + i] = mot.charAt(i);
            }
        } else {
            if (ligne + longueurMot > nbLignes) {
                return false;
            }

            for (int i = 0; i < longueurMot; i++) {
                if (grille[ligne + i][colonne] != ' ') {
                    return false;
                }
                grille[ligne + i][colonne] = mot.charAt(i);
            }
        }

        return true;
    }
	
}

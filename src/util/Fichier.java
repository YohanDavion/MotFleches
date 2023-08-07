package util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Fichier {
	
	private static final String FICHIER = "util/liste_francais.txt";

	private ArrayList <String> listeDeMots = null;
	
	public Fichier() {
		super();
		listeDeMots = new ArrayList<>();
		ouvrirFichier();
		
	}
	
	private void ouvrirFichier(){
		try{
			InputStream flux = Fichier.class.getResourceAsStream(FICHIER);
			InputStreamReader lecture=new InputStreamReader(flux);
			BufferedReader buff = new BufferedReader(lecture);
			String ligne;
			while ((ligne=buff.readLine())!=null){
				listeDeMots.add(ligne);
			}
			buff.close(); 
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}
	}

	
	public void printMots(){
		System.out.println("affichage des mots pr�sents dans le dictionnaire");
		for (String m  : listeDeMots) {
			System.out.println(m);
		}
	}
		
	public void printMotsDebut(String d){
			System.out.printf("affichage des mots commen�ant par %s%n", d);
			for (String m  : listeDeMots) {
				if (m.startsWith(d)){
					System.out.println(m);
				}
			}
	}

	public ArrayList<String> getListeDeMots() {
		return listeDeMots;
	}

	public void setListeDeMots(ArrayList<String> listeDeMots) {
		this.listeDeMots = listeDeMots;
	}
		
	public static String getFichier() {
		return FICHIER;
	}
}

package main;

import javax.swing.SwingUtilities;

import ihm.GrilleMotsFlechesUI;

public class main {
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GrilleMotsFlechesUI(10, 10, "../util/liste_français.txt");
            }
        });
    }
}

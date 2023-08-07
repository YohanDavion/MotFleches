package ihm;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import util.GrilleMotsFleches;

public class GrilleMotsFlechesUI extends JFrame {
    private GrilleMotsFleches grilleMotsFleches;
    private JTextArea textAreaDefinitions;
    private JTextField textFieldMot;
    private JTextField textFieldTaille;
    private JButton buttonAjouterMot;
    private JButton buttonRechercherParTaille;
    private JButton buttonRechercherParLettreDebut;

    public GrilleMotsFlechesUI(int n, int m, String motsFile) {
        grilleMotsFleches = new GrilleMotsFleches(n, m, motsFile);
        initUI();
    }

    private void initUI() {
        setTitle("Application de création de grilles de mots fléchés");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        textAreaDefinitions = new JTextArea(10, 30);
        textAreaDefinitions.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textAreaDefinitions);
        add(scrollPane, BorderLayout.CENTER);

        JPanel controlsPanel = new JPanel();
        controlsPanel.setLayout(new FlowLayout());

        textFieldMot = new JTextField(10);
        textFieldTaille = new JTextField(5);
        buttonAjouterMot = new JButton("Ajouter Mot");
        buttonAjouterMot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterMot();
            }
        });

        buttonRechercherParTaille = new JButton("Rechercher par Taille");
        buttonRechercherParTaille.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rechercherParTaille();
            }
        });

        buttonRechercherParLettreDebut = new JButton("Rechercher par Lettre de Début");
        buttonRechercherParLettreDebut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rechercherParLettreDebut();
            }
        });

        controlsPanel.add(new JLabel("Mot : "));
        controlsPanel.add(textFieldMot);
        controlsPanel.add(new JLabel("Taille : "));
        controlsPanel.add(textFieldTaille);
        controlsPanel.add(buttonAjouterMot);
        controlsPanel.add(buttonRechercherParTaille);
        controlsPanel.add(buttonRechercherParLettreDebut);

        add(controlsPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void ajouterMot() {
        String mot = textFieldMot.getText().toUpperCase();
        String definition = JOptionPane.showInputDialog(this, "Entrez la définition du mot :");
        boolean horizontal = JOptionPane.showConfirmDialog(this, "Le mot est-il horizontal ?",
                "Direction du mot", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
        grilleMotsFleches.placerMot(0, 0, mot, horizontal, definition);
        afficherDefinitions();
    }

    private void rechercherParTaille() {
        String tailleStr = textFieldTaille.getText();
        try {
            int taille = Integer.parseInt(tailleStr);
            List<String> resultats = grilleMotsFleches.rechercherMotParTaille(taille);
            afficherResultatsRecherche("Mots de taille " + taille, resultats);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer un nombre valide pour la taille.");
        }
    }

    private void rechercherParLettreDebut() {
        String lettre = JOptionPane.showInputDialog(this, "Entrez la lettre de début :").toUpperCase();
        List<String> resultats = grilleMotsFleches.rechercherMotParLettreDebut(lettre.charAt(0));
        afficherResultatsRecherche("Mots commençant par " + lettre, resultats);
    }

    private void afficherDefinitions() {
        textAreaDefinitions.setText("");
        for (int i = 0; i < grilleMotsFleches.getDefinitions().length; i++) {
            for (int j = 0; j < grilleMotsFleches.getDefinitions()[0].length; j++) {
                if (grilleMotsFleches.getCasesDefinitions()[i][j]) {
                    String orientation = grilleMotsFleches.getOrientationHorizontale()[i][j] ? "Horizontal" : "Vertical";
                    String definition = grilleMotsFleches.getDefinitions()[i][j];
                    textAreaDefinitions.append("(" + i + ", " + j + "): " + orientation + " - " + definition + "\n");
                }
            }
        }
    }

    private void afficherResultatsRecherche(String titre, List<String> resultats) {
        if (resultats.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Aucun mot trouvé pour les critères spécifiés.");
        } else {
            String message = String.join("\n", resultats);
            JOptionPane.showMessageDialog(this, titre + " :\n" + message);
        }
    }
}


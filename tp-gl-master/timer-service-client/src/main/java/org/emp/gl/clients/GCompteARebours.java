package org.emp.gl.clients;

import java.beans.PropertyChangeEvent;
import javax.swing.*;
import java.awt.*;
import org.emp.gl.timer.service.TimerChangeListener;
import org.emp.gl.timer.service.TimerService;

public class GCompteARebours extends JFrame implements TimerChangeListener {

    private TimerService timerService;
    private int valeur;
    private final int valeurInitiale;
    private String nom;

    private JLabel labelCompte;
    private JProgressBar progressBar;

    public GCompteARebours(String nom, TimerService timerService, int valeurInitiale) {
        this.nom = nom;
        this.timerService = timerService;
        this.valeur = valeurInitiale;
        this.valeurInitiale = valeurInitiale;

        // Configuration de la fenêtre
        setTitle("Compte à Rebours - " + nom);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(350, 150);
        setLocationRelativeTo(null);
        setResizable(false);

        // Layout
        setLayout(new BorderLayout());

        // Label du nom
        JLabel labelNom = new JLabel(nom, SwingConstants.CENTER);
        labelNom.setFont(new Font("Arial", Font.BOLD, 16));
        add(labelNom, BorderLayout.NORTH);

        // Label du compte
        labelCompte = new JLabel(String.valueOf(valeur), SwingConstants.CENTER);
        labelCompte.setFont(new Font("Arial", Font.BOLD, 32));
        labelCompte.setForeground(getCouleur(valeur, valeurInitiale));
        add(labelCompte, BorderLayout.CENTER);

        // Barre de progression
        progressBar = new JProgressBar(0, valeurInitiale);
        progressBar.setValue(valeur);
        progressBar.setStringPainted(true);
        add(progressBar, BorderLayout.SOUTH);

        // S'inscrire comme listener
        timerService.addTimeChangeListener(this);

        // Afficher la fenêtre
        setVisible(true);

        System.out.println("Compte à Rebours Graphique " + nom + " lancé : " + valeurInitiale);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (TimerChangeListener.SECONDE_PROP.equals(evt.getPropertyName())) {
            if (valeur > 0) {
                valeur--;
                mettreAJourAffichage();

                if (valeur == 0) {
                    SwingUtilities.invokeLater(() -> {
                        labelCompte.setForeground(Color.RED);
                        labelCompte.setText("TERMINÉ !");
                        JOptionPane.showMessageDialog(this,
                                "Compte à rebours " + nom + " terminé !",
                                "Terminé",
                                JOptionPane.INFORMATION_MESSAGE);
                        timerService.removeTimeChangeListener(this);
                    });
                }
            }
        }
    }

    private void mettreAJourAffichage() {
        SwingUtilities.invokeLater(() -> {
            labelCompte.setText(String.valueOf(valeur));
            labelCompte.setForeground(getCouleur(valeur, valeurInitiale));
            progressBar.setValue(valeur);
        });
    }

    private Color getCouleur(int valeurActuelle, int valeurMax) {
        float ratio = (float) valeurActuelle / valeurMax;
        if (ratio > 0.5) return Color.GREEN;
        if (ratio > 0.2) return Color.ORANGE;
        return Color.RED;
    }

    public void fermer() {
        timerService.removeTimeChangeListener(this);
        dispose();
    }
}
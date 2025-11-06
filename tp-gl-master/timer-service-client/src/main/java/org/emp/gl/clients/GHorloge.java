package org.emp.gl.clients;

import java.beans.PropertyChangeEvent;
import javax.swing.*;
import java.awt.*;
import org.emp.gl.timer.service.TimerChangeListener;
import org.emp.gl.timer.service.TimerService;

public class GHorloge extends JFrame implements TimerChangeListener {

    private TimerService timerService;
    private JLabel labelHeure;
    private String nom;

    public GHorloge(String nom, TimerService timerService) {
        this.nom = nom;
        this.timerService = timerService;

        // Configuration de la fenêtre
        setTitle("Horloge - " + nom);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);
        setResizable(false);

        // Création du label pour afficher l'heure
        labelHeure = new JLabel("", SwingConstants.CENTER);
        labelHeure.setFont(new Font("Arial", Font.BOLD, 24));
        labelHeure.setForeground(Color.BLUE);

        // Mise à jour initiale
        mettreAJourHeure();

        // Ajout au layout
        setLayout(new BorderLayout());
        add(labelHeure, BorderLayout.CENTER);

        // Ajout d'un label pour le nom
        JLabel labelNom = new JLabel(nom, SwingConstants.CENTER);
        add(labelNom, BorderLayout.NORTH);

        // S'inscrire comme listener
        timerService.addTimeChangeListener(this);

        // Afficher la fenêtre
        setVisible(true);

        System.out.println("Horloge Graphique " + nom + " initialisée !");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (TimerChangeListener.SECONDE_PROP.equals(evt.getPropertyName())) {
            mettreAJourHeure();
        }
    }

    private void mettreAJourHeure() {
        SwingUtilities.invokeLater(() -> {
            String heure = String.format("%02d:%02d:%02d",
                    timerService.getHeures(),
                    timerService.getMinutes(),
                    timerService.getSecondes());
            labelHeure.setText(heure);
        });
    }

    public void fermer() {
        timerService.removeTimeChangeListener(this);
        dispose();
    }
}
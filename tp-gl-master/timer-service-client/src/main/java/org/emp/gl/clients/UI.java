package org.emp.gl.clients;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.emp.gl.timer.service.TimerService;

public class UI extends JFrame {

    private TimerService timerService;

    public UI(TimerService timerService) {
        this.timerService = timerService;

        // Configuration de la fenêtre principale
        setTitle("Dashboard - Observateur Temps Réel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Layout principal
        setLayout(new BorderLayout());

        // ======== PANEL DE CONTRÔLE ========
        JPanel panelControle = new JPanel();
        panelControle.setBackground(new Color(245, 247, 250));

        JButton btnNouvelleHorloge = createModernButton("➕ Nouvelle Horloge", new Color(52, 152, 219));
        JButton btnNouveauCompte = createModernButton("⏱ Nouveau Compte à Rebours", new Color(231, 76, 60));

        // Action : création d'une horloge
        btnNouvelleHorloge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nom = JOptionPane.showInputDialog("Nom de l'horloge:");
                if (nom != null && !nom.trim().isEmpty()) {
                    new GHorloge(nom, timerService);
                }
            }
        });

        // Action : création d’un compte à rebours
        btnNouveauCompte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nom = JOptionPane.showInputDialog("Nom du compte:");
                if (nom != null && !nom.trim().isEmpty()) {
                    try {
                        String valeurStr = JOptionPane.showInputDialog("Valeur initiale (en secondes):");
                        int valeur = Integer.parseInt(valeurStr);
                        new GCompteARebours(nom, timerService, valeur);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Valeur invalide !");
                    }
                }
            }
        });

        panelControle.add(btnNouvelleHorloge);
        panelControle.add(btnNouveauCompte);
        add(panelControle, BorderLayout.NORTH);

        // ======== ZONE D’INFORMATION ========
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setBackground(new Color(250, 250, 250));
     
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textArea.setMargin(new Insets(10, 10, 10, 10));
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        // ======== BARRE DE STATUT ========
        JLabel labelStatus = new JLabel("TP1", SwingConstants.CENTER);
        labelStatus.setFont(new Font("Segoe UI", Font.BOLD, 12));
        labelStatus.setForeground(Color.DARK_GRAY);
        labelStatus.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(labelStatus, BorderLayout.SOUTH);

        setVisible(true);
        System.out.println("Dashboard Principal initialisé !");
    }

    /**
     * Crée un bouton moderne avec couleur, coins arrondis et effet survol.
     */
    private JButton createModernButton(String text, Color baseColor) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setBackground(baseColor);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);

        // Effet de survol
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(baseColor.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(baseColor);
            }
        });

        // Coins arrondis
        button.setBorder(new RoundedBorder(15));

        return button;
    }

    /**
     * Classe interne pour créer des boutons arrondis
     */
    private static class RoundedBorder implements javax.swing.border.Border {
        private int radius;

        RoundedBorder(int radius) {
            this.radius = radius;
        }

        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius);
        }

        public boolean isBorderOpaque() {
            return false;
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.setColor(new Color(220, 220, 220));
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }
}

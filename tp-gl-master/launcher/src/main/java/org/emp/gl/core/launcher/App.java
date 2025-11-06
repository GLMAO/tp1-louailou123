package org.emp.gl.core.launcher;

import org.emp.gl.clients.Horloge;
import org.emp.gl.clients.CompteARebours;
import org.emp.gl.clients.GHorloge;
import org.emp.gl.clients.GCompteARebours;
import org.emp.gl.clients.UI;
import org.emp.gl.time.service.impl.DummyTimeServiceImpl;
import org.emp.gl.timer.service.TimerService;

import javax.swing.*;

public class App {
    public static void main(String[] args) throws InterruptedException {
        // Démarrer l'interface graphique dans l'EDT Swing
        SwingUtilities.invokeLater(() -> {
            try {
                // CORRECTION : utiliser getSystemLookAndFeelClassName() au lieu de getSystemLookAndFeel()
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            DummyTimeServiceImpl timer = new DummyTimeServiceImpl();

            // Créer le dashboard principal
            new UI(timer);

            // Créer quelques composants graphiques par défaut
            new GHorloge("Horloge Principale", timer);
            new GCompteARebours("Compte Démo", timer, 15);

            // Garder les composants console existants
            new Horloge("Console 1", timer);
            new Horloge("Console 2", timer);
            new CompteARebours("Console C1", timer, 5);
            new CompteARebours("Console C1", timer, 10);
            new CompteARebours("Console C1", timer, 12);
            new CompteARebours("Console C1", timer, 14);
            new CompteARebours("Console C1", timer, 16);
            new CompteARebours("Console C1", timer, 18);
            new CompteARebours("Console C1", timer, 20);

        });

        // Laisser le programme tourner plus longtemps pour voir les interfaces graphiques
        Thread.sleep(30000);
        System.out.println("Programme terminé ✅");
    }
}
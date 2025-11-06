package org.emp.gl.clients;

import java.beans.PropertyChangeEvent;
import org.emp.gl.timer.service.TimerChangeListener;
import org.emp.gl.timer.service.TimerService;

public class CompteARebours implements TimerChangeListener {

    private int valeur;
    private final TimerService timerService;
    private final String name;

    public CompteARebours(String name, TimerService timerService, int valeurInitiale) {
        this.name = name;
        this.valeur = valeurInitiale;
        this.timerService = timerService;
        this.timerService.addTimeChangeListener(this);
        System.out.println("CompteARebours " + name + " lancé : " + valeurInitiale);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (TimerChangeListener.SECONDE_PROP.equals(evt.getPropertyName())) {
            if (valeur > 0) {
                System.out.println(name + " : " + valeur--);
            } else {
                System.out.println(name + " terminé !");
                timerService.removeTimeChangeListener(this);
            }
        }
    }
}

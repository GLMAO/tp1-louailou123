package org.emp.gl.time.service.impl;

import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;
import java.beans.PropertyChangeSupport;

import org.emp.gl.timer.service.TimerService;

public class DummyTimeServiceImpl implements TimerService {

    int dixiemeDeSeconde;
    int secondes;
    int minutes;
    int heures;

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public DummyTimeServiceImpl() {
        setTimeValues();
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                timeChanged();
            }
        };
        timer.scheduleAtFixedRate(task, 100, 100);
    }

    private void setTimeValues() {
        LocalTime now = LocalTime.now();
        setDixiemeDeSeconde(now.getNano() / 100_000_000);
        setSecondes(now.getSecond());
        setMinutes(now.getMinute());
        setHeures(now.getHour());
    }

    private void timeChanged() {
        setTimeValues();
    }

    @Override
    public void addTimeChangeListener(org.emp.gl.timer.service.TimerChangeListener l) {
        pcs.addPropertyChangeListener(l);
    }

    @Override
    public void removeTimeChangeListener(org.emp.gl.timer.service.TimerChangeListener l) {
        pcs.removePropertyChangeListener(l);
    }

    // --- setters qui notifient via PropertyChangeSupport ---
    public void setDixiemeDeSeconde(int newVal) {
        int old = this.dixiemeDeSeconde;
        this.dixiemeDeSeconde = newVal;
        pcs.firePropertyChange("dixieme", old, newVal);
    }

    public void setSecondes(int newVal) {
        int old = this.secondes;
        this.secondes = newVal;
        pcs.firePropertyChange("seconde", old, newVal);
    }

    public void setMinutes(int newVal) {
        int old = this.minutes;
        this.minutes = newVal;
        pcs.firePropertyChange("minute", old, newVal);
    }

    public void setHeures(int newVal) {
        int old = this.heures;
        this.heures = newVal;
        pcs.firePropertyChange("heure", old, newVal);
    }

    @Override
    public int getDixiemeDeSeconde() { return dixiemeDeSeconde; }
    @Override
    public int getSecondes() { return secondes; }
    @Override
    public int getMinutes() { return minutes; }
    @Override
    public int getHeures() { return heures; }
}

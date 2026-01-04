package com.airtribe.meditrack.util;

import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.constants.AppointmentStatus;

import java.util.ArrayList;
import java.util.List;

public interface AppointmentObserver {
    /**
     * Update method called when appointment status changes
     * @param appointment Updated appointment
     */
    void update(Appointment appointment);
}

class AppointmentSubject {
    private final List<AppointmentObserver> observers = new ArrayList<>();
    private Appointment appointment;
    
    /**
     * Add observer
     * @param observer Observer to add
     */
    public void addObserver(AppointmentObserver observer) {
        observers.add(observer);
    }
    
    /**
     * Remove observer
     * @param observer Observer to remove
     */
    public void removeObserver(AppointmentObserver observer) {
        observers.remove(observer);
    }
    
    /**
     * Notify all observers
     * @param appointment Updated appointment
     */
    public void notifyObservers(Appointment appointment) {
        this.appointment = appointment;
        for (AppointmentObserver observer : observers) {
            observer.update(appointment);
        }
    }
}

class AppointmentNotificationObserver implements AppointmentObserver {
    
    @Override
    public void update(Appointment appointment) {
        if (appointment.getStatus() == AppointmentStatus.CONFIRMED) {
            System.out.println("\n[NOTIFICATION] Appointment #" + appointment.getId() + 
                    " has been confirmed for " + appointment.getAppointmentDateTime());
        } else if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
            System.out.println("\n[NOTIFICATION] Appointment #" + appointment.getId() + 
                    " has been cancelled.");
        }
    }
}

class AppointmentReminderObserver implements AppointmentObserver {
    
    @Override
    public void update(Appointment appointment) {
        if (appointment.getStatus() == AppointmentStatus.CONFIRMED) {
            // Check if appointment is within 24 hours
            java.time.Duration duration = java.time.Duration.between(
                    java.time.LocalDateTime.now(),
                    appointment.getAppointmentDateTime()
            );
            
            if (duration.toHours() <= 24 && duration.toHours() > 0) {
                System.out.println("\n[REMINDER] You have an appointment #" + 
                        appointment.getId() + " in " + duration.toHours() + " hours.");
            }
        }
    }
}


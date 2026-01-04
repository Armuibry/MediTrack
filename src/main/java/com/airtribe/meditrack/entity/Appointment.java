package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.constants.AppointmentStatus;
import java.time.LocalDateTime;
import java.util.Objects;


public class Appointment implements Cloneable {
    
    private int id;
    private int patientId;
    private int doctorId;
    private LocalDateTime appointmentDateTime;
    private AppointmentStatus status;
    private String reason;
    private String notes;
    
    public Appointment() {
        this.status = AppointmentStatus.PENDING;
    }
    
    public Appointment(int id, int patientId, int doctorId, 
                      LocalDateTime appointmentDateTime, AppointmentStatus status,
                      String reason, String notes) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentDateTime = appointmentDateTime;
        this.status = status != null ? status : AppointmentStatus.PENDING;
        this.reason = reason;
        this.notes = notes;
    }
        
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getPatientId() {
        return patientId;
    }
    
    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }
    
    public int getDoctorId() {
        return doctorId;
    }
    
    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }
    
    public LocalDateTime getAppointmentDateTime() {
        return appointmentDateTime;
    }
    
    public void setAppointmentDateTime(LocalDateTime appointmentDateTime) {
        this.appointmentDateTime = appointmentDateTime;
    }
    
    public AppointmentStatus getStatus() {
        return status;
    }
    
    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }
    
    public String getReason() {
        return reason;
    }
    
    public void setReason(String reason) {
        this.reason = reason;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void cancel() {
        this.status = AppointmentStatus.CANCELLED;
    }

    public void confirm() {
        this.status = AppointmentStatus.CONFIRMED;
    }
    
    /**
     * Deep clone implementation
     * @return Cloned Appointment object
     * @throws CloneNotSupportedException if cloning fails
     */
    @Override
    public Appointment clone() throws CloneNotSupportedException {
        Appointment cloned = (Appointment) super.clone();
        return cloned;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return id == that.id;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", doctorId=" + doctorId +
                ", appointmentDateTime=" + appointmentDateTime +
                ", status=" + status +
                ", reason='" + reason + '\'' +
                '}';
    }
}


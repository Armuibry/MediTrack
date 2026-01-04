package com.airtribe.meditrack.controller;

import com.airtribe.meditrack.constants.AppointmentStatus;
import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.entity.BillSummary;
import com.airtribe.meditrack.exception.AppointmentNotFoundException;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.service.AppointmentService;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;


public class AppointmentController {
    
    private final AppointmentService appointmentService;

    public AppointmentController() {
        this.appointmentService = new AppointmentService();
    }
    
    /**
     * Create a new appointment
     * @param patientId Patient ID
     * @param doctorId Doctor ID
     * @param appointmentDateTime Appointment date and time
     * @param reason Reason for appointment
     * @param notes Additional notes
     * @return Created appointment
     * @throws InvalidDataException if validation fails
     * @throws SQLException if database operation fails
     */
    public Appointment createAppointment(int patientId, int doctorId,
                                        LocalDateTime appointmentDateTime,
                                        String reason, String notes)
            throws InvalidDataException, SQLException {
        return appointmentService.createAppointment(patientId, doctorId,
                appointmentDateTime, reason, notes);
    }
    
    /**
     * Get appointment by ID
     * @param id Appointment ID
     * @return Appointment if found
     * @throws AppointmentNotFoundException if not found
     * @throws InvalidDataException if validation fails
     * @throws SQLException if database operation fails
     */
    public Appointment getAppointment(int id)
            throws AppointmentNotFoundException, InvalidDataException, SQLException {
        return appointmentService.findAppointmentById(id);
    }
    
    /**
     * Get all appointments
     * @return List of all appointments
     * @throws SQLException if database operation fails
     */
    public List<Appointment> getAllAppointments() throws SQLException {
        return appointmentService.getAllAppointments();
    }
    
    /**
     * Get appointments by patient ID
     * @param patientId Patient ID
     * @return List of appointments
     * @throws InvalidDataException if validation fails
     * @throws SQLException if database operation fails
     */
    public List<Appointment> getAppointmentsByPatientId(int patientId)
            throws InvalidDataException, SQLException {
        return appointmentService.getAppointmentsByPatientId(patientId);
    }
    
    /**
     * Get appointments by doctor ID
     * @param doctorId Doctor ID
     * @return List of appointments
     * @throws InvalidDataException if validation fails
     * @throws SQLException if database operation fails
     */
    public List<Appointment> getAppointmentsByDoctorId(int doctorId)
            throws InvalidDataException, SQLException {
        return appointmentService.getAppointmentsByDoctorId(doctorId);
    }
    
    /**
     * Cancel appointment
     * @param appointmentId Appointment ID
     * @return Cancelled appointment
     * @throws AppointmentNotFoundException if not found
     * @throws InvalidDataException if validation fails
     * @throws SQLException if database operation fails
     */
    public Appointment cancelAppointment(int appointmentId)
            throws AppointmentNotFoundException, InvalidDataException, SQLException {
        return appointmentService.cancelAppointment(appointmentId);
    }
    
    /**
     * Confirm appointment
     * @param appointmentId Appointment ID
     * @return Confirmed appointment
     * @throws AppointmentNotFoundException if not found
     * @throws InvalidDataException if validation fails
     * @throws SQLException if database operation fails
     */
    public Appointment confirmAppointment(int appointmentId)
            throws AppointmentNotFoundException, InvalidDataException, SQLException {
        return appointmentService.confirmAppointment(appointmentId);
    }
    
    /**
     * Create bill for appointment
     * @param appointmentId Appointment ID
     * @return Created bill
     * @throws AppointmentNotFoundException if appointment not found
     * @throws InvalidDataException if validation fails
     * @throws SQLException if database operation fails
     */
    public Bill createBill(int appointmentId)
            throws AppointmentNotFoundException, InvalidDataException, SQLException {
        return appointmentService.createBill(appointmentId);
    }
    
    /**
     * Generate bill summary
     * @param appointmentId Appointment ID
     * @return BillSummary object
     * @throws AppointmentNotFoundException if appointment not found
     * @throws InvalidDataException if validation fails
     * @throws SQLException if database operation fails
     */
    public BillSummary generateBillSummary(int appointmentId)
            throws AppointmentNotFoundException, InvalidDataException, SQLException {
        return appointmentService.generateBillSummary(appointmentId);
    }
    
    /**
     * Update appointment
     * @param appointment Appointment to update
     * @return Updated appointment
     * @throws InvalidDataException if validation fails
     * @throws SQLException if database operation fails
     */
    public Appointment updateAppointment(Appointment appointment)
            throws InvalidDataException, SQLException {
        return appointmentService.updateAppointment(appointment);
    }
    
    /**
     * Delete appointment
     * @param id Appointment ID
     * @return true if deleted
     * @throws InvalidDataException if validation fails
     * @throws SQLException if database operation fails
     */
    public boolean deleteAppointment(int id) throws InvalidDataException, SQLException {
        return appointmentService.deleteAppointment(id);
    }
}


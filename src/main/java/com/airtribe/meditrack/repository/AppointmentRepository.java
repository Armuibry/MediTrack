package com.airtribe.meditrack.repository;

import com.airtribe.meditrack.constants.AppointmentStatus;
import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.util.DatabaseUtil;
import com.airtribe.meditrack.util.DateUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentRepository {
    
    /**
     * Create a new appointment
     * @param appointment Appointment to create
     * @return Created appointment with ID
     * @throws SQLException if database operation fails
     */
    public Appointment create(Appointment appointment) throws SQLException {
        String sql = "INSERT INTO appointments (id, patient_id, doctor_id, appointment_datetime, " +
                     "status, reason, notes) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, appointment.getId());
            stmt.setInt(2, appointment.getPatientId());
            stmt.setInt(3, appointment.getDoctorId());
            stmt.setString(4, DateUtil.formatDateTime(appointment.getAppointmentDateTime()));
            stmt.setString(5, appointment.getStatus().name());
            stmt.setString(6, appointment.getReason());
            stmt.setString(7, appointment.getNotes());
            
            stmt.executeUpdate();
            return appointment;
        }
    }
    
    /**
     * Find appointment by ID
     * @param id Appointment ID
     * @return Appointment if found, null otherwise
     * @throws SQLException if database operation fails
     */
    public Appointment findById(int id) throws SQLException {
        String sql = "SELECT * FROM appointments WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToAppointment(rs);
                }
            }
        }
        return null;
    }
    
    /**
     * Find all appointments
     * @return List of all appointments
     * @throws SQLException if database operation fails
     */
    public List<Appointment> findAll() throws SQLException {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments";
        
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                appointments.add(mapResultSetToAppointment(rs));
            }
        }
        return appointments;
    }
    
    /**
     * Find appointments by patient ID
     * @param patientId Patient ID
     * @return List of appointments
     * @throws SQLException if database operation fails
     */
    public List<Appointment> findByPatientId(int patientId) throws SQLException {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE patient_id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, patientId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    appointments.add(mapResultSetToAppointment(rs));
                }
            }
        }
        return appointments;
    }
    
    /**
     * Find appointments by doctor ID
     * @param doctorId Doctor ID
     * @return List of appointments
     * @throws SQLException if database operation fails
     */
    public List<Appointment> findByDoctorId(int doctorId) throws SQLException {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE doctor_id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, doctorId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    appointments.add(mapResultSetToAppointment(rs));
                }
            }
        }
        return appointments;
    }
    
    /**
     * Update appointment
     * @param appointment Appointment to update
     * @return Updated appointment
     * @throws SQLException if database operation fails
     */
    public Appointment update(Appointment appointment) throws SQLException {
        String sql = "UPDATE appointments SET patient_id = ?, doctor_id = ?, appointment_datetime = ?, " +
                     "status = ?, reason = ?, notes = ? WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, appointment.getPatientId());
            stmt.setInt(2, appointment.getDoctorId());
            stmt.setString(3, DateUtil.formatDateTime(appointment.getAppointmentDateTime()));
            stmt.setString(4, appointment.getStatus().name());
            stmt.setString(5, appointment.getReason());
            stmt.setString(6, appointment.getNotes());
            stmt.setInt(7, appointment.getId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0 ? appointment : null;
        }
    }
    
    /**
     * Delete appointment by ID
     * @param id Appointment ID
     * @return true if deleted, false otherwise
     * @throws SQLException if database operation fails
     */
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM appointments WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
    
    /**
     * Map ResultSet to Appointment object
     * @param rs ResultSet
     * @return Appointment object
     * @throws SQLException if mapping fails
     */
    private Appointment mapResultSetToAppointment(ResultSet rs) throws SQLException {
        Appointment appointment = new Appointment();
        appointment.setId(rs.getInt("id"));
        appointment.setPatientId(rs.getInt("patient_id"));
        appointment.setDoctorId(rs.getInt("doctor_id"));
        
        String dateTimeStr = rs.getString("appointment_datetime");
        if (dateTimeStr != null) {
            appointment.setAppointmentDateTime(DateUtil.parseDateTime(dateTimeStr));
        }
        
        String statusStr = rs.getString("status");
        if (statusStr != null) {
            appointment.setStatus(AppointmentStatus.valueOf(statusStr));
        }
        
        appointment.setReason(rs.getString("reason"));
        appointment.setNotes(rs.getString("notes"));
        
        return appointment;
    }
}


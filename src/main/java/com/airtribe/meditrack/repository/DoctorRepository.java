package com.airtribe.meditrack.repository;

import com.airtribe.meditrack.constants.Specialization;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.util.DatabaseUtil;
import com.airtribe.meditrack.util.DateUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DoctorRepository {
    
    /**
     * Create a new doctor
     * @param doctor Doctor to create
     * @return Created doctor with ID
     * @throws SQLException if database operation fails
     */
    public Doctor create(Doctor doctor) throws SQLException {
        String sql = "INSERT INTO doctors (id, name, date_of_birth, email, phone_number, " +
                     "specialization, consultation_fee, experience_years, license_number) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, doctor.getId());
            stmt.setString(2, doctor.getName());
            stmt.setString(3, DateUtil.formatDate(doctor.getDateOfBirth()));
            stmt.setString(4, doctor.getEmail());
            stmt.setString(5, doctor.getPhoneNumber());
            stmt.setString(6, doctor.getSpecialization().name());
            stmt.setDouble(7, doctor.getConsultationFee());
            stmt.setInt(8, doctor.getExperienceYears());
            stmt.setString(9, doctor.getLicenseNumber());
            
            stmt.executeUpdate();
            return doctor;
        }
    }
    
    /**
     * Find doctor by ID
     * @param id Doctor ID
     * @return Doctor if found, null otherwise
     * @throws SQLException if database operation fails
     */
    public Doctor findById(int id) throws SQLException {
        String sql = "SELECT * FROM doctors WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToDoctor(rs);
                }
            }
        }
        return null;
    }
    
    /**
     * Find all doctors
     * @return List of all doctors
     * @throws SQLException if database operation fails
     */
    public List<Doctor> findAll() throws SQLException {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM doctors";
        
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                doctors.add(mapResultSetToDoctor(rs));
            }
        }
        return doctors;
    }
    
    /**
     * Update doctor
     * @param doctor Doctor to update
     * @return Updated doctor
     * @throws SQLException if database operation fails
     */
    public Doctor update(Doctor doctor) throws SQLException {
        String sql = "UPDATE doctors SET name = ?, date_of_birth = ?, email = ?, phone_number = ?, " +
                     "specialization = ?, consultation_fee = ?, experience_years = ?, license_number = ? " +
                     "WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, doctor.getName());
            stmt.setString(2, DateUtil.formatDate(doctor.getDateOfBirth()));
            stmt.setString(3, doctor.getEmail());
            stmt.setString(4, doctor.getPhoneNumber());
            stmt.setString(5, doctor.getSpecialization().name());
            stmt.setDouble(6, doctor.getConsultationFee());
            stmt.setInt(7, doctor.getExperienceYears());
            stmt.setString(8, doctor.getLicenseNumber());
            stmt.setInt(9, doctor.getId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0 ? doctor : null;
        }
    }
    
    /**
     * Delete doctor by ID
     * @param id Doctor ID
     * @return true if deleted, false otherwise
     * @throws SQLException if database operation fails
     */
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM doctors WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
    
    /**
     * Search doctors by specialization
     * @param specialization Specialization to search
     * @return List of matching doctors
     * @throws SQLException if database operation fails
     */
    public List<Doctor> findBySpecialization(Specialization specialization) throws SQLException {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM doctors WHERE specialization = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, specialization.name());
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    doctors.add(mapResultSetToDoctor(rs));
                }
            }
        }
        return doctors;
    }
    
    /**
     * Search doctors by name
     * @param name Name to search
     * @return List of matching doctors
     * @throws SQLException if database operation fails
     */
    public List<Doctor> searchByName(String name) throws SQLException {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM doctors WHERE name LIKE ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + name + "%");
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    doctors.add(mapResultSetToDoctor(rs));
                }
            }
        }
        return doctors;
    }
    
    /**
     * Map ResultSet to Doctor object
     * @param rs ResultSet
     * @return Doctor object
     * @throws SQLException if mapping fails
     */
    private Doctor mapResultSetToDoctor(ResultSet rs) throws SQLException {
        Doctor doctor = new Doctor();
        doctor.setId(rs.getInt("id"));
        doctor.setName(rs.getString("name"));
        
        String dobStr = rs.getString("date_of_birth");
        if (dobStr != null) {
            doctor.setDateOfBirth(DateUtil.parseDate(dobStr));
        }
        
        doctor.setEmail(rs.getString("email"));
        doctor.setPhoneNumber(rs.getString("phone_number"));
        
        String specStr = rs.getString("specialization");
        if (specStr != null) {
            doctor.setSpecialization(Specialization.valueOf(specStr));
        }
        
        doctor.setConsultationFee(rs.getDouble("consultation_fee"));
        doctor.setExperienceYears(rs.getInt("experience_years"));
        doctor.setLicenseNumber(rs.getString("license_number"));
        
        return doctor;
    }
}


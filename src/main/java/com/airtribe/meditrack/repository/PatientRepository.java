package com.airtribe.meditrack.repository;

import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.util.DatabaseUtil;
import com.airtribe.meditrack.util.DateUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PatientRepository {
    
    /**
     * Create a new patient
     * @param patient Patient to create
     * @return Created patient with ID
     * @throws SQLException if database operation fails
     */
    public Patient create(Patient patient) throws SQLException {
        String sql = "INSERT INTO patients (id, name, date_of_birth, email, phone_number, " +
                     "medical_history, allergies, insurance_provider, insurance_policy_number) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, patient.getId());
            stmt.setString(2, patient.getName());
            stmt.setString(3, DateUtil.formatDate(patient.getDateOfBirth()));
            stmt.setString(4, patient.getEmail());
            stmt.setString(5, patient.getPhoneNumber());
            stmt.setString(6, patient.getMedicalHistory());
            stmt.setString(7, patient.getAllergies());
            stmt.setString(8, patient.getInsuranceProvider());
            stmt.setString(9, patient.getInsurancePolicyNumber());
            
            stmt.executeUpdate();
            return patient;
        }
    }
    
    /**
     * Find patient by ID
     * @param id Patient ID
     * @return Patient if found, null otherwise
     * @throws SQLException if database operation fails
     */
    public Patient findById(int id) throws SQLException {
        String sql = "SELECT * FROM patients WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPatient(rs);
                }
            }
        }
        return null;
    }
    
    /**
     * Find all patients
     * @return List of all patients
     * @throws SQLException if database operation fails
     */
    public List<Patient> findAll() throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patients";
        
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                patients.add(mapResultSetToPatient(rs));
            }
        }
        return patients;
    }
    
    /**
     * Update patient
     * @param patient Patient to update
     * @return Updated patient
     * @throws SQLException if database operation fails
     */
    public Patient update(Patient patient) throws SQLException {
        String sql = "UPDATE patients SET name = ?, date_of_birth = ?, email = ?, phone_number = ?, " +
                     "medical_history = ?, allergies = ?, insurance_provider = ?, " +
                     "insurance_policy_number = ? WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, patient.getName());
            stmt.setString(2, DateUtil.formatDate(patient.getDateOfBirth()));
            stmt.setString(3, patient.getEmail());
            stmt.setString(4, patient.getPhoneNumber());
            stmt.setString(5, patient.getMedicalHistory());
            stmt.setString(6, patient.getAllergies());
            stmt.setString(7, patient.getInsuranceProvider());
            stmt.setString(8, patient.getInsurancePolicyNumber());
            stmt.setInt(9, patient.getId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0 ? patient : null;
        }
    }
    
    /**
     * Delete patient by ID
     * @param id Patient ID
     * @return true if deleted, false otherwise
     * @throws SQLException if database operation fails
     */
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM patients WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
    
    /**
     * Search patients by name
     * @param name Name to search
     * @return List of matching patients
     * @throws SQLException if database operation fails
     */
    public List<Patient> searchByName(String name) throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patients WHERE name LIKE ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + name + "%");
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    patients.add(mapResultSetToPatient(rs));
                }
            }
        }
        return patients;
    }
    
    /**
     * Map ResultSet to Patient object
     * @param rs ResultSet
     * @return Patient object
     * @throws SQLException if mapping fails
     */
    private Patient mapResultSetToPatient(ResultSet rs) throws SQLException {
        Patient patient = new Patient();
        patient.setId(rs.getInt("id"));
        patient.setName(rs.getString("name"));
        
        String dobStr = rs.getString("date_of_birth");
        if (dobStr != null) {
            patient.setDateOfBirth(DateUtil.parseDate(dobStr));
        }
        
        patient.setEmail(rs.getString("email"));
        patient.setPhoneNumber(rs.getString("phone_number"));
        patient.setMedicalHistory(rs.getString("medical_history"));
        patient.setAllergies(rs.getString("allergies"));
        patient.setInsuranceProvider(rs.getString("insurance_provider"));
        patient.setInsurancePolicyNumber(rs.getString("insurance_policy_number"));
        
        return patient;
    }
}


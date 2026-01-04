package com.airtribe.meditrack.controller;

import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.service.PatientService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;


public class PatientController {
    
    private final PatientService patientService;
    

    public PatientController() {
        this.patientService = new PatientService();
    }
    
    /**
     * Create a new patient
     * @param name Patient name
     * @param dateOfBirth Date of birth
     * @param email Email
     * @param phoneNumber Phone number
     * @param medicalHistory Medical history
     * @param allergies Allergies
     * @param insuranceProvider Insurance provider
     * @param insurancePolicyNumber Insurance policy number
     * @return Created patient
     * @throws InvalidDataException if validation fails
     * @throws SQLException if database operation fails
     */
    public Patient createPatient(String name, LocalDate dateOfBirth, String email,
                                String phoneNumber, String medicalHistory, String allergies,
                                String insuranceProvider, String insurancePolicyNumber)
            throws InvalidDataException, SQLException {
        return patientService.createPatient(name, dateOfBirth, email, phoneNumber,
                medicalHistory, allergies, insuranceProvider, insurancePolicyNumber);
    }
    
    /**
     * Get patient by ID
     * @param id Patient ID
     * @return Patient if found
     * @throws InvalidDataException if validation fails
     * @throws SQLException if database operation fails
     */
    public Patient getPatient(int id) throws InvalidDataException, SQLException {
        return patientService.findPatientById(id);
    }
    
    /**
     * Get all patients
     * @return List of all patients
     * @throws SQLException if database operation fails
     */
    public List<Patient> getAllPatients() throws SQLException {
        return patientService.getAllPatients();
    }
    
    /**
     * Update patient
     * @param patient Patient to update
     * @return Updated patient
     * @throws InvalidDataException if validation fails
     * @throws SQLException if database operation fails
     */
    public Patient updatePatient(Patient patient) throws InvalidDataException, SQLException {
        return patientService.updatePatient(patient);
    }
    
    /**
     * Delete patient
     * @param id Patient ID
     * @return true if deleted
     * @throws InvalidDataException if validation fails
     * @throws SQLException if database operation fails
     */
    public boolean deletePatient(int id) throws InvalidDataException, SQLException {
        return patientService.deletePatient(id);
    }
    
    /**
     * Search patient by ID
     * @param id Patient ID
     * @return Patient if found
     * @throws InvalidDataException if validation fails
     * @throws SQLException if database operation fails
     */
    public Patient searchPatientById(int id) throws InvalidDataException, SQLException {
        return patientService.searchPatient(id);
    }
    
    /**
     * Search patient by name
     * @param name Patient name
     * @return List of matching patients
     * @throws InvalidDataException if validation fails
     * @throws SQLException if database operation fails
     */
    public List<Patient> searchPatientByName(String name) throws InvalidDataException, SQLException {
        return patientService.searchPatient(name);
    }
    
    /**
     * Search patient by age
     * @param age Patient age
     * @return List of matching patients
     * @throws InvalidDataException if validation fails
     * @throws SQLException if database operation fails
     */
    public List<Patient> searchPatientByAge(int age) throws InvalidDataException, SQLException {
        return patientService.searchPatientsByAge(age);
    }
}


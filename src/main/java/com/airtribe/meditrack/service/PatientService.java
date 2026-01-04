package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.repository.PatientRepository;
import com.airtribe.meditrack.util.IdGenerator;
import com.airtribe.meditrack.util.Validator;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PatientService {
    
    private final PatientRepository patientRepository;
    
    public PatientService() {
        this.patientRepository = new PatientRepository();
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
        
        // Validation
        Validator.validateName(name);
        Validator.validateDateOfBirth(dateOfBirth);
        Validator.validateEmail(email);
        Validator.validatePhone(phoneNumber);
        
        Patient patient = new Patient();
        patient.setId(IdGenerator.getInstance().getNextPatientId());
        patient.setName(name);
        patient.setDateOfBirth(dateOfBirth);
        patient.setEmail(email);
        patient.setPhoneNumber(phoneNumber);
        patient.setMedicalHistory(medicalHistory);
        patient.setAllergies(allergies);
        patient.setInsuranceProvider(insuranceProvider);
        patient.setInsurancePolicyNumber(insurancePolicyNumber);
        
        return patientRepository.create(patient);
    }
    
    /**
     * Find patient by ID
     * @param id Patient ID
     * @return Patient if found, null otherwise
     * @throws InvalidDataException if ID is invalid
     * @throws SQLException if database operation fails
     */
    public Patient findPatientById(int id) throws InvalidDataException, SQLException {
        Validator.validateId(id);
        return patientRepository.findById(id);
    }
    
    /**
     * Get all patients
     * @return List of all patients
     * @throws SQLException if database operation fails
     */
    public List<Patient> getAllPatients() throws SQLException {
        return patientRepository.findAll();
    }
    
    /**
     * Update patient
     * @param patient Patient to update
     * @return Updated patient
     * @throws InvalidDataException if validation fails
     * @throws SQLException if database operation fails
     */
    public Patient updatePatient(Patient patient) throws InvalidDataException, SQLException {
        Validator.validateId(patient.getId());
        Validator.validateName(patient.getName());
        Validator.validateEmail(patient.getEmail());
        Validator.validatePhone(patient.getPhoneNumber());
        
        return patientRepository.update(patient);
    }
    
    /**
     * Delete patient by ID
     * @param id Patient ID
     * @return true if deleted, false otherwise
     * @throws InvalidDataException if ID is invalid
     * @throws SQLException if database operation fails
     */
    public boolean deletePatient(int id) throws InvalidDataException, SQLException {
        Validator.validateId(id);
        return patientRepository.delete(id);
    }
    
    /**
     * Search patient by ID - Method Overloading (Polymorphism)
     * @param id Patient ID
     * @return Patient if found, null otherwise
     * @throws InvalidDataException if ID is invalid
     * @throws SQLException if database operation fails
     */
    public Patient searchPatient(int id) throws InvalidDataException, SQLException {
        Validator.validateId(id);
        return patientRepository.findById(id);
    }
    
    /**
     * Search patient by name - Method Overloading (Polymorphism)
     * @param name Patient name
     * @return List of matching patients
     * @throws InvalidDataException if name is invalid
     * @throws SQLException if database operation fails
     */
    public List<Patient> searchPatient(String name) throws InvalidDataException, SQLException {
        Validator.validateName(name);
        return patientRepository.searchByName(name);
    }
    
    /**
     * Search patients by age
     * Note: Cannot overload with searchPatient(int id) as both use int parameter
     * @param age Patient age
     * @return List of patients with matching age
     * @throws InvalidDataException if age is invalid
     * @throws SQLException if database operation fails
     */
    public List<Patient> searchPatientsByAge(int age) throws InvalidDataException, SQLException {
        Validator.validateAge(age);
        List<Patient> allPatients = patientRepository.findAll();
        return allPatients.stream()
                .filter(p -> p.getAge() == age)
                .collect(java.util.stream.Collectors.toList());
    }
}


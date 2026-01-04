package com.airtribe.meditrack.service;

import com.airtribe.meditrack.constants.Specialization;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.repository.DoctorRepository;
import com.airtribe.meditrack.util.IdGenerator;
import com.airtribe.meditrack.util.Validator;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class DoctorService {
    
    private final DoctorRepository doctorRepository;
    
    public DoctorService() {
        this.doctorRepository = new DoctorRepository();
    }
    
    /**
     * Create a new doctor
     * @param name Doctor name
     * @param dateOfBirth Date of birth
     * @param email Email
     * @param phoneNumber Phone number
     * @param specialization Specialization
     * @param consultationFee Consultation fee
     * @param experienceYears Years of experience
     * @param licenseNumber License number
     * @return Created doctor
     * @throws InvalidDataException if validation fails
     * @throws SQLException if database operation fails
     */
    public Doctor createDoctor(String name, LocalDate dateOfBirth, String email,
                              String phoneNumber, Specialization specialization,
                              double consultationFee, int experienceYears, String licenseNumber)
            throws InvalidDataException, SQLException {
        
        // Validation
        Validator.validateName(name);
        Validator.validateDateOfBirth(dateOfBirth);
        Validator.validateEmail(email);
        Validator.validatePhone(phoneNumber);
        Validator.validateAmount(consultationFee);
        Validator.validateNotNull(specialization, "Specialization");
        
        Doctor doctor = new Doctor();
        doctor.setId(IdGenerator.getInstance().getNextDoctorId());
        doctor.setName(name);
        doctor.setDateOfBirth(dateOfBirth);
        doctor.setEmail(email);
        doctor.setPhoneNumber(phoneNumber);
        doctor.setSpecialization(specialization);
        doctor.setConsultationFee(consultationFee);
        doctor.setExperienceYears(experienceYears);
        doctor.setLicenseNumber(licenseNumber);
        
        return doctorRepository.create(doctor);
    }
    
    /**
     * Find doctor by ID
     * @param id Doctor ID
     * @return Doctor if found, null otherwise
     * @throws InvalidDataException if ID is invalid
     * @throws SQLException if database operation fails
     */
    public Doctor findDoctorById(int id) throws InvalidDataException, SQLException {
        Validator.validateId(id);
        return doctorRepository.findById(id);
    }
    
    /**
     * Get all doctors
     * @return List of all doctors
     * @throws SQLException if database operation fails
     */
    public List<Doctor> getAllDoctors() throws SQLException {
        return doctorRepository.findAll();
    }
    
    /**
     * Update doctor
     * @param doctor Doctor to update
     * @return Updated doctor
     * @throws InvalidDataException if validation fails
     * @throws SQLException if database operation fails
     */
    public Doctor updateDoctor(Doctor doctor) throws InvalidDataException, SQLException {
        Validator.validateId(doctor.getId());
        Validator.validateName(doctor.getName());
        Validator.validateEmail(doctor.getEmail());
        Validator.validatePhone(doctor.getPhoneNumber());
        Validator.validateAmount(doctor.getConsultationFee());
        
        return doctorRepository.update(doctor);
    }
    
    /**
     * Delete doctor by ID
     * @param id Doctor ID
     * @return true if deleted, false otherwise
     * @throws InvalidDataException if ID is invalid
     * @throws SQLException if database operation fails
     */
    public boolean deleteDoctor(int id) throws InvalidDataException, SQLException {
        Validator.validateId(id);
        return doctorRepository.delete(id);
    }
    
    /**
     * Search doctors by specialization
     * @param specialization Specialization
     * @return List of matching doctors
     * @throws InvalidDataException if specialization is null
     * @throws SQLException if database operation fails
     */
    public List<Doctor> searchDoctorsBySpecialization(Specialization specialization)
            throws InvalidDataException, SQLException {
        Validator.validateNotNull(specialization, "Specialization");
        return doctorRepository.findBySpecialization(specialization);
    }
    
    /**
     * Search doctors by name
     * @param name Doctor name
     * @return List of matching doctors
     * @throws InvalidDataException if name is invalid
     * @throws SQLException if database operation fails
     */
    public List<Doctor> searchDoctorsByName(String name) throws InvalidDataException, SQLException {
        Validator.validateName(name);
        return doctorRepository.searchByName(name);
    }
    
    /**
     * Search doctor by ID
     * @param id Doctor ID
     * @return Doctor if found, null otherwise
     * @throws InvalidDataException if ID is invalid
     * @throws SQLException if database operation fails
     */
    public Doctor searchDoctorById(int id) throws InvalidDataException, SQLException {
        Validator.validateId(id);
        return doctorRepository.findById(id);
    }
}


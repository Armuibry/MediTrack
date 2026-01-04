package com.airtribe.meditrack.controller;

import com.airtribe.meditrack.constants.Specialization;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.service.DoctorService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class DoctorController {
    
    private final DoctorService doctorService;
    
    public DoctorController() {
        this.doctorService = new DoctorService();
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
        return doctorService.createDoctor(name, dateOfBirth, email, phoneNumber,
                specialization, consultationFee, experienceYears, licenseNumber);
    }
    
    /**
     * Get doctor by ID
     * @param id Doctor ID
     * @return Doctor if found
     * @throws InvalidDataException if validation fails
     * @throws SQLException if database operation fails
     */
    public Doctor getDoctor(int id) throws InvalidDataException, SQLException {
        return doctorService.findDoctorById(id);
    }
    
    /**
     * Get all doctors
     * @return List of all doctors
     * @throws SQLException if database operation fails
     */
    public List<Doctor> getAllDoctors() throws SQLException {
        return doctorService.getAllDoctors();
    }
    
    /**
     * Update doctor
     * @param doctor Doctor to update
     * @return Updated doctor
     * @throws InvalidDataException if validation fails
     * @throws SQLException if database operation fails
     */
    public Doctor updateDoctor(Doctor doctor) throws InvalidDataException, SQLException {
        return doctorService.updateDoctor(doctor);
    }
    
    /**
     * Delete doctor
     * @param id Doctor ID
     * @return true if deleted
     * @throws InvalidDataException if validation fails
     * @throws SQLException if database operation fails
     */
    public boolean deleteDoctor(int id) throws InvalidDataException, SQLException {
        return doctorService.deleteDoctor(id);
    }
    
    /**
     * Search doctors by specialization
     * @param specialization Specialization
     * @return List of matching doctors
     * @throws InvalidDataException if validation fails
     * @throws SQLException if database operation fails
     */
    public List<Doctor> searchDoctorsBySpecialization(Specialization specialization)
            throws InvalidDataException, SQLException {
        return doctorService.searchDoctorsBySpecialization(specialization);
    }
    
    /**
     * Search doctors by name
     * @param name Doctor name
     * @return List of matching doctors
     * @throws InvalidDataException if validation fails
     * @throws SQLException if database operation fails
     */
    public List<Doctor> searchDoctorsByName(String name) throws InvalidDataException, SQLException {
        return doctorService.searchDoctorsByName(name);
    }
    
    /**
     * Search doctor by ID
     * @param id Doctor ID
     * @return Doctor if found
     * @throws InvalidDataException if validation fails
     * @throws SQLException if database operation fails
     */
    public Doctor searchDoctorById(int id) throws InvalidDataException, SQLException {
        return doctorService.searchDoctorById(id);
    }
}


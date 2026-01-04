package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.constants.Specialization;
import com.airtribe.meditrack.interfaces.Searchable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Doctor entity extending Person
 * Demonstrates inheritance and polymorphism
 */
public class Doctor extends Person implements Searchable {
    
    private Specialization specialization;
    private double consultationFee;
    private int experienceYears;
    private String licenseNumber;

    public Doctor() {
        super();
    }
    
    public Doctor(int id, String name, LocalDate dateOfBirth, String email, 
                  String phoneNumber, Specialization specialization, 
                  double consultationFee, int experienceYears, String licenseNumber) {
        super(id, name, dateOfBirth, email, phoneNumber);
        this.specialization = specialization;
        this.consultationFee = consultationFee;
        this.experienceYears = experienceYears;
        this.licenseNumber = licenseNumber;
    }

    public Specialization getSpecialization() {
        return specialization;
    }
    
    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }
    
    public double getConsultationFee() {
        return consultationFee;
    }
    
    public void setConsultationFee(double consultationFee) {
        this.consultationFee = consultationFee;
    }
    
    public int getExperienceYears() {
        return experienceYears;
    }
    
    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }
    
    public String getLicenseNumber() {
        return licenseNumber;
    }
    
    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }
    
    @Override
    public boolean matches(String query) {
        if (query == null || query.isEmpty()) {
            return false;
        }
        String lowerQuery = query.toLowerCase();
        return getName().toLowerCase().contains(lowerQuery) ||
               specialization.toString().toLowerCase().contains(lowerQuery) ||
               String.valueOf(getId()).equals(query) ||
               licenseNumber.toLowerCase().contains(lowerQuery);
    }
    
    @Override
    public String getSearchableText() {
        return String.format("%s %s %s %d", getName(), specialization, licenseNumber, getId());
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Doctor doctor = (Doctor) o;
        return Objects.equals(licenseNumber, doctor.licenseNumber);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), licenseNumber);
    }
    
    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", specialization=" + specialization +
                ", consultationFee=" + consultationFee +
                ", experienceYears=" + experienceYears +
                ", licenseNumber='" + licenseNumber + '\'' +
                '}';
    }
}


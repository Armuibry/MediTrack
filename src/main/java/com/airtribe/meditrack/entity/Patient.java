package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.interfaces.Searchable;
import java.time.LocalDate;
import java.util.Objects;

public class Patient extends Person implements Searchable, Cloneable {
    
    private String medicalHistory;
    private String allergies;
    private String insuranceProvider;
    private String insurancePolicyNumber;
    
    public Patient() {
        super();
    }
    
    public Patient(int id, String name, LocalDate dateOfBirth, String email,
                   String phoneNumber, String medicalHistory, String allergies,
                   String insuranceProvider, String insurancePolicyNumber) {
        super(id, name, dateOfBirth, email, phoneNumber);
        this.medicalHistory = medicalHistory;
        this.allergies = allergies;
        this.insuranceProvider = insuranceProvider;
        this.insurancePolicyNumber = insurancePolicyNumber;
    }
        
    public String getMedicalHistory() {
        return medicalHistory;
    }
    
    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }
    
    public String getAllergies() {
        return allergies;
    }
    
    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }
    
    public String getInsuranceProvider() {
        return insuranceProvider;
    }
    
    public void setInsuranceProvider(String insuranceProvider) {
        this.insuranceProvider = insuranceProvider;
    }
    
    public String getInsurancePolicyNumber() {
        return insurancePolicyNumber;
    }
    
    public void setInsurancePolicyNumber(String insurancePolicyNumber) {
        this.insurancePolicyNumber = insurancePolicyNumber;
    }
    
    @Override
    public boolean matches(String query) {
        if (query == null || query.isEmpty()) {
            return false;
        }
        String lowerQuery = query.toLowerCase();
        return getName().toLowerCase().contains(lowerQuery) ||
               String.valueOf(getId()).equals(query) ||
               String.valueOf(getAge()).equals(query) ||
               getEmail() != null && getEmail().toLowerCase().contains(lowerQuery);
    }
    
    @Override
    public String getSearchableText() {
        return String.format("%s %d %s", getName(), getId(), getAge());
    }
    
    /**
     * Deep clone implementation
     * @return Cloned Patient object
     * @throws CloneNotSupportedException if cloning fails
     */
    @Override
    public Patient clone() throws CloneNotSupportedException {
        Patient cloned = (Patient) super.clone();
        return cloned;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Patient patient = (Patient) o;
        return Objects.equals(insurancePolicyNumber, patient.insurancePolicyNumber);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), insurancePolicyNumber);
    }
    
    @Override
    public String toString() {
        return "Patient{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", age=" + getAge() +
                ", medicalHistory='" + medicalHistory + '\'' +
                ", allergies='" + allergies + '\'' +
                ", insuranceProvider='" + insuranceProvider + '\'' +
                '}';
    }
}


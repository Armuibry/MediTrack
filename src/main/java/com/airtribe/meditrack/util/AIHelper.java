package com.airtribe.meditrack.util;

import com.airtribe.meditrack.constants.Specialization;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.repository.DoctorRepository;
import com.airtribe.meditrack.repository.AppointmentRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class AIHelper {
    
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    
    private static final Map<String, Specialization> SYMPTOM_SPECIALIZATION_MAP = new HashMap<>();
    
    static {
        SYMPTOM_SPECIALIZATION_MAP.put("chest pain", Specialization.CARDIOLOGY);
        SYMPTOM_SPECIALIZATION_MAP.put("heart", Specialization.CARDIOLOGY);
        SYMPTOM_SPECIALIZATION_MAP.put("cardiac", Specialization.CARDIOLOGY);
        SYMPTOM_SPECIALIZATION_MAP.put("rash", Specialization.DERMATOLOGY);
        SYMPTOM_SPECIALIZATION_MAP.put("skin", Specialization.DERMATOLOGY);
        SYMPTOM_SPECIALIZATION_MAP.put("acne", Specialization.DERMATOLOGY);
        SYMPTOM_SPECIALIZATION_MAP.put("child", Specialization.PEDIATRICS);
        SYMPTOM_SPECIALIZATION_MAP.put("pediatric", Specialization.PEDIATRICS);
        SYMPTOM_SPECIALIZATION_MAP.put("baby", Specialization.PEDIATRICS);
        SYMPTOM_SPECIALIZATION_MAP.put("fracture", Specialization.ORTHOPEDICS);
        SYMPTOM_SPECIALIZATION_MAP.put("bone", Specialization.ORTHOPEDICS);
        SYMPTOM_SPECIALIZATION_MAP.put("joint", Specialization.ORTHOPEDICS);
        SYMPTOM_SPECIALIZATION_MAP.put("headache", Specialization.NEUROLOGY);
        SYMPTOM_SPECIALIZATION_MAP.put("neurological", Specialization.NEUROLOGY);
        SYMPTOM_SPECIALIZATION_MAP.put("seizure", Specialization.NEUROLOGY);
        SYMPTOM_SPECIALIZATION_MAP.put("mental", Specialization.PSYCHIATRY);
        SYMPTOM_SPECIALIZATION_MAP.put("depression", Specialization.PSYCHIATRY);
        SYMPTOM_SPECIALIZATION_MAP.put("anxiety", Specialization.PSYCHIATRY);
        SYMPTOM_SPECIALIZATION_MAP.put("cancer", Specialization.ONCOLOGY);
        SYMPTOM_SPECIALIZATION_MAP.put("tumor", Specialization.ONCOLOGY);
        SYMPTOM_SPECIALIZATION_MAP.put("women", Specialization.GYNECOLOGY);
        SYMPTOM_SPECIALIZATION_MAP.put("gynecological", Specialization.GYNECOLOGY);
        SYMPTOM_SPECIALIZATION_MAP.put("urinary", Specialization.UROLOGY);
        SYMPTOM_SPECIALIZATION_MAP.put("kidney", Specialization.UROLOGY);
    }

    public AIHelper() {
        this.doctorRepository = new DoctorRepository();
        this.appointmentRepository = new AppointmentRepository();
    }
    
    /**
     * Recommend doctor based on patient symptoms
     * @param symptoms Patient symptoms description
     * @return Recommended doctor or null if no match
     * @throws SQLException if database operation fails
     */
    public Doctor recommendDoctor(String symptoms) throws SQLException {
        if (symptoms == null || symptoms.trim().isEmpty()) {
            return null;
        }
        
        String lowerSymptoms = symptoms.toLowerCase();
        Specialization recommendedSpecialization = null;
        
        for (Map.Entry<String, Specialization> entry : SYMPTOM_SPECIALIZATION_MAP.entrySet()) {
            if (lowerSymptoms.contains(entry.getKey())) {
                recommendedSpecialization = entry.getValue();
                break;
            }
        }
        
        if (recommendedSpecialization == null) {
            recommendedSpecialization = Specialization.GENERAL;
        }
        
        List<Doctor> doctors = doctorRepository.findBySpecialization(recommendedSpecialization);
        
        if (doctors.isEmpty()) {
            doctors = doctorRepository.findBySpecialization(Specialization.GENERAL);
        }
        
        if (!doctors.isEmpty()) {
            return findLeastBusyDoctor(doctors);
        }
        
        return null;
    }
    
    /**
     * Suggest optimal appointment slots based on doctor availability
     * @param doctorId Doctor ID
     * @param preferredDate Preferred date (can be null)
     * @return List of suggested appointment times
     * @throws SQLException if database operation fails
     */
    public List<LocalDateTime> suggestAppointmentSlots(int doctorId, LocalDate preferredDate) 
            throws SQLException {
        List<LocalDateTime> suggestions = new ArrayList<>();
        LocalDateTime baseDateTime;
        
        if (preferredDate != null) {
            baseDateTime = preferredDate.atTime(9, 0); // Start at 9 AM
        } else {
            baseDateTime = LocalDateTime.now().plusDays(1).withHour(9).withMinute(0);
        }
        
        List<Appointment> existingAppointments = appointmentRepository.findByDoctorId(doctorId);
        Set<LocalDateTime> bookedSlots = new HashSet<>();
        
        for (Appointment apt : existingAppointments) {
            if (apt.getStatus() != com.airtribe.meditrack.constants.AppointmentStatus.CANCELLED) {
                bookedSlots.add(apt.getAppointmentDateTime());
            }
        }
        
        LocalDateTime currentSlot = baseDateTime;
        int slotsSuggested = 0;
        int maxSlots = 5;
        
        while (slotsSuggested < maxSlots && currentSlot.getHour() < 17) {
            if (currentSlot.isAfter(LocalDateTime.now()) && !bookedSlots.contains(currentSlot)) {
                suggestions.add(currentSlot);
                slotsSuggested++;
            }
            currentSlot = currentSlot.plusHours(1);
        }
        
        return suggestions;
    }
    
    /**
     * Find the least busy doctor from a list
     * @param doctors List of doctors
     * @return Least busy doctor
     */
    private Doctor findLeastBusyDoctor(List<Doctor> doctors) {
        if (doctors.isEmpty()) {
            return null;
        }
        
        if (doctors.size() == 1) {
            return doctors.get(0);
        }
        
        return doctors.get(0);
    }
}


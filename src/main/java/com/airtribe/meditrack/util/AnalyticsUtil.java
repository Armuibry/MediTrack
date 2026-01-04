package com.airtribe.meditrack.util;

import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.constants.AppointmentStatus;
import com.airtribe.meditrack.repository.AppointmentRepository;
import com.airtribe.meditrack.repository.BillRepository;
import com.airtribe.meditrack.repository.DoctorRepository;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class AnalyticsUtil {
    
    private final AppointmentRepository appointmentRepository;
    private final BillRepository billRepository;
    private final DoctorRepository doctorRepository;

    public AnalyticsUtil() {
        this.appointmentRepository = new AppointmentRepository();
        this.billRepository = new BillRepository();
        this.doctorRepository = new DoctorRepository();
    }
    
    /**
     * Filter doctors by specialization using streams
     * @param specialization Specialization to filter
     * @return List of doctors with the specialization
     * @throws SQLException if database operation fails
     */
    public List<Doctor> filterDoctorsBySpecialization(String specialization) throws SQLException {
        return doctorRepository.findAll().stream()
                .filter(doctor -> doctor.getSpecialization().toString()
                        .equalsIgnoreCase(specialization))
                .collect(Collectors.toList());
    }
    
    /**
     * Calculate average consultation fee using streams
     * @return Average consultation fee
     * @throws SQLException if database operation fails
     */
    public double calculateAverageConsultationFee() throws SQLException {
        return doctorRepository.findAll().stream()
                .mapToDouble(Doctor::getConsultationFee)
                .average()
                .orElse(0.0);
    }
    
    /**
     * Get appointments per doctor using streams
     * @return Map of doctor ID to appointment count
     * @throws SQLException if database operation fails
     */
    public Map<Integer, Long> getAppointmentsPerDoctor() throws SQLException {
        return appointmentRepository.findAll().stream()
                .filter(apt -> apt.getStatus() != AppointmentStatus.CANCELLED)
                .collect(Collectors.groupingBy(
                        Appointment::getDoctorId,
                        Collectors.counting()
                ));
    }
    
    /**
     * Calculate total revenue using streams
     * @return Total revenue
     * @throws SQLException if database operation fails
     */
    public double calculateTotalRevenue() throws SQLException {
        return billRepository.findAll().stream()
                .filter(bill -> "PAID".equalsIgnoreCase(bill.getPaymentStatus()))
                .mapToDouble(Bill::getTotalAmount)
                .sum();
    }
    
    /**
     * Get most booked doctors using streams
     * @param limit Number of top doctors to return
     * @return List of doctors sorted by appointment count
     * @throws SQLException if database operation fails
     */
    public List<Doctor> getMostBookedDoctors(int limit) throws SQLException {
        Map<Integer, Long> appointmentsPerDoctor = getAppointmentsPerDoctor();
        
        return appointmentsPerDoctor.entrySet().stream()
                .sorted(Map.Entry.<Integer, Long>comparingByValue().reversed())
                .limit(limit)
                .map(entry -> {
                    try {
                        return doctorRepository.findById(entry.getKey());
                    } catch (SQLException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
    
    /**
     * Get pending appointments using streams
     * @return List of pending appointments
     * @throws SQLException if database operation fails
     */
    public List<Appointment> getPendingAppointments() throws SQLException {
        return appointmentRepository.findAll().stream()
                .filter(apt -> apt.getStatus() == AppointmentStatus.PENDING)
                .sorted(Comparator.comparing(Appointment::getAppointmentDateTime))
                .collect(Collectors.toList());
    }
    
    /**
     * Get confirmed appointments count using streams
     * @return Count of confirmed appointments
     * @throws SQLException if database operation fails
     */
    public long getConfirmedAppointmentsCount() throws SQLException {
        return appointmentRepository.findAll().stream()
                .filter(apt -> apt.getStatus() == AppointmentStatus.CONFIRMED)
                .count();
    }
    
    /**
     * Get doctors with fee above average using streams
     * @return List of doctors with above-average fees
     * @throws SQLException if database operation fails
     */
    public List<Doctor> getDoctorsWithAboveAverageFee() throws SQLException {
        double averageFee = calculateAverageConsultationFee();
        
        return doctorRepository.findAll().stream()
                .filter(doctor -> doctor.getConsultationFee() > averageFee)
                .sorted(Comparator.comparing(Doctor::getConsultationFee).reversed())
                .collect(Collectors.toList());
    }
    
    /**
     * Generate analytics report
     * @return Analytics report as string
     * @throws SQLException if database operation fails
     */
    public String generateAnalyticsReport() throws SQLException {
        StringBuilder report = new StringBuilder();
        report.append("=== MEDITRACK ANALYTICS REPORT ===\n\n");
        
        report.append("Average Consultation Fee: $")
                .append(String.format("%.2f", calculateAverageConsultationFee()))
                .append("\n");
        
        report.append("Total Revenue: $")
                .append(String.format("%.2f", calculateTotalRevenue()))
                .append("\n");
        
        report.append("Confirmed Appointments: ")
                .append(getConfirmedAppointmentsCount())
                .append("\n");
        
        report.append("\nTop 3 Most Booked Doctors:\n");
        List<Doctor> topDoctors = getMostBookedDoctors(3);
        for (int i = 0; i < topDoctors.size(); i++) {
            Doctor doctor = topDoctors.get(i);
            report.append((i + 1))
                    .append(". ")
                    .append(doctor.getName())
                    .append(" - ")
                    .append(doctor.getSpecialization())
                    .append("\n");
        }
        
        return report.toString();
    }
}


package com.airtribe.meditrack.test;

import com.airtribe.meditrack.constants.AppointmentStatus;
import com.airtribe.meditrack.constants.Specialization;
import com.airtribe.meditrack.controller.AppointmentController;
import com.airtribe.meditrack.controller.DoctorController;
import com.airtribe.meditrack.controller.PatientController;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.util.AnalyticsUtil;
import com.airtribe.meditrack.util.AIHelper;
import com.airtribe.meditrack.util.DatabaseUtil;
import com.airtribe.meditrack.util.DateUtil;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class TestRunner {
    
    private static final PatientController patientController = new PatientController();
    private static final DoctorController doctorController = new DoctorController();
    private static final AppointmentController appointmentController = new AppointmentController();
    
    /**
     * Main test method
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        try {
            System.out.println("=== MEDITRACK TEST RUNNER ===\n");
            
            // Initialize database
            DatabaseUtil.initializeDatabase();
            System.out.println("✓ Database initialized\n");
            
            // Run tests
            testPatientOperations();
            testDoctorOperations();
            testAppointmentOperations();
            testSearchOperations();
            testBillingOperations();
            testAIHelper();
            testAnalytics();
            
            System.out.println("\n=== ALL TESTS COMPLETED ===");
            
        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Test patient operations
     */
    private static void testPatientOperations() throws Exception {
        System.out.println("--- Testing Patient Operations ---");
        
        // Create patient
        Patient patient = patientController.createPatient(
                "John Doe",
                LocalDate.of(1990, 5, 15),
                "john.doe@email.com",
                "1234567890",
                "No significant history",
                "None",
                "ABC Insurance",
                "POL123456"
        );
        System.out.println("✓ Patient created: " + patient.getId());
        
        // Retrieve patient
        Patient retrieved = patientController.getPatient(patient.getId());
        System.out.println("✓ Patient retrieved: " + retrieved.getName());
        
        // Update patient
        retrieved.setEmail("john.updated@email.com");
        patientController.updatePatient(retrieved);
        System.out.println("✓ Patient updated");
        
        // Search by name
        var patients = patientController.searchPatientByName("John");
        System.out.println("✓ Patient search by name: " + patients.size() + " found");
        
        System.out.println();
    }
    
    /**
     * Test doctor operations
     */
    private static void testDoctorOperations() throws Exception {
        System.out.println("--- Testing Doctor Operations ---");
        
        // Create doctor
        Doctor doctor = doctorController.createDoctor(
                "Dr. Jane Smith",
                LocalDate.of(1980, 3, 20),
                "jane.smith@clinic.com",
                "9876543210",
                Specialization.CARDIOLOGY,
                150.0,
                10,
                "LIC12345"
        );
        System.out.println("✓ Doctor created: " + doctor.getId());
        
        // Retrieve doctor
        Doctor retrieved = doctorController.getDoctor(doctor.getId());
        System.out.println("✓ Doctor retrieved: " + retrieved.getName());
        
        // Search by specialization
        var doctors = doctorController.searchDoctorsBySpecialization(Specialization.CARDIOLOGY);
        System.out.println("✓ Doctor search by specialization: " + doctors.size() + " found");
        
        System.out.println();
    }
    
    /**
     * Test appointment operations
     */
    private static void testAppointmentOperations() throws Exception {
        System.out.println("--- Testing Appointment Operations ---");
        
        // Get first patient and doctor
        var patients = patientController.getAllPatients();
        var doctors = doctorController.getAllDoctors();
        
        if (patients.isEmpty() || doctors.isEmpty()) {
            System.out.println("⚠ Skipping - Need at least one patient and doctor");
            return;
        }
        
        int patientId = patients.get(0).getId();
        int doctorId = doctors.get(0).getId();
        
        // Create appointment
        Appointment appointment = appointmentController.createAppointment(
                patientId,
                doctorId,
                LocalDateTime.now().plusDays(1).withHour(10).withMinute(0),
                "Regular checkup",
                "Routine examination"
        );
        System.out.println("✓ Appointment created: " + appointment.getId());
        
        // Confirm appointment
        appointmentController.confirmAppointment(appointment.getId());
        System.out.println("✓ Appointment confirmed");
        
        // Get appointment
        Appointment retrieved = appointmentController.getAppointment(appointment.getId());
        System.out.println("✓ Appointment retrieved: Status = " + retrieved.getStatus());
        
        System.out.println();
    }
    
    /**
     * Test search operations
     */
    private static void testSearchOperations() throws Exception {
        System.out.println("--- Testing Search Operations ---");
        
        // Search patient by ID (method overloading)
        var patients = patientController.getAllPatients();
        if (!patients.isEmpty()) {
            Patient found = patientController.searchPatientById(patients.get(0).getId());
            System.out.println("✓ Search patient by ID: " + (found != null ? "Found" : "Not found"));
        }
        
        // Search patient by name (method overloading)
        var foundByName = patientController.searchPatientByName("John");
        System.out.println("✓ Search patient by name: " + foundByName.size() + " found");
        
        // Search patient by age (method overloading)
        var foundByAge = patientController.searchPatientByAge(34);
        System.out.println("✓ Search patient by age: " + foundByAge.size() + " found");
        
        System.out.println();
    }
    
    /**
     * Test billing operations
     */
    private static void testBillingOperations() throws Exception {
        System.out.println("--- Testing Billing Operations ---");
        
        var appointments = appointmentController.getAllAppointments();
        if (appointments.isEmpty()) {
            System.out.println("⚠ Skipping - No appointments available");
            return;
        }
        
        // Create bill
        int appointmentId = appointments.get(0).getId();
        appointmentController.createBill(appointmentId);
        System.out.println("✓ Bill created for appointment: " + appointmentId);
        
        // Generate bill summary
        var summary = appointmentController.generateBillSummary(appointmentId);
        System.out.println("✓ Bill summary generated: Total = $" + summary.getTotalAmount());
        
        System.out.println();
    }
    
    /**
     * Test AI Helper
     */
    private static void testAIHelper() throws Exception {
        System.out.println("--- Testing AI Helper ---");
        
        AIHelper aiHelper = new AIHelper();
        
        // Test doctor recommendation
        Doctor recommended = aiHelper.recommendDoctor("chest pain and heart issues");
        if (recommended != null) {
            System.out.println("✓ Doctor recommended: " + recommended.getName() + 
                    " - " + recommended.getSpecialization());
        } else {
            System.out.println("⚠ No doctor recommendation available");
        }
        
        // Test appointment slot suggestion
        var doctors = doctorController.getAllDoctors();
        if (!doctors.isEmpty()) {
            var slots = aiHelper.suggestAppointmentSlots(doctors.get(0).getId(), 
                    LocalDate.now().plusDays(1));
            System.out.println("✓ Appointment slots suggested: " + slots.size() + " slots");
        }
        
        System.out.println();
    }
    
    /**
     * Test analytics
     */
    private static void testAnalytics() throws Exception {
        System.out.println("--- Testing Analytics (Streams & Lambdas) ---");
        
        AnalyticsUtil analytics = new AnalyticsUtil();
        
        // Average consultation fee
        double avgFee = analytics.calculateAverageConsultationFee();
        System.out.println("✓ Average consultation fee: $" + String.format("%.2f", avgFee));
        
        // Total revenue
        double revenue = analytics.calculateTotalRevenue();
        System.out.println("✓ Total revenue: $" + String.format("%.2f", revenue));
        
        // Confirmed appointments count
        long confirmedCount = analytics.getConfirmedAppointmentsCount();
        System.out.println("✓ Confirmed appointments: " + confirmedCount);
        
        // Generate report
        String report = analytics.generateAnalyticsReport();
        System.out.println("✓ Analytics report generated");
        System.out.println(report);
    }
}


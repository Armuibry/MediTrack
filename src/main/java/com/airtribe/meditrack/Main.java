package com.airtribe.meditrack;

import com.airtribe.meditrack.constants.AppointmentStatus;
import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.constants.Specialization;
import com.airtribe.meditrack.controller.AppointmentController;
import com.airtribe.meditrack.controller.DoctorController;
import com.airtribe.meditrack.controller.PatientController;
import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.BillSummary;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.exception.AppointmentNotFoundException;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.util.DateUtil;
import com.airtribe.meditrack.util.DatabaseUtil;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;


public class Main {
    
    private static final Scanner scanner = new Scanner(System.in);
    private static final PatientController patientController = new PatientController();
    private static final DoctorController doctorController = new DoctorController();
    private static final AppointmentController appointmentController = new AppointmentController();
    
    /**
     * Main method
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        try {
            // Initialize database
            DatabaseUtil.initializeDatabase();
            System.out.println("Database initialized successfully.");
            
            // Check for --loadData argument
            if (args.length > 0 && args[0].equals("--loadData")) {
                System.out.println("Loading data from files...");
            }
            
            // Display welcome message
            System.out.println("\n" + Constants.WELCOME_MSG);
            System.out.println("=".repeat(50));
            
            // Main menu loop
            boolean running = true;
            while (running) {
                displayMainMenu();
                int choice = getIntInput("Enter your choice: ");
                
                switch (choice) {
                    case 1:
                        handlePatientMenu();
                        break;
                    case 2:
                        handleDoctorMenu();
                        break;
                    case 3:
                        handleAppointmentMenu();
                        break;
                    case 4:
                        handleBillingMenu();
                        break;
                    case 5:
                        handleSearchMenu();
                        break;
                    case 6:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
            
            System.out.println("\n" + Constants.EXIT_MSG);
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
    
    /**
     * Display main menu
     */
    private static void displayMainMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("1. Patient Management");
        System.out.println("2. Doctor Management");
        System.out.println("3. Appointment Management");
        System.out.println("4. Billing");
        System.out.println("5. Search");
        System.out.println("6. Exit");
    }
    
    /**
     * Handle patient menu
     */
    private static void handlePatientMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== PATIENT MANAGEMENT ===");
            System.out.println("1. Add Patient");
            System.out.println("2. View All Patients");
            System.out.println("3. View Patient by ID");
            System.out.println("4. Update Patient");
            System.out.println("5. Delete Patient");
            System.out.println("6. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            try {
                switch (choice) {
                    case 1:
                        addPatient();
                        break;
                    case 2:
                        viewAllPatients();
                        break;
                    case 3:
                        viewPatientById();
                        break;
                    case 4:
                        updatePatient();
                        break;
                    case 5:
                        deletePatient();
                        break;
                    case 6:
                        back = true;
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }
    
    /**
     * Handle doctor menu
     */
    private static void handleDoctorMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== DOCTOR MANAGEMENT ===");
            System.out.println("1. Add Doctor");
            System.out.println("2. View All Doctors");
            System.out.println("3. View Doctor by ID");
            System.out.println("4. Update Doctor");
            System.out.println("5. Delete Doctor");
            System.out.println("6. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            try {
                switch (choice) {
                    case 1:
                        addDoctor();
                        break;
                    case 2:
                        viewAllDoctors();
                        break;
                    case 3:
                        viewDoctorById();
                        break;
                    case 4:
                        updateDoctor();
                        break;
                    case 5:
                        deleteDoctor();
                        break;
                    case 6:
                        back = true;
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }
    
    /**
     * Handle appointment menu
     */
    private static void handleAppointmentMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== APPOINTMENT MANAGEMENT ===");
            System.out.println("1. Create Appointment");
            System.out.println("2. View All Appointments");
            System.out.println("3. View Appointment by ID");
            System.out.println("4. View Appointments by Patient");
            System.out.println("5. View Appointments by Doctor");
            System.out.println("6. Cancel Appointment");
            System.out.println("7. Confirm Appointment");
            System.out.println("8. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            try {
                switch (choice) {
                    case 1:
                        createAppointment();
                        break;
                    case 2:
                        viewAllAppointments();
                        break;
                    case 3:
                        viewAppointmentById();
                        break;
                    case 4:
                        viewAppointmentsByPatient();
                        break;
                    case 5:
                        viewAppointmentsByDoctor();
                        break;
                    case 6:
                        cancelAppointment();
                        break;
                    case 7:
                        confirmAppointment();
                        break;
                    case 8:
                        back = true;
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }
    
    /**
     * Handle billing menu
     */
    private static void handleBillingMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== BILLING ===");
            System.out.println("1. Create Bill for Appointment");
            System.out.println("2. View Bill Summary");
            System.out.println("3. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            try {
                switch (choice) {
                    case 1:
                        createBill();
                        break;
                    case 2:
                        viewBillSummary();
                        break;
                    case 3:
                        back = true;
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }
    
    /**
     * Handle search menu
     */
    private static void handleSearchMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== SEARCH ===");
            System.out.println("1. Search Patient by ID");
            System.out.println("2. Search Patient by Name");
            System.out.println("3. Search Patient by Age");
            System.out.println("4. Search Doctor by ID");
            System.out.println("5. Search Doctor by Name");
            System.out.println("6. Search Doctor by Specialization");
            System.out.println("7. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            try {
                switch (choice) {
                    case 1: {
                        int id = getIntInput("Enter Patient ID: ");
                        Patient patient = patientController.searchPatientById(id);
                        if (patient != null) {
                            System.out.println(patient);
                        } else {
                            System.out.println("Patient not found.");
                        }
                        break;
                    }
                    case 2: {
                        String name = getStringInput("Enter Patient Name: ");
                        List<Patient> patients = patientController.searchPatientByName(name);
                        displayPatients(patients);
                        break;
                    }
                    case 3: {
                        int age = getIntInput("Enter Patient Age: ");
                        List<Patient> patients = patientController.searchPatientByAge(age);
                        displayPatients(patients);
                        break;
                    }
                    case 4: {
                        int id = getIntInput("Enter Doctor ID: ");
                        Doctor doctor = doctorController.searchDoctorById(id);
                        if (doctor != null) {
                            System.out.println(doctor);
                        } else {
                            System.out.println("Doctor not found.");
                        }
                        break;
                    }
                    case 5: {
                        String name = getStringInput("Enter Doctor Name: ");
                        List<Doctor> doctors = doctorController.searchDoctorsByName(name);
                        displayDoctors(doctors);
                        break;
                    }
                    case 6:
                        searchDoctorBySpecialization();
                        break;
                    case 7:
                        back = true;
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }
    
    // Patient operations
    private static void addPatient() throws Exception {
        System.out.println("\n=== ADD PATIENT ===");
        String name = getStringInput("Name: ");
        String dobStr = getStringInput("Date of Birth (yyyy-MM-dd): ");
        LocalDate dob = LocalDate.parse(dobStr);
        String email = getStringInput("Email: ");
        String phone = getStringInput("Phone Number: ");
        String medicalHistory = getStringInput("Medical History (optional): ");
        String allergies = getStringInput("Allergies (optional): ");
        String insuranceProvider = getStringInput("Insurance Provider (optional): ");
        String insurancePolicy = getStringInput("Insurance Policy Number (optional): ");
        
        Patient patient = patientController.createPatient(name, dob, email, phone,
                medicalHistory, allergies, insuranceProvider, insurancePolicy);
        System.out.println("Patient created successfully!");
        System.out.println("Patient ID: " + patient.getId());
    }
    
    private static void viewAllPatients() throws Exception {
        List<Patient> patients = patientController.getAllPatients();
        displayPatients(patients);
    }
    
    private static void viewPatientById() throws Exception {
        int id = getIntInput("Enter Patient ID: ");
        Patient patient = patientController.getPatient(id);
        if (patient != null) {
            System.out.println(patient);
        } else {
            System.out.println("Patient not found.");
        }
    }
    
    private static void updatePatient() throws Exception {
        int id = getIntInput("Enter Patient ID to update: ");
        Patient patient = patientController.getPatient(id);
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }
        
        System.out.println("Current patient: " + patient);
        String name = getStringInput("New Name (or press Enter to keep current): ");
        if (!name.isEmpty()) patient.setName(name);
        
        String email = getStringInput("New Email (or press Enter to keep current): ");
        if (!email.isEmpty()) patient.setEmail(email);
        
        patientController.updatePatient(patient);
        System.out.println("Patient updated successfully!");
    }
    
    private static void deletePatient() throws Exception {
        int id = getIntInput("Enter Patient ID to delete: ");
        if (patientController.deletePatient(id)) {
            System.out.println("Patient deleted successfully!");
        } else {
            System.out.println("Patient not found or could not be deleted.");
        }
    }
    
    // Doctor operations
    private static void addDoctor() throws Exception {
        System.out.println("\n=== ADD DOCTOR ===");
        String name = getStringInput("Name: ");
        String dobStr = getStringInput("Date of Birth (yyyy-MM-dd): ");
        LocalDate dob = LocalDate.parse(dobStr);
        String email = getStringInput("Email: ");
        String phone = getStringInput("Phone Number: ");
        
        System.out.println("Available Specializations:");
        Specialization[] specializations = Specialization.values();
        for (int i = 0; i < specializations.length; i++) {
            System.out.println((i + 1) + ". " + specializations[i]);
        }
        int specChoice = getIntInput("Select Specialization (1-" + specializations.length + "): ");
        Specialization specialization = specializations[specChoice - 1];
        
        double fee = getDoubleInput("Consultation Fee: ");
        int experience = getIntInput("Years of Experience: ");
        String license = getStringInput("License Number: ");
        
        Doctor doctor = doctorController.createDoctor(name, dob, email, phone,
                specialization, fee, experience, license);
        System.out.println("Doctor created successfully!");
        System.out.println("Doctor ID: " + doctor.getId());
    }
    
    private static void viewAllDoctors() throws Exception {
        List<Doctor> doctors = doctorController.getAllDoctors();
        displayDoctors(doctors);
    }
    
    private static void viewDoctorById() throws Exception {
        int id = getIntInput("Enter Doctor ID: ");
        Doctor doctor = doctorController.getDoctor(id);
        if (doctor != null) {
            System.out.println(doctor);
        } else {
            System.out.println("Doctor not found.");
        }
    }
    
    private static void updateDoctor() throws Exception {
        int id = getIntInput("Enter Doctor ID to update: ");
        Doctor doctor = doctorController.getDoctor(id);
        if (doctor == null) {
            System.out.println("Doctor not found.");
            return;
        }
        
        System.out.println("Current doctor: " + doctor);
        String name = getStringInput("New Name (or press Enter to keep current): ");
        if (!name.isEmpty()) doctor.setName(name);
        
        double fee = getDoubleInput("New Consultation Fee (or -1 to keep current): ");
        if (fee >= 0) doctor.setConsultationFee(fee);
        
        doctorController.updateDoctor(doctor);
        System.out.println("Doctor updated successfully!");
    }
    
    private static void deleteDoctor() throws Exception {
        int id = getIntInput("Enter Doctor ID to delete: ");
        if (doctorController.deleteDoctor(id)) {
            System.out.println("Doctor deleted successfully!");
        } else {
            System.out.println("Doctor not found or could not be deleted.");
        }
    }
    
    // Appointment operations
    private static void createAppointment() throws Exception {
        System.out.println("\n=== CREATE APPOINTMENT ===");
        int patientId = getIntInput("Patient ID: ");
        int doctorId = getIntInput("Doctor ID: ");
        String dateTimeStr = getStringInput("Appointment Date/Time (yyyy-MM-dd HH:mm): ");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr.replace(" ", "T"));
        String reason = getStringInput("Reason: ");
        String notes = getStringInput("Notes (optional): ");
        
        Appointment appointment = appointmentController.createAppointment(
                patientId, doctorId, dateTime, reason, notes);
        System.out.println("Appointment created successfully!");
        System.out.println("Appointment ID: " + appointment.getId());
    }
    
    private static void viewAllAppointments() throws Exception {
        List<Appointment> appointments = appointmentController.getAllAppointments();
        displayAppointments(appointments);
    }
    
    private static void viewAppointmentById() throws Exception {
        int id = getIntInput("Enter Appointment ID: ");
        Appointment appointment = appointmentController.getAppointment(id);
        if (appointment != null) {
            System.out.println(appointment);
        } else {
            System.out.println("Appointment not found.");
        }
    }
    
    private static void viewAppointmentsByPatient() throws Exception {
        int patientId = getIntInput("Enter Patient ID: ");
        List<Appointment> appointments = appointmentController.getAppointmentsByPatientId(patientId);
        displayAppointments(appointments);
    }
    
    private static void viewAppointmentsByDoctor() throws Exception {
        int doctorId = getIntInput("Enter Doctor ID: ");
        List<Appointment> appointments = appointmentController.getAppointmentsByDoctorId(doctorId);
        displayAppointments(appointments);
    }
    
    private static void cancelAppointment() throws Exception {
        int id = getIntInput("Enter Appointment ID to cancel: ");
        Appointment appointment = appointmentController.cancelAppointment(id);
        System.out.println("Appointment cancelled successfully!");
        System.out.println(appointment);
    }
    
    private static void confirmAppointment() throws Exception {
        int id = getIntInput("Enter Appointment ID to confirm: ");
        Appointment appointment = appointmentController.confirmAppointment(id);
        System.out.println("Appointment confirmed successfully!");
        System.out.println(appointment);
    }
    
    // Billing operations
    private static void createBill() throws Exception {
        int appointmentId = getIntInput("Enter Appointment ID: ");
        appointmentController.createBill(appointmentId);
        System.out.println("Bill created successfully!");
    }
    
    private static void viewBillSummary() throws Exception {
        int appointmentId = getIntInput("Enter Appointment ID: ");
        BillSummary summary = appointmentController.generateBillSummary(appointmentId);
        System.out.println("\n=== BILL SUMMARY ===");
        System.out.println(summary);
    }
    
    // Search operations
    private static void searchDoctorBySpecialization() throws Exception {
        System.out.println("Available Specializations:");
        Specialization[] specializations = Specialization.values();
        for (int i = 0; i < specializations.length; i++) {
            System.out.println((i + 1) + ". " + specializations[i]);
        }
        int specChoice = getIntInput("Select Specialization (1-" + specializations.length + "): ");
        Specialization specialization = specializations[specChoice - 1];
        
        List<Doctor> doctors = doctorController.searchDoctorsBySpecialization(specialization);
        displayDoctors(doctors);
    }
    
    // Helper methods
    private static void displayPatients(List<Patient> patients) {
        if (patients.isEmpty()) {
            System.out.println("No patients found.");
            return;
        }
        System.out.println("\n=== PATIENTS ===");
        for (Patient patient : patients) {
            System.out.println(patient);
        }
    }
    
    private static void displayDoctors(List<Doctor> doctors) {
        if (doctors.isEmpty()) {
            System.out.println("No doctors found.");
            return;
        }
        System.out.println("\n=== DOCTORS ===");
        for (Doctor doctor : doctors) {
            System.out.println(doctor);
        }
    }
    
    private static void displayAppointments(List<Appointment> appointments) {
        if (appointments.isEmpty()) {
            System.out.println("No appointments found.");
            return;
        }
        System.out.println("\n=== APPOINTMENTS ===");
        for (Appointment appointment : appointments) {
            System.out.println(appointment);
        }
    }
    
    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        try {
            int value = Integer.parseInt(scanner.nextLine().trim());
            return value;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return getIntInput(prompt);
        }
    }
    
    private static double getDoubleInput(String prompt) {
        System.out.print(prompt);
        try {
            return Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return getDoubleInput(prompt);
        }
    }
}


package com.airtribe.meditrack.constants;


public class Constants {
    
    // Database
    public static final String DB_URL = "jdbc:sqlite:meditrack.db";
    public static final String DB_DRIVER = "org.sqlite.JDBC";
    
    // Tax rate
    public static final double TAX_RATE = 0.10; // 10%
    
    // Date formats
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm";
    
    // Validation constants
    public static final int MIN_AGE = 0;
    public static final int MAX_AGE = 150;
    public static final int MIN_NAME_LENGTH = 2;
    public static final int MAX_NAME_LENGTH = 100;
    
    // File paths
    public static final String CSV_PATIENTS = "data/patients.csv";
    public static final String CSV_DOCTORS = "data/doctors.csv";
    public static final String CSV_APPOINTMENTS = "data/appointments.csv";
    public static final String SERIALIZATION_DIR = "data/serialized/";
    
    // Application messages
    public static final String WELCOME_MSG = "Welcome to MediTrack - Clinic Management System";
    public static final String EXIT_MSG = "Thank you for using MediTrack. Goodbye!";
    
    // Prevent instantiation
    private Constants() {
        throw new UnsupportedOperationException("Constants class cannot be instantiated");
    }
}


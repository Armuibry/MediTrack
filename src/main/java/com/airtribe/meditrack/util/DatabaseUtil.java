package com.airtribe.meditrack.util;

import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.constants.AppointmentStatus;
import com.airtribe.meditrack.constants.Specialization;

import java.sql.*;
import java.util.logging.Logger;

public class DatabaseUtil {
    
    private static final Logger logger = Logger.getLogger(DatabaseUtil.class.getName());
    
    /**
     * Get database connection
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(Constants.DB_DRIVER);
            return DriverManager.getConnection(Constants.DB_URL);
        } catch (ClassNotFoundException e) {
            throw new SQLException("SQLite JDBC driver not found", e);
        }
    }
    
    /**
     * Initialize database schema
     * @throws SQLException if schema creation fails
     */
    public static void initializeDatabase() throws SQLException {
        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);
            
            // Create patients table
            String createPatientsTable = "CREATE TABLE IF NOT EXISTS patients (" +
                    "id INTEGER PRIMARY KEY, " +
                    "name TEXT NOT NULL, " +
                    "date_of_birth TEXT NOT NULL, " +
                    "email TEXT NOT NULL, " +
                    "phone_number TEXT NOT NULL, " +
                    "medical_history TEXT, " +
                    "allergies TEXT, " +
                    "insurance_provider TEXT, " +
                    "insurance_policy_number TEXT)";
            
            // Create doctors table
            String createDoctorsTable = "CREATE TABLE IF NOT EXISTS doctors (" +
                    "id INTEGER PRIMARY KEY, " +
                    "name TEXT NOT NULL, " +
                    "date_of_birth TEXT NOT NULL, " +
                    "email TEXT NOT NULL, " +
                    "phone_number TEXT NOT NULL, " +
                    "specialization TEXT NOT NULL, " +
                    "consultation_fee REAL NOT NULL, " +
                    "experience_years INTEGER NOT NULL, " +
                    "license_number TEXT NOT NULL UNIQUE)";
            
            // Create appointments table
            String createAppointmentsTable = "CREATE TABLE IF NOT EXISTS appointments (" +
                    "id INTEGER PRIMARY KEY, " +
                    "patient_id INTEGER NOT NULL, " +
                    "doctor_id INTEGER NOT NULL, " +
                    "appointment_datetime TEXT NOT NULL, " +
                    "status TEXT NOT NULL, " +
                    "reason TEXT, " +
                    "notes TEXT, " +
                    "FOREIGN KEY (patient_id) REFERENCES patients(id), " +
                    "FOREIGN KEY (doctor_id) REFERENCES doctors(id))";
            
            // Create bills table
            String createBillsTable = "CREATE TABLE IF NOT EXISTS bills (" +
                    "id INTEGER PRIMARY KEY, " +
                    "appointment_id INTEGER NOT NULL, " +
                    "base_amount REAL NOT NULL, " +
                    "tax_amount REAL NOT NULL, " +
                    "total_amount REAL NOT NULL, " +
                    "bill_date TEXT NOT NULL, " +
                    "payment_status TEXT NOT NULL, " +
                    "FOREIGN KEY (appointment_id) REFERENCES appointments(id))";
            
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(createPatientsTable);
                stmt.execute(createDoctorsTable);
                stmt.execute(createAppointmentsTable);
                stmt.execute(createBillsTable);
                conn.commit();
                logger.info("Database schema initialized successfully");
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }
    
    /**
     * Close connection safely
     * @param conn Connection to close
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                logger.warning("Error closing connection: " + e.getMessage());
            }
        }
    }
    
    /**
     * Close statement safely
     * @param stmt Statement to close
     */
    public static void closeStatement(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                logger.warning("Error closing statement: " + e.getMessage());
            }
        }
    }
    
    /**
     * Close result set safely
     * @param rs ResultSet to close
     */
    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                logger.warning("Error closing result set: " + e.getMessage());
            }
        }
    }
}


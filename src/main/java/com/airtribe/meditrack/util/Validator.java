package com.airtribe.meditrack.util;

import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.exception.InvalidDataException;
import java.time.LocalDate;
import java.util.regex.Pattern;

public class Validator {
    
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    
    private static final Pattern PHONE_PATTERN = 
        Pattern.compile("^[0-9]{10}$");
    
    /**
     * Validate name
     * @param name Name to validate
     * @throws InvalidDataException if invalid
     */
    public static void validateName(String name) throws InvalidDataException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidDataException("Name cannot be null or empty");
        }
        if (name.length() < Constants.MIN_NAME_LENGTH || 
            name.length() > Constants.MAX_NAME_LENGTH) {
            throw new InvalidDataException(
                String.format("Name must be between %d and %d characters", 
                    Constants.MIN_NAME_LENGTH, Constants.MAX_NAME_LENGTH)
            );
        }
    }
    
    /**
     * Validate email
     * @param email Email to validate
     * @throws InvalidDataException if invalid
     */
    public static void validateEmail(String email) throws InvalidDataException {
        if (email == null || email.trim().isEmpty()) {
            throw new InvalidDataException("Email cannot be null or empty");
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new InvalidDataException("Invalid email format");
        }
    }
    
    /**
     * Validate phone number
     * @param phone Phone number to validate
     * @throws InvalidDataException if invalid
     */
    public static void validatePhone(String phone) throws InvalidDataException {
        if (phone == null || phone.trim().isEmpty()) {
            throw new InvalidDataException("Phone number cannot be null or empty");
        }
        // Remove any non-digit characters for validation
        String digitsOnly = phone.replaceAll("[^0-9]", "");
        if (!PHONE_PATTERN.matcher(digitsOnly).matches()) {
            throw new InvalidDataException("Phone number must be 10 digits");
        }
    }
    
    /**
     * Validate age
     * @param age Age to validate
     * @throws InvalidDataException if invalid
     */
    public static void validateAge(int age) throws InvalidDataException {
        if (age < Constants.MIN_AGE || age > Constants.MAX_AGE) {
            throw new InvalidDataException(
                String.format("Age must be between %d and %d", 
                    Constants.MIN_AGE, Constants.MAX_AGE)
            );
        }
    }
    
    /**
     * Validate date of birth
     * @param dateOfBirth Date of birth
     * @throws InvalidDataException if invalid
     */
    public static void validateDateOfBirth(LocalDate dateOfBirth) throws InvalidDataException {
        if (dateOfBirth == null) {
            throw new InvalidDataException("Date of birth cannot be null");
        }
        if (dateOfBirth.isAfter(LocalDate.now())) {
            throw new InvalidDataException("Date of birth cannot be in the future");
        }
    }
    
    /**
     * Validate amount (positive number)
     * @param amount Amount to validate
     * @throws InvalidDataException if invalid
     */
    public static void validateAmount(double amount) throws InvalidDataException {
        if (amount < 0) {
            throw new InvalidDataException("Amount cannot be negative");
        }
    }
    
    /**
     * Validate ID (positive number)
     * @param id ID to validate
     * @throws InvalidDataException if invalid
     */
    public static void validateId(int id) throws InvalidDataException {
        if (id <= 0) {
            throw new InvalidDataException("ID must be a positive number");
        }
    }
    
    /**
     * Validate not null
     * @param obj Object to validate
     * @param fieldName Field name for error message
     * @throws InvalidDataException if null
     */
    public static void validateNotNull(Object obj, String fieldName) throws InvalidDataException {
        if (obj == null) {
            throw new InvalidDataException(fieldName + " cannot be null");
        }
    }
}


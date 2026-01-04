package com.airtribe.meditrack.exception;

public class AppointmentNotFoundException extends Exception {
    
    /**
     * Constructor with message
     * @param message Error message
     */
    public AppointmentNotFoundException(String message) {
        super(message);
    }
    
    /**
     * Constructor with message and cause (exception chaining)
     * @param message Error message
     * @param cause The cause of this exception
     */
    public AppointmentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}


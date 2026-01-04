package com.airtribe.meditrack.exception;

public class InvalidDataException extends Exception {
    
    /**
     * Constructor with message
     * @param message Error message
     */
    public InvalidDataException(String message) {
        super(message);
    }
    
    /**
     * Constructor with message and cause (exception chaining)
     * @param message Error message
     * @param cause The cause of this exception
     */
    public InvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }
}


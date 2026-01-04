package com.airtribe.meditrack.util;

import com.airtribe.meditrack.constants.Constants;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtil {
    
    private static final DateTimeFormatter DATE_FORMATTER = 
        DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
    private static final DateTimeFormatter DATETIME_FORMATTER = 
        DateTimeFormatter.ofPattern(Constants.DATETIME_FORMAT);
    
    /**
     * Format LocalDate to string
     * @param date Date to format
     * @return Formatted date string
     */
    public static String formatDate(LocalDate date) {
        if (date == null) {
            return "";
        }
        return date.format(DATE_FORMATTER);
    }
    
    /**
     * Format LocalDateTime to string
     * @param dateTime DateTime to format
     * @return Formatted datetime string
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        return dateTime.format(DATETIME_FORMATTER);
    }
    
    /**
     * Parse string to LocalDate
     * @param dateString Date string to parse
     * @return Parsed LocalDate
     * @throws DateTimeParseException if parsing fails
     */
    public static LocalDate parseDate(String dateString) throws DateTimeParseException {
        if (dateString == null || dateString.trim().isEmpty()) {
            return null;
        }
        return LocalDate.parse(dateString.trim(), DATE_FORMATTER);
    }
    
    /**
     * Parse string to LocalDateTime
     * @param dateTimeString DateTime string to parse (format: yyyy-MM-dd HH:mm)
     * @return Parsed LocalDateTime
     * @throws DateTimeParseException if parsing fails
     */
    public static LocalDateTime parseDateTime(String dateTimeString) throws DateTimeParseException {
        if (dateTimeString == null || dateTimeString.trim().isEmpty()) {
            return null;
        }
        // Replace space with T for ISO format, or use our formatter
        String trimmed = dateTimeString.trim();
        try {
            return LocalDateTime.parse(trimmed, DATETIME_FORMATTER);
        } catch (DateTimeParseException e) {
            // Try with T separator format
            return LocalDateTime.parse(trimmed.replace(" ", "T"));
        }
    }
    
    /**
     * Check if date is in the past
     * @param date Date to check
     * @return true if in past, false otherwise
     */
    public static boolean isPastDate(LocalDate date) {
        return date != null && date.isBefore(LocalDate.now());
    }
    
    /**
     * Check if datetime is in the past
     * @param dateTime DateTime to check
     * @return true if in past, false otherwise
     */
    public static boolean isPastDateTime(LocalDateTime dateTime) {
        return dateTime != null && dateTime.isBefore(LocalDateTime.now());
    }
}


package com.airtribe.meditrack.constants;


public enum AppointmentStatus {
    CONFIRMED("Confirmed"),
    CANCELLED("Cancelled"),
    PENDING("Pending"),
    COMPLETED("Completed");
    
    private final String displayName;
    

    AppointmentStatus(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    @Override
    public String toString() {
        return displayName;
    }
}


package com.airtribe.meditrack.constants;


public enum Specialization {
    CARDIOLOGY("Cardiology", "Heart and cardiovascular system"),
    DERMATOLOGY("Dermatology", "Skin, hair, and nails"),
    PEDIATRICS("Pediatrics", "Child healthcare"),
    ORTHOPEDICS("Orthopedics", "Bones, joints, and muscles"),
    NEUROLOGY("Neurology", "Brain and nervous system"),
    GENERAL("General Medicine", "General health and wellness"),
    PSYCHIATRY("Psychiatry", "Mental health"),
    ONCOLOGY("Oncology", "Cancer treatment"),
    GYNECOLOGY("Gynecology", "Women's reproductive health"),
    UROLOGY("Urology", "Urinary tract and male reproductive system");
    
    private final String displayName;
    private final String description;
    

    Specialization(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public String getDescription() {
        return description;
    }
    
    @Override
    public String toString() {
        return displayName;
    }
}


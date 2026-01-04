package com.airtribe.meditrack.entity;

public abstract class MedicalEntity {
    
    protected int id;
    
    public MedicalEntity() {
    }

    public MedicalEntity(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public abstract String getEntityType();
    
    public boolean isValid() {
        return id > 0;
    }
}


package com.airtribe.meditrack.util;

import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {
    
    private static volatile IdGenerator instance;
    private final AtomicInteger patientIdCounter;
    private final AtomicInteger doctorIdCounter;
    private final AtomicInteger appointmentIdCounter;
    private final AtomicInteger billIdCounter;
    
    private IdGenerator() {
        patientIdCounter = new AtomicInteger(1000);
        doctorIdCounter = new AtomicInteger(2000);
        appointmentIdCounter = new AtomicInteger(3000);
        billIdCounter = new AtomicInteger(4000);
    }
    
    /**
     * Get singleton instance (thread-safe lazy initialization)
     * @return IdGenerator instance
     */
    public static IdGenerator getInstance() {
        if (instance == null) {
            synchronized (IdGenerator.class) {
                if (instance == null) {
                    instance = new IdGenerator();
                }
            }
        }
        return instance;
    }
    
    /**
     * Generate next patient ID
     * @return Next patient ID
     */
    public int getNextPatientId() {
        return patientIdCounter.incrementAndGet();
    }
    
    /**
     * Generate next doctor ID
     * @return Next doctor ID
     */
    public int getNextDoctorId() {
        return doctorIdCounter.incrementAndGet();
    }
    
    /**
     * Generate next appointment ID
     * @return Next appointment ID
     */
    public int getNextAppointmentId() {
        return appointmentIdCounter.incrementAndGet();
    }
    
    /**
     * Generate next bill ID
     * @return Next bill ID
     */
    public int getNextBillId() {
        return billIdCounter.incrementAndGet();
    }
    
    /**
     * Reset all counters (for testing purposes)
     */
    public void reset() {
        patientIdCounter.set(1000);
        doctorIdCounter.set(2000);
        appointmentIdCounter.set(3000);
        billIdCounter.set(4000);
    }
}


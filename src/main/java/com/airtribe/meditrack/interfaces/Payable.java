package com.airtribe.meditrack.interfaces;

public interface Payable {
    
    /**
     * Calculate the total amount to be paid
     * @return Total amount
     */
    double calculateAmount();
    
    /**
     * Generate a bill summary
     * @return Bill summary string
     */
    String generateBillSummary();
}


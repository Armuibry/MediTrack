package com.airtribe.meditrack.util;

import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.constants.Constants;

public class BillFactory {
    
    /**
     * Create a standard consultation bill
     * @param appointmentId Appointment ID
     * @param baseAmount Base amount
     * @return Bill object
     */
    public static Bill createConsultationBill(int appointmentId, double baseAmount) {
        Bill bill = new Bill();
        bill.setId(IdGenerator.getInstance().getNextBillId());
        bill.setAppointmentId(appointmentId);
        bill.setBaseAmount(baseAmount);
        return bill;
    }
    
    /**
     * Create a bill with discount
     * @param appointmentId Appointment ID
     * @param baseAmount Base amount
     * @param discountPercent Discount percentage (0-100)
     * @return Bill object
     */
    public static Bill createDiscountedBill(int appointmentId, double baseAmount, double discountPercent) {
        double discountedAmount = baseAmount * (1 - discountPercent / 100);
        Bill bill = new Bill();
        bill.setId(IdGenerator.getInstance().getNextBillId());
        bill.setAppointmentId(appointmentId);
        bill.setBaseAmount(discountedAmount);
        return bill;
    }
    
    /**
     * Create a premium bill (includes additional services)
     * @param appointmentId Appointment ID
     * @param baseAmount Base amount
     * @param additionalCharges Additional charges
     * @return Bill object
     */
    public static Bill createPremiumBill(int appointmentId, double baseAmount, double additionalCharges) {
        Bill bill = new Bill();
        bill.setId(IdGenerator.getInstance().getNextBillId());
        bill.setAppointmentId(appointmentId);
        bill.setBaseAmount(baseAmount + additionalCharges);
        return bill;
    }
    
    /**
     * Factory method to create bill based on type
     * @param type Bill type (STANDARD, DISCOUNTED, PREMIUM)
     * @param appointmentId Appointment ID
     * @param baseAmount Base amount
     * @param additionalParam Additional parameter (discount % or additional charges)
     * @return Bill object
     */
    public static Bill createBill(String type, int appointmentId, double baseAmount, double additionalParam) {
        switch (type.toUpperCase()) {
            case "STANDARD":
                return createConsultationBill(appointmentId, baseAmount);
            case "DISCOUNTED":
                return createDiscountedBill(appointmentId, baseAmount, additionalParam);
            case "PREMIUM":
                return createPremiumBill(appointmentId, baseAmount, additionalParam);
            default:
                return createConsultationBill(appointmentId, baseAmount);
        }
    }
}


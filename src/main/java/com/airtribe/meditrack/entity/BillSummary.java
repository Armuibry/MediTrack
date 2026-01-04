package com.airtribe.meditrack.entity;

import java.time.LocalDateTime;

public final class BillSummary {
    private final int billId;
    private final int appointmentId;
    private final double baseAmount;
    private final double taxAmount;
    private final double totalAmount;
    private final LocalDateTime billDate;
    private final String paymentStatus;
    
    /**
     * Constructor - only way to create BillSummary
     * @param billId Bill ID
     * @param appointmentId Appointment ID
     * @param baseAmount Base amount
     * @param taxAmount Tax amount
     * @param totalAmount Total amount
     * @param billDate Bill date
     * @param paymentStatus Payment status
     */
    public BillSummary(int billId, int appointmentId, double baseAmount,
                      double taxAmount, double totalAmount, LocalDateTime billDate,
                      String paymentStatus) {
        this.billId = billId;
        this.appointmentId = appointmentId;
        this.baseAmount = baseAmount;
        this.taxAmount = taxAmount;
        this.totalAmount = totalAmount;
        this.billDate = billDate;
        this.paymentStatus = paymentStatus;
    }
        
    public int getBillId() {
        return billId;
    }
    
    public int getAppointmentId() {
        return appointmentId;
    }
    
    public double getBaseAmount() {
        return baseAmount;
    }
    
    public double getTaxAmount() {
        return taxAmount;
    }
    
    public double getTotalAmount() {
        return totalAmount;
    }
    
    public LocalDateTime getBillDate() {
        return billDate;
    }
    
    public String getPaymentStatus() {
        return paymentStatus;
    }
    
    @Override
    public String toString() {
        return "BillSummary{" +
                "billId=" + billId +
                ", appointmentId=" + appointmentId +
                ", baseAmount=" + baseAmount +
                ", taxAmount=" + taxAmount +
                ", totalAmount=" + totalAmount +
                ", billDate=" + billDate +
                ", paymentStatus='" + paymentStatus + '\'' +
                '}';
    }
}


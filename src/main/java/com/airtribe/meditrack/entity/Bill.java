package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.interfaces.Payable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Bill implements Payable {
    
    private int id;
    private int appointmentId;
    private double baseAmount;
    private double taxAmount;
    private double totalAmount;
    private LocalDateTime billDate;
    private String paymentStatus;

    public Bill() {
        this.billDate = LocalDateTime.now();
        this.paymentStatus = "PENDING";
    }

    public Bill(int id, int appointmentId, double baseAmount) {
        this.id = id;
        this.appointmentId = appointmentId;
        this.baseAmount = baseAmount;
        this.taxAmount = baseAmount * Constants.TAX_RATE;
        this.totalAmount = baseAmount + this.taxAmount;
        this.billDate = LocalDateTime.now();
        this.paymentStatus = "PENDING";
    }
        
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getAppointmentId() {
        return appointmentId;
    }
    
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }
    
    public double getBaseAmount() {
        return baseAmount;
    }
    
    public void setBaseAmount(double baseAmount) {
        this.baseAmount = baseAmount;
        this.taxAmount = baseAmount * Constants.TAX_RATE;
        this.totalAmount = baseAmount + this.taxAmount;
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
    
    public void setBillDate(LocalDateTime billDate) {
        this.billDate = billDate;
    }
    
    public String getPaymentStatus() {
        return paymentStatus;
    }
    
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    
    @Override
    public double calculateAmount() {
        return totalAmount;
    }
    
    @Override
    public String generateBillSummary() {
        return String.format(
            "Bill ID: %d\nAppointment ID: %d\nBase Amount: $%.2f\nTax (%.0f%%): $%.2f\nTotal: $%.2f\nDate: %s\nStatus: %s",
            id, appointmentId, baseAmount, Constants.TAX_RATE * 100, taxAmount, totalAmount, billDate, paymentStatus
        );
    }
    
    /**
     * Override generateBill method to demonstrate polymorphism
     * @return Formatted bill string
     */
    public String generateBill() {
        return generateBillSummary();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bill bill = (Bill) o;
        return id == bill.id;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", appointmentId=" + appointmentId +
                ", baseAmount=" + baseAmount +
                ", taxAmount=" + taxAmount +
                ", totalAmount=" + totalAmount +
                ", billDate=" + billDate +
                ", paymentStatus='" + paymentStatus + '\'' +
                '}';
    }
}


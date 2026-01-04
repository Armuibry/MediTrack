package com.airtribe.meditrack.repository;

import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.util.DatabaseUtil;
import com.airtribe.meditrack.util.DateUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BillRepository {
    
    /**
     * Create a new bill
     * @param bill Bill to create
     * @return Created bill with ID
     * @throws SQLException if database operation fails
     */
    public Bill create(Bill bill) throws SQLException {
        String sql = "INSERT INTO bills (id, appointment_id, base_amount, tax_amount, " +
                     "total_amount, bill_date, payment_status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, bill.getId());
            stmt.setInt(2, bill.getAppointmentId());
            stmt.setDouble(3, bill.getBaseAmount());
            stmt.setDouble(4, bill.getTaxAmount());
            stmt.setDouble(5, bill.getTotalAmount());
            stmt.setString(6, DateUtil.formatDateTime(bill.getBillDate()));
            stmt.setString(7, bill.getPaymentStatus());
            
            stmt.executeUpdate();
            return bill;
        }
    }
    
    /**
     * Find bill by ID
     * @param id Bill ID
     * @return Bill if found, null otherwise
     * @throws SQLException if database operation fails
     */
    public Bill findById(int id) throws SQLException {
        String sql = "SELECT * FROM bills WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToBill(rs);
                }
            }
        }
        return null;
    }
    
    /**
     * Find bill by appointment ID
     * @param appointmentId Appointment ID
     * @return Bill if found, null otherwise
     * @throws SQLException if database operation fails
     */
    public Bill findByAppointmentId(int appointmentId) throws SQLException {
        String sql = "SELECT * FROM bills WHERE appointment_id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, appointmentId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToBill(rs);
                }
            }
        }
        return null;
    }
    
    /**
     * Find all bills
     * @return List of all bills
     * @throws SQLException if database operation fails
     */
    public List<Bill> findAll() throws SQLException {
        List<Bill> bills = new ArrayList<>();
        String sql = "SELECT * FROM bills";
        
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                bills.add(mapResultSetToBill(rs));
            }
        }
        return bills;
    }
    
    /**
     * Update bill
     * @param bill Bill to update
     * @return Updated bill
     * @throws SQLException if database operation fails
     */
    public Bill update(Bill bill) throws SQLException {
        String sql = "UPDATE bills SET appointment_id = ?, base_amount = ?, tax_amount = ?, " +
                     "total_amount = ?, bill_date = ?, payment_status = ? WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, bill.getAppointmentId());
            stmt.setDouble(2, bill.getBaseAmount());
            stmt.setDouble(3, bill.getTaxAmount());
            stmt.setDouble(4, bill.getTotalAmount());
            stmt.setString(5, DateUtil.formatDateTime(bill.getBillDate()));
            stmt.setString(6, bill.getPaymentStatus());
            stmt.setInt(7, bill.getId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0 ? bill : null;
        }
    }
    
    /**
     * Map ResultSet to Bill object
     * @param rs ResultSet
     * @return Bill object
     * @throws SQLException if mapping fails
     */
    private Bill mapResultSetToBill(ResultSet rs) throws SQLException {
        Bill bill = new Bill();
        bill.setId(rs.getInt("id"));
        bill.setAppointmentId(rs.getInt("appointment_id"));
        bill.setBaseAmount(rs.getDouble("base_amount"));
        bill.setPaymentStatus(rs.getString("payment_status"));
        
        String dateTimeStr = rs.getString("bill_date");
        if (dateTimeStr != null) {
            bill.setBillDate(DateUtil.parseDateTime(dateTimeStr));
        }
        
        return bill;
    }
}


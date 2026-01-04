package com.airtribe.meditrack.service;

import com.airtribe.meditrack.constants.AppointmentStatus;
import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.entity.BillSummary;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.exception.AppointmentNotFoundException;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.repository.AppointmentRepository;
import com.airtribe.meditrack.repository.BillRepository;
import com.airtribe.meditrack.repository.DoctorRepository;
import com.airtribe.meditrack.util.IdGenerator;
import com.airtribe.meditrack.util.Validator;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class AppointmentService {
    
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final BillRepository billRepository;
    
    public AppointmentService() {
        this.appointmentRepository = new AppointmentRepository();
        this.doctorRepository = new DoctorRepository();
        this.billRepository = new BillRepository();
    }
    
    /**
     * Create a new appointment
     * @param patientId Patient ID
     * @param doctorId Doctor ID
     * @param appointmentDateTime Appointment date and time
     * @param reason Reason for appointment
     * @param notes Additional notes
     * @return Created appointment
     * @throws InvalidDataException if validation fails
     * @throws SQLException if database operation fails
     */
    public Appointment createAppointment(int patientId, int doctorId,
                                        LocalDateTime appointmentDateTime,
                                        String reason, String notes)
            throws InvalidDataException, SQLException {
        
        // Validation
        Validator.validateId(patientId);
        Validator.validateId(doctorId);
        Validator.validateNotNull(appointmentDateTime, "Appointment date/time");
        
        if (appointmentDateTime.isBefore(LocalDateTime.now())) {
            throw new InvalidDataException("Appointment date/time cannot be in the past");
        }
        
        Appointment appointment = new Appointment();
        appointment.setId(IdGenerator.getInstance().getNextAppointmentId());
        appointment.setPatientId(patientId);
        appointment.setDoctorId(doctorId);
        appointment.setAppointmentDateTime(appointmentDateTime);
        appointment.setStatus(AppointmentStatus.PENDING);
        appointment.setReason(reason);
        appointment.setNotes(notes);
        
        return appointmentRepository.create(appointment);
    }
    
    /**
     * Find appointment by ID
     * @param id Appointment ID
     * @return Appointment if found
     * @throws AppointmentNotFoundException if not found
     * @throws InvalidDataException if ID is invalid
     * @throws SQLException if database operation fails
     */
    public Appointment findAppointmentById(int id)
            throws AppointmentNotFoundException, InvalidDataException, SQLException {
        Validator.validateId(id);
        Appointment appointment = appointmentRepository.findById(id);
        if (appointment == null) {
            throw new AppointmentNotFoundException("Appointment with ID " + id + " not found");
        }
        return appointment;
    }
    
    /**
     * Get all appointments
     * @return List of all appointments
     * @throws SQLException if database operation fails
     */
    public List<Appointment> getAllAppointments() throws SQLException {
        return appointmentRepository.findAll();
    }
    
    /**
     * Get appointments by patient ID
     * @param patientId Patient ID
     * @return List of appointments
     * @throws InvalidDataException if ID is invalid
     * @throws SQLException if database operation fails
     */
    public List<Appointment> getAppointmentsByPatientId(int patientId)
            throws InvalidDataException, SQLException {
        Validator.validateId(patientId);
        return appointmentRepository.findByPatientId(patientId);
    }
    
    /**
     * Get appointments by doctor ID
     * @param doctorId Doctor ID
     * @return List of appointments
     * @throws InvalidDataException if ID is invalid
     * @throws SQLException if database operation fails
     */
    public List<Appointment> getAppointmentsByDoctorId(int doctorId)
            throws InvalidDataException, SQLException {
        Validator.validateId(doctorId);
        return appointmentRepository.findByDoctorId(doctorId);
    }
    
    /**
     * Cancel appointment
     * @param appointmentId Appointment ID
     * @return Cancelled appointment
     * @throws AppointmentNotFoundException if not found
     * @throws InvalidDataException if ID is invalid
     * @throws SQLException if database operation fails
     */
    public Appointment cancelAppointment(int appointmentId)
            throws AppointmentNotFoundException, InvalidDataException, SQLException {
        Appointment appointment = findAppointmentById(appointmentId);
        appointment.cancel();
        return appointmentRepository.update(appointment);
    }
    
    /**
     * Confirm appointment
     * @param appointmentId Appointment ID
     * @return Confirmed appointment
     * @throws AppointmentNotFoundException if not found
     * @throws InvalidDataException if ID is invalid
     * @throws SQLException if database operation fails
     */
    public Appointment confirmAppointment(int appointmentId)
            throws AppointmentNotFoundException, InvalidDataException, SQLException {
        Appointment appointment = findAppointmentById(appointmentId);
        appointment.confirm();
        return appointmentRepository.update(appointment);
    }
    
    /**
     * Create bill for appointment
     * @param appointmentId Appointment ID
     * @return Created bill
     * @throws AppointmentNotFoundException if appointment not found
     * @throws InvalidDataException if validation fails
     * @throws SQLException if database operation fails
     */
    public Bill createBill(int appointmentId)
            throws AppointmentNotFoundException, InvalidDataException, SQLException {
        
        Appointment appointment = findAppointmentById(appointmentId);
        Doctor doctor = doctorRepository.findById(appointment.getDoctorId());
        
        if (doctor == null) {
            throw new InvalidDataException("Doctor not found for appointment");
        }
        
        Bill bill = new Bill();
        bill.setId(IdGenerator.getInstance().getNextBillId());
        bill.setAppointmentId(appointmentId);
        bill.setBaseAmount(doctor.getConsultationFee());
        
        return billRepository.create(bill);
    }
    
    /**
     * Generate bill summary (immutable)
     * @param appointmentId Appointment ID
     * @return BillSummary object
     * @throws AppointmentNotFoundException if appointment not found
     * @throws InvalidDataException if validation fails
     * @throws SQLException if database operation fails
     */
    public BillSummary generateBillSummary(int appointmentId)
            throws AppointmentNotFoundException, InvalidDataException, SQLException {
        
        Bill bill = billRepository.findByAppointmentId(appointmentId);
        if (bill == null) {
            throw new InvalidDataException("Bill not found for appointment ID: " + appointmentId);
        }
        
        return new BillSummary(
            bill.getId(),
            bill.getAppointmentId(),
            bill.getBaseAmount(),
            bill.getTaxAmount(),
            bill.getTotalAmount(),
            bill.getBillDate(),
            bill.getPaymentStatus()
        );
    }
    
    /**
     * Update appointment
     * @param appointment Appointment to update
     * @return Updated appointment
     * @throws InvalidDataException if validation fails
     * @throws SQLException if database operation fails
     */
    public Appointment updateAppointment(Appointment appointment)
            throws InvalidDataException, SQLException {
        Validator.validateId(appointment.getId());
        Validator.validateId(appointment.getPatientId());
        Validator.validateId(appointment.getDoctorId());
        
        return appointmentRepository.update(appointment);
    }
    
    /**
     * Delete appointment by ID
     * @param id Appointment ID
     * @return true if deleted, false otherwise
     * @throws InvalidDataException if ID is invalid
     * @throws SQLException if database operation fails
     */
    public boolean deleteAppointment(int id) throws InvalidDataException, SQLException {
        Validator.validateId(id);
        return appointmentRepository.delete(id);
    }
}


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

/**
 *
 * @author BRAVO15
 */
@Entity
@Table(name = "appointment")
@NamedQueries({
    @NamedQuery(name = "Appointment.findAll", query = "SELECT a FROM Appointment a"),
    @NamedQuery(name = "Appointment.findById", query = "SELECT a FROM Appointment a WHERE a.id = :id"),
    @NamedQuery(name = "Appointment.findByCreatedDate", query = "SELECT a FROM Appointment a WHERE a.createdDate = :createdDate"),
    @NamedQuery(name = "Appointment.findByStatus", query = "SELECT a FROM Appointment a WHERE a.status = :status"),
    @NamedQuery(name = "Appointment.findByOnline", query = "SELECT a FROM Appointment a WHERE a.online = :online"),
    @NamedQuery(name = "Appointment.findByRoomUrl", query = "SELECT a FROM Appointment a WHERE a.roomUrl = :roomUrl"),
    @NamedQuery(name = "Appointment.findByPaymentForService", query = "SELECT a FROM Appointment a WHERE a.paymentForService = :paymentForService"),
    @NamedQuery(name = "Appointment.findByPaymentForPrescription", query = "SELECT a FROM Appointment a WHERE a.paymentForPrescription = :paymentForPrescription")})
@JsonIgnoreProperties(value = {"medicalrecordSet", "patientId", "serviceId", "paymentSet"})
public class Appointment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "created_date")
    private LocalDate createdDate;
    @Size(max = 9)
    @Column(name = "status")
    private String status;
    @Column(name = "online")
    private Boolean online;
    @Size(max = 255)
    @Column(name = "room_url")
    private String roomUrl;
    @Column(name = "payment_for_service")
    private Boolean paymentForService;
    @Column(name = "payment_for_prescription")
    private Boolean paymentForPrescription;
    @OneToMany(mappedBy = "appointmentId")
    private Set<Medicalrecord> medicalrecordSet;
    @JoinColumn(name = "appointment_slot", referencedColumnName = "id", unique = true)
    @OneToOne
    @JsonManagedReference
    private Appointmentslot appointmentSlot;
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    @ManyToOne
    private Patient patientId;
    @JoinColumn(name = "service_id", referencedColumnName = "id")
    @ManyToOne
    private Service serviceId;
    @OneToMany(mappedBy = "appointmentId")
    private Set<Payment> paymentSet;

    public Appointment() {
    }

    public Appointment(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public String getRoomUrl() {
        return roomUrl;
    }

    public void setRoomUrl(String roomUrl) {
        this.roomUrl = roomUrl;
    }

    public Boolean getPaymentForService() {
        return paymentForService;
    }

    public void setPaymentForService(Boolean paymentForService) {
        this.paymentForService = paymentForService;
    }

    public Boolean getPaymentForPrescription() {
        return paymentForPrescription;
    }

    public void setPaymentForPrescription(Boolean paymentForPrescription) {
        this.paymentForPrescription = paymentForPrescription;
    }

    public Set<Medicalrecord> getMedicalrecordSet() {
        return medicalrecordSet;
    }

    public void setMedicalrecordSet(Set<Medicalrecord> medicalrecordSet) {
        this.medicalrecordSet = medicalrecordSet;
    }

    public Appointmentslot getAppointmentSlot() {
        return appointmentSlot;
    }

    public void setAppointmentSlot(Appointmentslot appointmentSlot) {
        this.appointmentSlot = appointmentSlot;
    }

    public Patient getPatientId() {
        return patientId;
    }

    public void setPatientId(Patient patientId) {
        this.patientId = patientId;
    }

    public Service getServiceId() {
        return serviceId;
    }

    public void setServiceId(Service serviceId) {
        this.serviceId = serviceId;
    }

    public Set<Payment> getPaymentSet() {
        return paymentSet;
    }

    public void setPaymentSet(Set<Payment> paymentSet) {
        this.paymentSet = paymentSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Appointment)) {
            return false;
        }
        Appointment other = (Appointment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vvmntl.pojo.Appointment[ id=" + id + " ]";
    }
    
}

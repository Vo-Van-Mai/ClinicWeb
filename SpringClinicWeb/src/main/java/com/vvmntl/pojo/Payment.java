/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 *
 * @author BRAVO15
 */
@Entity
@Table(name = "payment")
@NamedQueries({
    @NamedQuery(name = "Payment.findAll", query = "SELECT p FROM Payment p"),
    @NamedQuery(name = "Payment.findById", query = "SELECT p FROM Payment p WHERE p.id = :id"),
    @NamedQuery(name = "Payment.findByMethod", query = "SELECT p FROM Payment p WHERE p.method = :method"),
    @NamedQuery(name = "Payment.findByTotalAmount", query = "SELECT p FROM Payment p WHERE p.totalAmount = :totalAmount"),
    @NamedQuery(name = "Payment.findByCreatedDate", query = "SELECT p FROM Payment p WHERE p.createdDate = :createdDate"),
    @NamedQuery(name = "Payment.findByTypeServicePayment", query = "SELECT p FROM Payment p WHERE p.typeServicePayment = :typeServicePayment")})
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "method")
    private PaymentMethodType method;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "total_amount")
    private BigDecimal totalAmount;
    
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    
    @Size(max = 12)
    @Column(name = "type_service_payment")
    private String typeServicePayment;
    
    @OneToMany(mappedBy = "paymentId")
    private Set<Paymentdetail> paymentdetailSet;
    
    @JoinColumn(name = "appointment_id", referencedColumnName = "id")
    @ManyToOne
    private Appointment appointmentId;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PaymentStatus status;

    @Column(name = "transaction_id", unique = true)
    private String transactionId;
    
    public Payment() {
    }

    public Payment(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getTypeServicePayment() {
        return typeServicePayment;
    }

    public void setTypeServicePayment(String typeServicePayment) {
        this.typeServicePayment = typeServicePayment;
    }

    public Set<Paymentdetail> getPaymentdetailSet() {
        return paymentdetailSet;
    }

    public void setPaymentdetailSet(Set<Paymentdetail> paymentdetailSet) {
        this.paymentdetailSet = paymentdetailSet;
    }

    public Appointment getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Appointment appointmentId) {
        this.appointmentId = appointmentId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Payment)) {
            return false;
        }
        Payment other = (Payment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vvmntl.pojo.Payment[ id=" + id + " ]";
    }

    /**
     * @return the status
     */
    public PaymentStatus getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    /**
     * @return the transactionId
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * @param transactionId the transactionId to set
     */
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * @return the method
     */
    public PaymentMethodType getMethod() {
        return method;
    }

    /**
     * @param method the method to set
     */
    public void setMethod(PaymentMethodType method) {
        this.method = method;
    }
    
}

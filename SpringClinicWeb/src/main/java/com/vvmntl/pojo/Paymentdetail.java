/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.pojo;

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
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author BRAVO15
 */
@Entity
@Table(name = "paymentdetail")
@NamedQueries({
    @NamedQuery(name = "Paymentdetail.findAll", query = "SELECT p FROM Paymentdetail p"),
    @NamedQuery(name = "Paymentdetail.findById", query = "SELECT p FROM Paymentdetail p WHERE p.id = :id"),
    @NamedQuery(name = "Paymentdetail.findByAmount", query = "SELECT p FROM Paymentdetail p WHERE p.amount = :amount"),
    @NamedQuery(name = "Paymentdetail.findByReferenceId", query = "SELECT p FROM Paymentdetail p WHERE p.referenceId = :referenceId"),
    @NamedQuery(name = "Paymentdetail.findByStatus", query = "SELECT p FROM Paymentdetail p WHERE p.status = :status")})
public class Paymentdetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "reference_id")
    private Integer referenceId;
    @Size(max = 10)
    @Column(name = "status")
    private String status;
    @JoinColumn(name = "payment_id", referencedColumnName = "id")
    @ManyToOne
    private Payment paymentId;

    public Paymentdetail() {
    }

    public Paymentdetail(Integer id) {
        this.id = id;
    }

    public Paymentdetail(Integer id, BigDecimal amount) {
        this.id = id;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Integer referenceId) {
        this.referenceId = referenceId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Payment getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Payment paymentId) {
        this.paymentId = paymentId;
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
        if (!(object instanceof Paymentdetail)) {
            return false;
        }
        Paymentdetail other = (Paymentdetail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vvmntl.pojo.Paymentdetail[ id=" + id + " ]";
    }
    
}

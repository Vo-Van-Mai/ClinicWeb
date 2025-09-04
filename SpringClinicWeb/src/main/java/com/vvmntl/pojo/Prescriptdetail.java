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
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author BRAVO15
 */
@Entity
@Table(name = "prescriptdetail")
@NamedQueries({
    @NamedQuery(name = "Prescriptdetail.findAll", query = "SELECT p FROM Prescriptdetail p"),
    @NamedQuery(name = "Prescriptdetail.findById", query = "SELECT p FROM Prescriptdetail p WHERE p.id = :id"),
    @NamedQuery(name = "Prescriptdetail.findByQuantiry", query = "SELECT p FROM Prescriptdetail p WHERE p.quantiry = :quantiry"),
    @NamedQuery(name = "Prescriptdetail.findByPriceNow", query = "SELECT p FROM Prescriptdetail p WHERE p.priceNow = :priceNow")})
public class Prescriptdetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "quantiry")
    private Integer quantiry;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "price_now")
    private BigDecimal priceNow;
    @JoinColumn(name = "medicine_id", referencedColumnName = "id")
    @ManyToOne
    private Medicine medicineId;
    @JoinColumn(name = "prescription_id", referencedColumnName = "id")
    @ManyToOne
    private Prescription prescriptionId;

    public Prescriptdetail() {
    }

    public Prescriptdetail(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantiry() {
        return quantiry;
    }

    public void setQuantiry(Integer quantiry) {
        this.quantiry = quantiry;
    }

    public BigDecimal getPriceNow() {
        return priceNow;
    }

    public void setPriceNow(BigDecimal priceNow) {
        this.priceNow = priceNow;
    }

    public Medicine getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(Medicine medicineId) {
        this.medicineId = medicineId;
    }

    public Prescription getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(Prescription prescriptionId) {
        this.prescriptionId = prescriptionId;
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
        if (!(object instanceof Prescriptdetail)) {
            return false;
        }
        Prescriptdetail other = (Prescriptdetail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vvmntl.pojo.Prescriptdetail[ id=" + id + " ]";
    }
    
}

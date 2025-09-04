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
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 *
 * @author BRAVO15
 */
@Entity
@Table(name = "prescription")
@NamedQueries({
    @NamedQuery(name = "Prescription.findAll", query = "SELECT p FROM Prescription p"),
    @NamedQuery(name = "Prescription.findById", query = "SELECT p FROM Prescription p WHERE p.id = :id"),
    @NamedQuery(name = "Prescription.findByCreatedDate", query = "SELECT p FROM Prescription p WHERE p.createdDate = :createdDate"),
    @NamedQuery(name = "Prescription.findByFrequency", query = "SELECT p FROM Prescription p WHERE p.frequency = :frequency"),
    @NamedQuery(name = "Prescription.findByDuration", query = "SELECT p FROM Prescription p WHERE p.duration = :duration")})
public class Prescription implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Lob
    @Size(max = 65535)
    @Column(name = "note")
    private String note;
    @Column(name = "frequency")
    private Integer frequency;
    @Column(name = "duration")
    private Integer duration;
    @Lob
    @Size(max = 65535)
    @Column(name = "intruction")
    private String intruction;
    @OneToMany(mappedBy = "prescriptionId")
    private Set<Prescriptdetail> prescriptdetailSet;
    @JoinColumn(name = "medicalrecord_id", referencedColumnName = "id")
    @ManyToOne
    private Medicalrecord medicalrecordId;

    public Prescription() {
    }

    public Prescription(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getIntruction() {
        return intruction;
    }

    public void setIntruction(String intruction) {
        this.intruction = intruction;
    }

    public Set<Prescriptdetail> getPrescriptdetailSet() {
        return prescriptdetailSet;
    }

    public void setPrescriptdetailSet(Set<Prescriptdetail> prescriptdetailSet) {
        this.prescriptdetailSet = prescriptdetailSet;
    }

    public Medicalrecord getMedicalrecordId() {
        return medicalrecordId;
    }

    public void setMedicalrecordId(Medicalrecord medicalrecordId) {
        this.medicalrecordId = medicalrecordId;
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
        if (!(object instanceof Prescription)) {
            return false;
        }
        Prescription other = (Prescription) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vvmntl.pojo.Prescription[ id=" + id + " ]";
    }
    
}

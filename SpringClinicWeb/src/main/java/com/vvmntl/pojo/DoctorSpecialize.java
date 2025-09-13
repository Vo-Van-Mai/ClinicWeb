/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author BRAVO15
 */
@Entity
@Table(name = "doctor_specialize")
@NamedQueries({
    @NamedQuery(name = "DoctorSpecialize.findAll", query = "SELECT d FROM DoctorSpecialize d"),
    @NamedQuery(name = "DoctorSpecialize.findById", query = "SELECT d FROM DoctorSpecialize d WHERE d.id = :id"),
    @NamedQuery(name = "DoctorSpecialize.findByJoinDate", query = "SELECT d FROM DoctorSpecialize d WHERE d.joinDate = :joinDate")})
public class DoctorSpecialize implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "join_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date joinDate;
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    @ManyToOne
    @JsonIgnoreProperties("doctorSpecializeSet")
    private Doctor doctorId;
    @JoinColumn(name = "specialize_id", referencedColumnName = "id")
    @ManyToOne
    @JsonIgnoreProperties("doctorSpecializeSet")
    private Specialize specializeId;

    public DoctorSpecialize() {
    }

    public DoctorSpecialize(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public Doctor getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Doctor doctorId) {
        this.doctorId = doctorId;
    }

    public Specialize getSpecializeId() {
        return specializeId;
    }

    public void setSpecializeId(Specialize specializeId) {
        this.specializeId = specializeId;
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
        if (!(object instanceof DoctorSpecialize)) {
            return false;
        }
        DoctorSpecialize other = (DoctorSpecialize) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vvmntl.pojo.DoctorSpecialize[ id=" + id + " ]";
    }
    
}

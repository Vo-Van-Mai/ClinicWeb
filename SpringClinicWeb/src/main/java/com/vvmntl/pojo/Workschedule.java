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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 *
 * @author BRAVO15
 */
@Entity
@Table(name = "workschedule")
@NamedQueries({
    @NamedQuery(name = "Workschedule.findAll", query = "SELECT w FROM Workschedule w"),
    @NamedQuery(name = "Workschedule.findById", query = "SELECT w FROM Workschedule w WHERE w.id = :id"),
    @NamedQuery(name = "Workschedule.findByDateWork", query = "SELECT w FROM Workschedule w WHERE w.dateWork = :dateWork"),
    @NamedQuery(name = "Workschedule.findByStartTime", query = "SELECT w FROM Workschedule w WHERE w.startTime = :startTime"),
    @NamedQuery(name = "Workschedule.findByEndTime", query = "SELECT w FROM Workschedule w WHERE w.endTime = :endTime")})
public class Workschedule implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "date_work")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateWork;
    @Column(name = "start_time")
    @Temporal(TemporalType.TIME)
    private Date startTime;
    @Column(name = "end_time")
    @Temporal(TemporalType.TIME)
    private Date endTime;
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    @ManyToOne
    private Doctor doctorId;
    @OneToMany(mappedBy = "scheduleId")
    private Set<Appointmentslot> appointmentslotSet;

    public Workschedule() {
    }

    public Workschedule(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateWork() {
        return dateWork;
    }

    public void setDateWork(Date dateWork) {
        this.dateWork = dateWork;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Doctor getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Doctor doctorId) {
        this.doctorId = doctorId;
    }

    public Set<Appointmentslot> getAppointmentslotSet() {
        return appointmentslotSet;
    }

    public void setAppointmentslotSet(Set<Appointmentslot> appointmentslotSet) {
        this.appointmentslotSet = appointmentslotSet;
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
        if (!(object instanceof Workschedule)) {
            return false;
        }
        Workschedule other = (Workschedule) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vvmntl.pojo.Workschedule[ id=" + id + " ]";
    }
    
}

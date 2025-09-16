/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
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
    @NotNull(message = "{workschadule.dateWord.nullErr}")
    @FutureOrPresent(message = "{wordschedule.dateWork.timeErr}")
    @Column(name = "date_work")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateWork; 
    @NotNull(message = "{workschedule.startTime.nullErr}")
    @Column(name = "start_time")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;
    @NotNull(message = "{workschedule.endTime.nullErr}")
    @Column(name = "end_time")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    @ManyToOne
    private Doctor doctorId;
    @JsonIgnore
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

    public LocalDate getDateWork() {
        return dateWork;
    }

    public void setDateWork(LocalDate dateWork) {
        this.dateWork = dateWork;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
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

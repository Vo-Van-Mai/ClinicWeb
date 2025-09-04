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
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 *
 * @author BRAVO15
 */
@Entity
@Table(name = "appointmentslot")
@NamedQueries({
    @NamedQuery(name = "Appointmentslot.findAll", query = "SELECT a FROM Appointmentslot a"),
    @NamedQuery(name = "Appointmentslot.findById", query = "SELECT a FROM Appointmentslot a WHERE a.id = :id"),
    @NamedQuery(name = "Appointmentslot.findByStartTime", query = "SELECT a FROM Appointmentslot a WHERE a.startTime = :startTime"),
    @NamedQuery(name = "Appointmentslot.findByEndTime", query = "SELECT a FROM Appointmentslot a WHERE a.endTime = :endTime"),
    @NamedQuery(name = "Appointmentslot.findByIsBooked", query = "SELECT a FROM Appointmentslot a WHERE a.isBooked = :isBooked")})
public class Appointmentslot implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "start_time")
    @Temporal(TemporalType.TIME)
    private Date startTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "end_time")
    @Temporal(TemporalType.TIME)
    private Date endTime;
    @Column(name = "is_booked")
    private Boolean isBooked;
    @OneToMany(mappedBy = "appointmentSlot")
    private Set<Appointment> appointmentSet;
    @JoinColumn(name = "schedule_id", referencedColumnName = "id")
    @ManyToOne
    private Workschedule scheduleId;

    public Appointmentslot() {
    }

    public Appointmentslot(Integer id) {
        this.id = id;
    }

    public Appointmentslot(Integer id, Date startTime, Date endTime) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Boolean getIsBooked() {
        return isBooked;
    }

    public void setIsBooked(Boolean isBooked) {
        this.isBooked = isBooked;
    }

    public Set<Appointment> getAppointmentSet() {
        return appointmentSet;
    }

    public void setAppointmentSet(Set<Appointment> appointmentSet) {
        this.appointmentSet = appointmentSet;
    }

    public Workschedule getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Workschedule scheduleId) {
        this.scheduleId = scheduleId;
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
        if (!(object instanceof Appointmentslot)) {
            return false;
        }
        Appointmentslot other = (Appointmentslot) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vvmntl.pojo.Appointmentslot[ id=" + id + " ]";
    }
    
}

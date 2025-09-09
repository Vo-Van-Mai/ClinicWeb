/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.pojo;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

/**
 *
 * @author BRAVO15
 */
@Entity
@Table(name = "doctor")
@NamedQueries({
    @NamedQuery(name = "Doctor.findAll", query = "SELECT d FROM Doctor d"),
    @NamedQuery(name = "Doctor.findById", query = "SELECT d FROM Doctor d WHERE d.id = :id"),
    @NamedQuery(name = "Doctor.findByYearOfExperience", query = "SELECT d FROM Doctor d WHERE d.yearOfExperience = :yearOfExperience"),
    @NamedQuery(name = "Doctor.findByLicenseNumber", query = "SELECT d FROM Doctor d WHERE d.licenseNumber = :licenseNumber"),
    @NamedQuery(name = "Doctor.findByIsVerified", query = "SELECT d FROM Doctor d WHERE d.isVerified = :isVerified")})
public class Doctor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Lob
    @Size(max = 65535)
    @Column(name = "experience")
    private String experience;
    @Column(name = "year_of_experience")
    private Integer yearOfExperience;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "license_number")
    private String licenseNumber;
    @Column(name = "is_verified")
    private Boolean isVerified;
    @OneToMany(mappedBy = "doctorId")
    private Set<Rating> ratingSet;
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private User user;
    @OneToMany(mappedBy = "doctorId")
    private Set<Chat> chatSet;
    @OneToMany(mappedBy = "doctorId")
    private Set<DoctorSpecialize> doctorSpecializeSet;
    @OneToMany(mappedBy = "doctorId")
    private Set<Workschedule> workscheduleSet;

    public Doctor() {
    }

    public Doctor(Integer id) {
        this.id = id;
    }

    public Doctor(Integer id, String licenseNumber) {
        this.id = id;
        this.licenseNumber = licenseNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public Integer getYearOfExperience() {
        return yearOfExperience;
    }

    public void setYearOfExperience(Integer yearOfExperience) {
        this.yearOfExperience = yearOfExperience;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public Boolean getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }

    public Set<Rating> getRatingSet() {
        return ratingSet;
    }

    public void setRatingSet(Set<Rating> ratingSet) {
        this.ratingSet = ratingSet;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Chat> getChatSet() {
        return chatSet;
    }

    public void setChatSet(Set<Chat> chatSet) {
        this.chatSet = chatSet;
    }

    public Set<DoctorSpecialize> getDoctorSpecializeSet() {
        return doctorSpecializeSet;
    }

    public void setDoctorSpecializeSet(Set<DoctorSpecialize> doctorSpecializeSet) {
        this.doctorSpecializeSet = doctorSpecializeSet;
    }

    public Set<Workschedule> getWorkscheduleSet() {
        return workscheduleSet;
    }

    public void setWorkscheduleSet(Set<Workschedule> workscheduleSet) {
        this.workscheduleSet = workscheduleSet;
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
        if (!(object instanceof Doctor)) {
            return false;
        }
        Doctor other = (Doctor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vvmntl.pojo.Doctor[ id=" + id + " ]";
    }
    
}

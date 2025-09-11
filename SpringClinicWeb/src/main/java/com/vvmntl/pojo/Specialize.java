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
import jakarta.persistence.Lob;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
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
@Table(name = "specialize")
@NamedQueries({
    @NamedQuery(name = "Specialize.findAll", query = "SELECT s FROM Specialize s"),
    @NamedQuery(name = "Specialize.findById", query = "SELECT s FROM Specialize s WHERE s.id = :id"),
    @NamedQuery(name = "Specialize.findByName", query = "SELECT s FROM Specialize s WHERE s.name = :name")})
public class Specialize implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull(message = "{specialize.name.nullErr}")
    @Size(min = 1, max = 255, message = "{specialize.name.lenErr}")
    @Column(name = "name")
    private String name;
    @Lob
    @Size(max = 65535)
    @Column(name = "description")
    private String description;
    @OneToMany(mappedBy = "specializeId")
    private Set<Service> serviceSet;
    @OneToMany(mappedBy = "specializeId")
    private Set<DoctorSpecialize> doctorSpecializeSet;
    @Column(name = "is_active", columnDefinition = "boolean default true")
    private Boolean isActive = true;

    public Specialize() {
    }

    public Specialize(Integer id) {
        this.id = id;
    }

    public Specialize(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Service> getServiceSet() {
        return serviceSet;
    }

    public void setServiceSet(Set<Service> serviceSet) {
        this.serviceSet = serviceSet;
    }

    public Set<DoctorSpecialize> getDoctorSpecializeSet() {
        return doctorSpecializeSet;
    }

    public void setDoctorSpecializeSet(Set<DoctorSpecialize> doctorSpecializeSet) {
        this.doctorSpecializeSet = doctorSpecializeSet;
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
        if (!(object instanceof Specialize)) {
            return false;
        }
        Specialize other = (Specialize) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vvmntl.pojo.Specialize[ id=" + id + " ]";
    }

    /**
     * @return the isActive
     */
    public Boolean getIsActive() {
        return isActive;
    }

    /**
     * @param isActive the isActive to set
     */
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    
}

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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "medicine")
@NamedQueries({
    @NamedQuery(name = "Medicine.findAll", query = "SELECT m FROM Medicine m"),
    @NamedQuery(name = "Medicine.findById", query = "SELECT m FROM Medicine m WHERE m.id = :id"),
    @NamedQuery(name = "Medicine.findByName", query = "SELECT m FROM Medicine m WHERE m.name = :name"),
    @NamedQuery(name = "Medicine.findByBrandName", query = "SELECT m FROM Medicine m WHERE m.brandName = :brandName"),
    @NamedQuery(name = "Medicine.findByQuantity", query = "SELECT m FROM Medicine m WHERE m.quantity = :quantity"),
    @NamedQuery(name = "Medicine.findByUnit", query = "SELECT m FROM Medicine m WHERE m.unit = :unit"),
    @NamedQuery(name = "Medicine.findByExpiryDate", query = "SELECT m FROM Medicine m WHERE m.expiryDate = :expiryDate"),
    @NamedQuery(name = "Medicine.findByPrice", query = "SELECT m FROM Medicine m WHERE m.price = :price"),
    @NamedQuery(name = "Medicine.findByInsuranceCovered", query = "SELECT m FROM Medicine m WHERE m.insuranceCovered = :insuranceCovered"),
    @NamedQuery(name = "Medicine.findByStock", query = "SELECT m FROM Medicine m WHERE m.stock = :stock"),
    @NamedQuery(name = "Medicine.findByStatus", query = "SELECT m FROM Medicine m WHERE m.status = :status"),
    @NamedQuery(name = "Medicine.findByUpdatedDate", query = "SELECT m FROM Medicine m WHERE m.updatedDate = :updatedDate"),
    @NamedQuery(name = "Medicine.findByCreatedDate", query = "SELECT m FROM Medicine m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "Medicine.findByMedicinecol", query = "SELECT m FROM Medicine m WHERE m.medicinecol = :medicinecol")})
public class Medicine implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name")
    private String name;
    @Size(max = 255)
    @Column(name = "brand_name")
    private String brandName;
    @Column(name = "quantity")
    private Integer quantity;
    @Size(max = 45)
    @Column(name = "unit")
    private String unit;
    @Lob
    @Size(max = 65535)
    @Column(name = "description")
    private String description;
    @Column(name = "expiry_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiryDate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "insurance_covered")
    private Boolean insuranceCovered;
    @Column(name = "stock")
    private Integer stock;
    @Size(max = 12)
    @Column(name = "status")
    private String status;
    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Size(max = 45)
    @Column(name = "medicinecol")
    private String medicinecol;
    @OneToMany(mappedBy = "medicineId")
    private Set<Prescriptdetail> prescriptdetailSet;
    @OneToMany(mappedBy = "medicineId")
    private Set<CategoryMedicine> categoryMedicineSet;

    public Medicine() {
    }

    public Medicine(Integer id) {
        this.id = id;
    }

    public Medicine(Integer id, String name) {
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

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getInsuranceCovered() {
        return insuranceCovered;
    }

    public void setInsuranceCovered(Boolean insuranceCovered) {
        this.insuranceCovered = insuranceCovered;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getMedicinecol() {
        return medicinecol;
    }

    public void setMedicinecol(String medicinecol) {
        this.medicinecol = medicinecol;
    }

    public Set<Prescriptdetail> getPrescriptdetailSet() {
        return prescriptdetailSet;
    }

    public void setPrescriptdetailSet(Set<Prescriptdetail> prescriptdetailSet) {
        this.prescriptdetailSet = prescriptdetailSet;
    }

    public Set<CategoryMedicine> getCategoryMedicineSet() {
        return categoryMedicineSet;
    }

    public void setCategoryMedicineSet(Set<CategoryMedicine> categoryMedicineSet) {
        this.categoryMedicineSet = categoryMedicineSet;
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
        if (!(object instanceof Medicine)) {
            return false;
        }
        Medicine other = (Medicine) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vvmntl.pojo.Medicine[ id=" + id + " ]";
    }
    
}

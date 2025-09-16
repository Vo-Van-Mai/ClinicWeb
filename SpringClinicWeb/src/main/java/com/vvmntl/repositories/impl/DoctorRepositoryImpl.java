/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.repositories.impl;

import com.vvmntl.exception.ResourceNotFoundException;
import com.vvmntl.pojo.Doctor;
import com.vvmntl.pojo.DoctorSpecialize;
import com.vvmntl.pojo.Role;
import com.vvmntl.pojo.Specialize;
import com.vvmntl.pojo.User;
import com.vvmntl.repositories.DoctorRepository;
import com.vvmntl.services.UserService;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author BRAVO15
 */
@Repository
@Transactional
public class DoctorRepositoryImpl implements DoctorRepository {

    private static final int PAGE_SIZE = 10;
    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private UserService userService;

    @Override
    public List<Doctor> list() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createNamedQuery("Doctor.findAll", Doctor.class);
        return q.getResultList();
    }

    @Override
    public List<Doctor> getDoctor(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Doctor> query = b.createQuery(Doctor.class);
        Root<Doctor> r = query.from(Doctor.class);
//        r.fetch("doctorSpecializeSet", JoinType.LEFT);

        query.select(r).distinct(true);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();

            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                predicates.add(b.like(r.get("user").get("firstName"), String.format("%%%s%%", kw)));
            }
            
            String expYear = params.get("minYear");
            if (expYear != null && !expYear.isEmpty()) {
                predicates.add(
                    b.greaterThanOrEqualTo(r.get("yearOfExperience"), Integer.valueOf(expYear))
                );
            }

            String verified = params.get("isVerified");
            if (verified != null && !verified.isEmpty()) {
                predicates.add(
                    b.equal(r.get("isVerified"), Boolean.valueOf(verified))
                );
            }

            String license = params.get("licenseNumber");
            if (license != null && !license.isEmpty()) {
                predicates.add(
                    b.equal(r.get("licenseNumber"), license)
                );
            }

            String specialize = params.get("specialize");
            if (specialize != null && !specialize.isEmpty()) {
                Join<Object, Object> sp = r.join("doctorSpecializeSet", JoinType.LEFT);
                predicates.add(
                    b.equal(sp.get("specializeId").get("name"), specialize)
                );
            }
            
            if (!predicates.isEmpty()) {
                query.where(b.and(predicates.toArray(Predicate[]::new)));
            }
        }
        Query q = s.createQuery(query);

        if (params != null && !params.isEmpty()) {
            String p = params.getOrDefault("page", "1");
            int page = Integer.parseInt(p);
            int start = (page - 1) * PAGE_SIZE;
            q.setMaxResults(PAGE_SIZE);
            q.setFirstResult(start);
        }

        return q.getResultList();
    }

    @Override
    public List<Doctor> getDoctorBySpecializeId(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Doctor> query = b.createQuery(Doctor.class);
        Root<Doctor> r = query.from(Doctor.class);
        Join<Doctor, DoctorSpecialize> dsJoin = r.join("doctorSpecializeSet");
        Join<DoctorSpecialize, Specialize> sJoin = dsJoin.join("specializeId");

        query.select(r).where(b.equal(sJoin.get("id"), id));

        Query q = s.createQuery(query);
        return q.getResultList();
    }

    @Override
    public boolean deleteDoctor(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Doctor d = this.getDoctorById(id);
        if (d != null) {
            s.remove(d);
            return true;
        }
        return false;
    }

    @Override
    public Doctor getDoctorById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.find(Doctor.class, id);
    }

    @Override
    public Doctor addDoctor(User u, Doctor d) {
        Session s = this.factory.getObject().getCurrentSession();
        if (d.getUser() == null) {
            throw new ResourceNotFoundException("Không tìm thấy người dùng");
        }
        d.setId(u.getId());
        s.persist(d);
        return d;
    }

    @Override
    public Doctor updateDoctor(Doctor d) {
        Session s = this.factory.getObject().getCurrentSession();
        try {
            Doctor existingDoctor = this.getDoctorById(d.getId());
            if (existingDoctor == null) {
                throw new RuntimeException("Doctor with ID " + d.getId() + " not found");
            }
            if (d.getUser() == null) {
                throw new RuntimeException("User information is required for a Doctor");
            }
            if (d.getUser().getId() != d.getId()) {
                throw new RuntimeException("User ID and Doctor ID do not match");
            }

            if (d.getIsVerified() == null) {
                d.setIsVerified(existingDoctor.getIsVerified());
            }
            s.merge(d.getUser());
            Doctor updatedDoctor = (Doctor) s.merge(d);
            s.flush();
            return updatedDoctor;
        } catch (Exception e) {
            throw new RuntimeException("Error updating doctor: " + e.getMessage(), e);
        }
    }

    @Override
    public long countDoctor(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Long> query = b.createQuery(Long.class);
        Root<Doctor> r = query.from(Doctor.class);

        query.select(b.countDistinct(r));

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();
            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                predicates.add(b.like(r.get("user").get("firstName"), String.format("%%%s%%", kw)));
            }
            query.where(predicates.toArray(new Predicate[0]));
        }

        return s.createQuery(query).getSingleResult();
    }

    @Override
    public boolean verifiedDoctor(Doctor d) {
        Session s = this.factory.getObject().getCurrentSession();
        Doctor doctor = this.getDoctorById(d.getId());
        if(doctor == null){
            throw new ResourceNotFoundException("Không tìm thấy bác sĩ!");
        }
        else{
            if (doctor.getLicenseNumber()==null || doctor.getLicenseNumber().isEmpty()){
                throw new ResourceNotFoundException("Bác sĩ chưa cung cấp mã!");
            }
            doctor.setIsVerified(true);
            return true;
        }
    }

    @Override
    public boolean cancelDoctor(Doctor d) {
        Session s = this.factory.getObject().getCurrentSession();
        Doctor doctor = this.getDoctorById(d.getId());
        if (doctor == null) {
            throw new ResourceNotFoundException("Không tìm thấy bác sĩ!");
        } else {
            if (doctor.getLicenseNumber() == null || doctor.getLicenseNumber().isEmpty()) {
                throw new ResourceNotFoundException("Bác sĩ chưa cung cấp mã!");
            }
            doctor.setIsVerified(false);
            return true;
        }
    }

}

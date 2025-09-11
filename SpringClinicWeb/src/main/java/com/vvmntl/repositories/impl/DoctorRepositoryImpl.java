/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.repositories.impl;

import com.vvmntl.pojo.Doctor;
import com.vvmntl.pojo.DoctorSpecialize;
import com.vvmntl.pojo.Specialize;
import com.vvmntl.repositories.DoctorRepository;
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
public class DoctorRepositoryImpl implements DoctorRepository{
    private static final int PAGE_SIZE = 10;
    @Autowired
    private LocalSessionFactoryBean factory;
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
        r.fetch("doctorSpecializeSet", JoinType.LEFT);

        query.select(r).distinct(true);
        
        if (params != null){
            List<Predicate> predicates = new ArrayList<>();
            
            String kw = params.get("kw");
            if(kw != null && !kw.isEmpty()){
                predicates.add(b.like(r.get("user").get("firstName"), String.format("%%%s%%", kw)));
            }
            query.where(predicates);
        }
        Query q = s.createQuery(query);
        
        if (params != null && !params.isEmpty()) {
            String p = params.getOrDefault("page", "1");
            int page = Integer.parseInt(p);
            int start = (page-1) * PAGE_SIZE;
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
        Join<Doctor, DoctorSpecialize> dsJoin = r.join("doctorSpecializeSet" );
        Join<DoctorSpecialize, Specialize> sJoin = dsJoin.join("specializeId");
        
        query.select(r).where(b.equal(sJoin.get("id"), id));
        
        Query q = s.createQuery(query);
        return q.getResultList();
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.repositories.impl;

import com.vvmntl.pojo.Patient;
import com.vvmntl.repositories.PatientRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author locnguyen
 */
@Repository
@Transactional
public class PatientRepositoryImpl implements PatientRepository {
    @Autowired
    private LocalSessionFactoryBean factory;
    
    @Override
    public Patient getPatientById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.find(Patient.class, id);
    }

    @Override
    public void add(Patient p) {
        Session s = this.factory.getObject().getCurrentSession();
        s.persist(p);
    }
}

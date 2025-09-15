/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.repositories.impl;

import com.vvmntl.pojo.Doctor;
import com.vvmntl.pojo.DoctorSpecialize;
import com.vvmntl.pojo.Specialize;
import com.vvmntl.repositories.DoctorSpecializeRepository;
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
public class DoctorSpecializeRepositoryImpl implements DoctorSpecializeRepository{
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public DoctorSpecialize add(DoctorSpecialize doctorSpecialize) {
        Session s = this.factory.getObject().getCurrentSession();
        s.persist(doctorSpecialize);
        return doctorSpecialize;
    }
    
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.repositories.impl;

import com.vvmntl.exception.ResourceNotFoundException;
import com.vvmntl.pojo.Medicalrecord;
import com.vvmntl.repositories.MedicalRecordRepository;
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
public class MedicalRecordRepositoryImpl implements MedicalRecordRepository{

    @Autowired
    private LocalSessionFactoryBean factory;
    
    @Override
    public Medicalrecord getMedicalRecordById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Medicalrecord mr = s.find(Medicalrecord.class, id);
        if (mr == null) {
            throw new ResourceNotFoundException("Không tìm thấy hồ sơ bệnh án!");
        }
        return mr;
    }

    @Override
    public Medicalrecord add(Medicalrecord medicalRecord) {
        Session s = this.factory.getObject().getCurrentSession();
        s.persist(medicalRecord);
        return medicalRecord;
    }

    @Override
    public Medicalrecord update(Medicalrecord medicalRecord) {
        Session s = this.factory.getObject().getCurrentSession();
        Medicalrecord mr = this.getMedicalRecordById(medicalRecord.getId());
        return s.merge(medicalRecord);
    }
    
}

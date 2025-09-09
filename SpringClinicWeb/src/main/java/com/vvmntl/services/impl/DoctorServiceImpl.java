/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.services.impl;

import com.vvmntl.pojo.Doctor;
import com.vvmntl.repositories.DoctorRepository;
import com.vvmntl.services.DoctorService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author BRAVO15
 */
@Service
public class DoctorServiceImpl implements DoctorService{
    @Autowired
    private DoctorRepository doctorRepo;
    
    @Override
    public List<Doctor> list() {
        return this.doctorRepo.list();
    }

    @Override
    public List<Doctor> getDoctor(Map<String, String> params) {
        return this.doctorRepo.getDoctor(params);
    }
    
}

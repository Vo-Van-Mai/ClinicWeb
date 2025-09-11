/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.services.impl;

import com.vvmntl.exception.ResourceNotFoundException;
import com.vvmntl.pojo.Doctor;
import com.vvmntl.pojo.Specialize;
import com.vvmntl.repositories.DoctorRepository;
import com.vvmntl.repositories.SpecializeRepository;
import com.vvmntl.services.DoctorService;
import com.vvmntl.services.SpecializeService;
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
    
    @Autowired
    private SpecializeService speciService;
    
    @Override
    public List<Doctor> list() {
        return this.doctorRepo.list();
    }

    @Override
    public List<Doctor> getDoctor(Map<String, String> params) {
        return this.doctorRepo.getDoctor(params);
    }

    @Override
    public List<Doctor> getDoctorBySpecializeId(int id) {
        this.speciService.getSpecializeById(id);
        return this.doctorRepo.getDoctorBySpecializeId(id);
    }
    
    
}

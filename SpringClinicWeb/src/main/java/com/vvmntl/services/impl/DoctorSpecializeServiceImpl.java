/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.services.impl;

import com.vvmntl.exception.ResourceNotFoundException;
import com.vvmntl.pojo.Doctor;
import com.vvmntl.pojo.DoctorSpecialize;
import com.vvmntl.pojo.Specialize;
import com.vvmntl.repositories.DoctorSpecializeRepository;
import com.vvmntl.services.DoctorService;
import com.vvmntl.services.DoctorSpecializeService;
import com.vvmntl.services.SpecializeService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author BRAVO15
 */
@Service
public class DoctorSpecializeServiceImpl implements DoctorSpecializeService{
    @Autowired
    private DoctorSpecializeRepository doctorSpeciRepo;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private SpecializeService speciService;
    
    @Override
    public DoctorSpecialize add(Doctor doctor, Specialize specialize, DoctorSpecialize docSpeci) {
        
        Doctor d = this.doctorService.getDoctorById(doctor.getUser().getId());
        if (d == null){
            throw new ResourceNotFoundException("Không tim thấy bác sĩ!");
        }
        Specialize s = this.speciService.getSpecializeById(specialize.getId());
        if(s==null){
             throw new ResourceNotFoundException("Không tim thấy chuyên khoa!");
        }
        docSpeci.setDoctorId(doctor);
        docSpeci.setSpecializeId(specialize);
        docSpeci.setJoinDate(LocalDateTime.now());
        try {
            return this.doctorSpeciRepo.add(docSpeci);
        } catch (Exception e) {
            System.out.printf("Xãy ra lỗi %s", e.getMessage());
            return null;
        }
    }

    @Override
    public List<Specialize> getSpecializesByDoctorId(int doctorId) {
        return this.doctorSpeciRepo.getSpecializesByDoctorId(doctorId);
    }
    
}

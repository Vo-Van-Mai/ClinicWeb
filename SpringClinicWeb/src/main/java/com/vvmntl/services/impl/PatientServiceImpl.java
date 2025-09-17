/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.services.impl;

import com.vvmntl.exception.ResourceNotFoundException;
import com.vvmntl.pojo.Patient;
import com.vvmntl.pojo.User;
import com.vvmntl.repositories.PatientRepository;
import com.vvmntl.services.PatientService;
import com.vvmntl.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author locnguyen
 */
@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepo;
    @Autowired
    private UserService userService;

    @Override
    public Patient getPatientById(int id) {
        Patient d = this.patientRepo.getPatientById(id);
        if (d != null) {
            return d;
        } else {
            throw new ResourceNotFoundException(String.format("Khong tim thay benh nhan voi ma %d !", id));
        }
    }

    @Override
    public void add(int userId, Patient p) {
        User u = this.userService.getUserById(userId);
        if (u == null) {
            throw new ResourceNotFoundException("không tìm thấy người dùng!");
        }

        this.patientRepo.add(p);
    }

}

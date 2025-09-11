/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.services.impl;

import com.vvmntl.exception.ResourceNotFoundException;
import com.vvmntl.pojo.Appointment;
import com.vvmntl.repositories.AppointmentRepository;
import com.vvmntl.services.AppointmentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author BRAVO15
 */
@Service
public class AppointmentServiceImpl implements AppointmentService{

    @Autowired()
    private AppointmentRepository AppRepo;
    
    @Override
    public List<Appointment> list() {
        return this.AppRepo.list();
    }

    @Override
    public Appointment getAppointmentById(int id) {
        return this.AppRepo.getAppointmentById(id);
    }

    @Override
    public boolean deleteAppointment(int id) {
        return false;
    }

    @Override
    public List<Appointment> getAppointmentByServiceId(int id) {
       return this.AppRepo.getAppointmentByServiceId(id);
    }
    
    
}

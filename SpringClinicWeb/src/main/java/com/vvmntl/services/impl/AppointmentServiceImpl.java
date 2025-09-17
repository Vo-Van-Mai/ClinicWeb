/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.services.impl;

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

    @Autowired
    private AppointmentRepository appRepo;
    
    @Override
    public List<Appointment> list() {
        return this.appRepo.list();
    }

    @Override
    public Appointment getAppointmentById(int id) {
        return this.appRepo.getAppointmentById(id);
    }

    @Override
    public boolean deleteAppointment(int id) {
        return this.appRepo.deleteAppointment(id);
    }

    @Override
    public List<Appointment> getAppointmentByServiceId(int id) {
       return this.appRepo.getAppointmentByServiceId(id);
    }

    @Override
    public Appointment bookAppointment(int patientId, int serviceId, int slotId) {
        return this.appRepo.bookAppointment(patientId, serviceId, slotId);
    }

    @Override
    public Appointment addAppointment(Appointment appointment) {
        return this.appRepo.addAppointment(appointment);
    }
    
}

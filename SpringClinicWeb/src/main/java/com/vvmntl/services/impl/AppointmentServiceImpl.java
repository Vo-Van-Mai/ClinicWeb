/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.services.impl;

import com.vvmntl.pojo.Appointment;
import com.vvmntl.pojo.Payment;
import com.vvmntl.pojo.PaymentMethod;
import com.vvmntl.repositories.AppointmentRepository;
import com.vvmntl.services.AppointmentService;
import com.vvmntl.services.PaymentService;
import java.util.List;
import java.util.Map;
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
    
    @Autowired
    private PaymentService paymentService;
    
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


    @Override
    public List<Appointment> loadAppointments(Map<String, String> params) {
        return this.appRepo.loadAppointmets(params);
    }

    @Override
    public String bookAppointmentAndCreatePayment(int patientId, int serviceId, int slotId, PaymentMethod paymentMethod) {
        Payment payment = this.appRepo.createAppointmentAndPayment(patientId, serviceId, slotId, paymentMethod);
        return paymentService.generatePaymentUrl(payment, paymentMethod);
    }
    
}

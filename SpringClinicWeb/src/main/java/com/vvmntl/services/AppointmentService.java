/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.vvmntl.services;

import com.vvmntl.pojo.Appointment;
import com.vvmntl.pojo.PaymentMethod;
import java.util.List;
import java.util.Map;

/**
 *
 * @author BRAVO15
 */
public interface AppointmentService {
    List<Appointment> list();
    Appointment getAppointmentById(int id);
    boolean deleteAppointment(int id);
    List<Appointment> getAppointmentByServiceId(int id);
    Appointment bookAppointment(int patientId, int serviceId, int slotId);
    Appointment addAppointment(Appointment appointment);
    List<Appointment> loadAppointments(Map <String, String> params);
    String bookAppointmentAndCreatePayment(int patientId, int serviceId, int slotId, PaymentMethod paymentMethod);
}

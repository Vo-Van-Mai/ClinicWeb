/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.vvmntl.repositories;

import com.vvmntl.pojo.Appointment;
import com.vvmntl.pojo.Payment;
import com.vvmntl.pojo.PaymentMethod;
import java.util.List;
import java.util.Map;

/**
 *
 * @author BRAVO15
 */
public interface AppointmentRepository {
    List<Appointment> list();
    Appointment getAppointmentById(int id);
    boolean deleteAppointment(int id);
    List<Appointment> getAppointmentByServiceId(int id);
//    Appointment bookAppointment(int patientId, int serviceId, int slotId);
    Appointment addAppointment(Appointment appointment);
//    Payment createPaymentForAppointment(Appointment appointment);
    Appointment updateAppointment(Appointment appointment);
    List<Appointment> loadAppointmets(Map<String, String> params);
//    Payment createAppointmentAndPayment(int patientId, int serviceId, int slotId, PaymentMethod paymentMethod);
}

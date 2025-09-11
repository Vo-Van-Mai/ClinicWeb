/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.vvmntl.services;

import com.vvmntl.pojo.Appointment;
import java.util.List;

/**
 *
 * @author BRAVO15
 */
public interface AppointmentService {
    List<Appointment> list();
    Appointment getAppointmentById(int id);
    boolean deleteAppointment(int id);
    List<Appointment> getAppointmentByServiceId(int id);
}

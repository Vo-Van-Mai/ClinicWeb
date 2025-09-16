/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.vvmntl.services;

import com.vvmntl.pojo.Appointmentslot;
import com.vvmntl.pojo.Doctor;
import com.vvmntl.pojo.Workschedule;
import java.util.List;
import java.util.Map;

/**
 *
 * @author locnguyen
 */
public interface AppointmentSlotService {

    Appointmentslot getSlotById(int id);

    Appointmentslot updateSlot(Appointmentslot slot);

    List<Appointmentslot> getAvailableSlots(Map<String, String> params);
    List<Appointmentslot> add(Workschedule ws);
    
    Appointmentslot getSlotByIdForUpdate(int id);
    List<Appointmentslot> getListAppointmentSlotByDoctorId(Doctor doctorId);
    List<Appointmentslot> getAppointmentSlots(Map<String, String> params);
}

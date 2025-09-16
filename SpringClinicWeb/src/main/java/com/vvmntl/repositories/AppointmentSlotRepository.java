/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.vvmntl.repositories;

import com.vvmntl.pojo.Appointmentslot;
import java.util.List;
import java.util.Map;

/**
 *
 * @author locnguyen
 */
public interface AppointmentSlotRepository {
    Appointmentslot getSlotById(int id);
    Appointmentslot updateSlot(Appointmentslot slot);
    List<Appointmentslot> getAvailableSlots(Map<String, String> params);
    Appointmentslot getSlotByIdForUpdate(int id);
}

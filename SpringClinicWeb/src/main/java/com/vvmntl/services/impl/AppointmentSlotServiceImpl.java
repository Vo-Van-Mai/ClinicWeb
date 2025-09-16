/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.services.impl;

import com.vvmntl.pojo.Appointmentslot;
import com.vvmntl.repositories.AppointmentSlotRepository;
import com.vvmntl.services.AppointmentSlotService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author locnguyen
 */
@Service
public class AppointmentSlotServiceImpl implements AppointmentSlotService {
    
    @Autowired
    private AppointmentSlotRepository slotRepo;

    @Override
    public Appointmentslot getSlotById(int id) {
        return this.slotRepo.getSlotById(id);
    }

    @Override
    public Appointmentslot updateSlot(Appointmentslot slot) {
        return this.slotRepo.updateSlot(slot);
    }

    @Override
    public List<Appointmentslot> getAvailableSlots(Map<String, String> params) {
        return this.slotRepo.getAvailableSlots(params);
    }

    @Override
    public Appointmentslot getSlotByIdForUpdate(int id) {
        return this.slotRepo.getSlotByIdForUpdate(id);
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.services.impl;

import com.vvmntl.pojo.Appointmentslot;
import com.vvmntl.pojo.Doctor;
import com.vvmntl.pojo.Workschedule;
import com.vvmntl.repositories.AppointmentSlotRepository;
import com.vvmntl.services.AppointmentSlotService;
import com.vvmntl.services.DoctorService;
import java.time.LocalTime;
import java.util.ArrayList;
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
    @Autowired
    private DoctorService doctorService;

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
    public List<Appointmentslot> add(Workschedule ws) {
        List<Appointmentslot> slots = new ArrayList<>();

        LocalTime start = ws.getStartTime();
        LocalTime end = ws.getEndTime();
        int slotMinutes = 30;

        while (start.plusMinutes(slotMinutes).compareTo(end) <= 0) {
            LocalTime slotEnd = start.plusMinutes(slotMinutes);

            Appointmentslot slot = new Appointmentslot();
            slot.setScheduleId(ws);
            slot.setStartTime(start);
            slot.setEndTime(slotEnd);
            slot.setIsBooked(false);

            slots.add(slot);
            start = slotEnd;
        }

        return this.slotRepo.add(slots);
    }
    
    
    public Appointmentslot getSlotByIdForUpdate(int id) {
        return this.slotRepo.getSlotByIdForUpdate(id);
    }

//    @Override
//    public List<Appointmentslot> getSlotsByScheduleId(int scheduleId) {
//        return this.slotRepo.getSlotsByScheduleId(scheduleId);
//    }
    @Override
    public List<Appointmentslot> getListAppointmentSlotByDoctorId(Doctor doctorId) {
        return this.slotRepo.getListAppointmentSlotByDoctorId(doctorId);
    }

    @Override
    public List<Appointmentslot> getAppointmentSlots(Map<String, String> params) {
        return this.slotRepo.getAppointmentSlots(params);
    }
    
}

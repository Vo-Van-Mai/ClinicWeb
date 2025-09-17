/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.services.impl;

import com.vvmntl.exception.ResourceNotFoundException;
import com.vvmntl.pojo.Appointment;
import com.vvmntl.pojo.Doctor;
import com.vvmntl.pojo.Workschedule;
import com.vvmntl.repositories.WorkScheduleRepository;
import com.vvmntl.services.AppointmentService;
import com.vvmntl.services.WorkScheduleService;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author BRAVO15
 */
@Service
public class WorkScheduleServiceImpl implements WorkScheduleService{
    @Autowired
    private WorkScheduleRepository wordScheduleRepo;
    @Autowired
    private AppointmentService appointmentService;

    @Override
    public List<Workschedule> list(Map<String, String> params) {
        return this.wordScheduleRepo.list(params);
    }

    @Override
    public Workschedule getWorksScheduleById(int id) {
        try {
            Workschedule ws = this.wordScheduleRepo.getWorksScheduleById(id);
            return ws;
        } catch (Exception e) {
            throw new ResourceNotFoundException("Không tìm thấy lịch làm việc!");
        }
    }

    @Override
    public List<Workschedule> getListWorkScheduleByDoctorId(int id) {
        return this.wordScheduleRepo.getListWorkScheduleByDoctorId(id);
    }

    @Override
    public Workschedule add(Workschedule ws) {

        if (ws.getStartTime().isAfter(ws.getEndTime()) || ws.getStartTime().equals(ws.getEndTime())) {
            throw new ResourceNotFoundException("Thời gian bắt đầu phải trước thời gian kết thúc");
        }
        boolean check = this.checkSchedule(ws.getDoctorId().getId(), ws.getDateWork(), ws.getStartTime(), ws.getEndTime());
        if (check == false)
            return this.wordScheduleRepo.add(ws);
        else {
            throw new RuntimeException("Lịch bị trùng!");
        }
    }

    @Override
    public boolean checkSchedule(int doctorId, LocalDate dateWork, LocalTime startTime, LocalTime endTime) {
        return this.wordScheduleRepo.checkSchedule(doctorId, dateWork, startTime, endTime);
    }

    @Override
    public boolean deleteWorkSchedule(int id) {
        try {
            Workschedule ws = this.getWorksScheduleById(id);
            Map<String, String> params = new HashMap<>();
            params.put("scheduleId", ws.getId().toString());
            List<Appointment> applist = this.appointmentService.loadAppointments(params);
            if(applist.isEmpty()){
                return this.wordScheduleRepo.delete(id);
            }
            else{
                throw new RuntimeException("Không thể xóa do còn lịch hẹn!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Xãy ra lỗi khi xóa lịch làm: " + e.getMessage());
        }
    }
    
}

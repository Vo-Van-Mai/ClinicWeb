/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.services.impl;

import com.vvmntl.exception.ResourceNotFoundException;
import com.vvmntl.pojo.Workschedule;
import com.vvmntl.repositories.WorkScheduleRepository;
import com.vvmntl.services.WorkScheduleService;
import java.time.LocalDate;
import java.time.LocalTime;
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

    @Override
    public List<Workschedule> list(Map<String, String> params) {
        return this.wordScheduleRepo.list(params);
    }

    @Override
    public Workschedule getWorksScheduleById(int id) {
        return this.getWorksScheduleById(id);
    }

    @Override
    public List<Workschedule> getListWorkScheduleByDoctorId(int id) {
        return this.getListWorkScheduleByDoctorId(id);
    }

    @Override
    public Workschedule add(Workschedule ws) {
//        if (ws.getDateWork() == null) {
//            throw new ResourceNotFoundException("Ngày làm việc không được để trống");
//        }
//        if (ws.getStartTime() == null) {
//            throw new ResourceNotFoundException("Thời gian bắt đầu không được để trống");
//        }
//        if (ws.getEndTime() == null) {
//            throw new ResourceNotFoundException("Thời gian kết thúc không được để trống");
//        }
//        if (ws.getDoctorId() == null) {
//            throw new ResourceNotFoundException("Bác sĩ không được để trống");
//        }

        if (ws.getStartTime().isAfter(ws.getEndTime()) || ws.getStartTime().equals(ws.getEndTime())) {
            throw new ResourceNotFoundException("Thời gian bắt đầu phải trước thời gian kết thúc");
        }
        
        return this.wordScheduleRepo.add(ws);
    }

    @Override
    public boolean checkSchedule(int doctorId, LocalDate dateWork, LocalTime startTime, LocalTime endTime) {
        return this.wordScheduleRepo.checkSchedule(doctorId, dateWork, startTime, endTime);
    }
    
}

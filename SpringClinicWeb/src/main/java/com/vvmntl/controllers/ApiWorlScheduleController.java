/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.controllers;

import com.vvmntl.pojo.Appointmentslot;
import com.vvmntl.pojo.Doctor;
import com.vvmntl.pojo.User;
import com.vvmntl.pojo.Workschedule;
import com.vvmntl.services.AppointmentService;
import com.vvmntl.services.AppointmentSlotService;
import com.vvmntl.services.DoctorService;
import com.vvmntl.services.UserService;
import com.vvmntl.services.WorkScheduleService;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author BRAVO15
 */
@RestController
@CrossOrigin
@RequestMapping("/api")
public class ApiWorlScheduleController {
    @Autowired
    private WorkScheduleService workScheduleService;
    
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private UserService userDetailService;
    @Autowired
    private AppointmentSlotService appointmentSlotService;
    
    @GetMapping("/workschedules/{doctorId}")
    public ResponseEntity<?> list(@PathVariable(value = "doctorId") int id){
        try {
            List<Workschedule> ws = this.workScheduleService.getListWorkScheduleByDoctorId(id);
            return ResponseEntity.ok(ws);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy lịch ");
        }
    }
    
    @PostMapping("/secure/workschedules")
    public ResponseEntity<?> addWordSchedule(@Valid @RequestBody Workschedule ws, BindingResult result, Principal principal){
        
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError fieldError : result.getFieldErrors()) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        if (ws.getStartTime().isAfter(ws.getEndTime()) || ws.getStartTime().equals(ws.getEndTime())) {
            Map<String, String> errors = new HashMap<>();
            errors.put("timeError", "Thời gian bắt đầu phải trước thời gian kết thúc");
            return ResponseEntity.badRequest().body(errors);
        }
        User user = this.userDetailService.getUserByUsername(principal.getName());
        
        
        Doctor doctor = this.doctorService.getDoctorById(user.getId());
            if(doctor==null){
                return ResponseEntity.badRequest().body("Khong tìm thấy bác sĩ");
            }
        try {
            ws.setDoctorId(doctor);
            Workschedule workScheduleSave = workScheduleService.add(ws);
            
            List<Appointmentslot> slots = this.appointmentSlotService.add(workScheduleSave);
            Map<String, Object> response = new HashMap<>();
            response.put("workSchedule", workScheduleSave);
            response.put("slots", slots);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.controllers;

import com.vvmntl.pojo.Appointment;
import com.vvmntl.pojo.Appointmentslot;
import com.vvmntl.services.AppointmentService;
import com.vvmntl.services.AppointmentSlotService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author locnguyen
 */
@RestController
@CrossOrigin
@RequestMapping("/api")
public class ApiAppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private AppointmentSlotService slotService;
    
    @GetMapping("/available-slots")
    public ResponseEntity<List<Appointmentslot>> getAvailableSlots(@RequestParam Map<String, String> params) {
        try {
            List<Appointmentslot> availableSlots = this.slotService.getAvailableSlots(params);
            return new ResponseEntity<>(availableSlots, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/appointments")
    public ResponseEntity<?> createAppointment(@RequestBody Map<String, Integer> payload) {
        try {
            Integer patientId = payload.get("patientId");
            Integer serviceId = payload.get("serviceId");
            Integer slotId = payload.get("slotId");

            if (patientId == null || serviceId == null || slotId == null) {
                return ResponseEntity.badRequest().body("Thiếu thông tin patientId, serviceId hoặc slotId.");
            }
            
            Appointment appointment = this.appointmentService.bookAppointment(patientId, serviceId, slotId);
            return new ResponseEntity<>(appointment, HttpStatus.CREATED);
            
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    
    @GetMapping("/appointments/{appointmentId}")
    public ResponseEntity<Appointment> getAppointmentDetails(@PathVariable(value = "appointmentId") int appointmentId) {
        try {
            Appointment appointment = this.appointmentService.getAppointmentById(appointmentId);
            return new ResponseEntity<>(appointment, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

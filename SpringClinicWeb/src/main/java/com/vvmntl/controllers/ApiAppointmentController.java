/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.controllers;

import com.vvmntl.pojo.Appointment;
import com.vvmntl.pojo.Appointmentslot;
import com.vvmntl.pojo.Patient;
import com.vvmntl.pojo.User;
import com.vvmntl.services.AppointmentService;
import com.vvmntl.services.AppointmentSlotService;
import com.vvmntl.services.PatientService;
import com.vvmntl.services.UserService;
import java.security.Principal;
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

    @Autowired
    private UserService userService;

    @Autowired
    private PatientService patientService;

    @GetMapping("/available-slots")
    public ResponseEntity<List<Appointmentslot>> getAvailableSlots(@RequestParam Map<String, String> params) {
        try {
            List<Appointmentslot> availableSlots = this.slotService.getAvailableSlots(params);
            return new ResponseEntity<>(availableSlots, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/secure/appointments/{slotId}")
    public ResponseEntity<?> bookAppointment(@PathVariable("slotId") int slotId, @RequestBody Map<String, Integer> payload, Principal principal) {
        try {
            Integer serviceId = payload.get("serviceId");
            if (serviceId == null) {
                return ResponseEntity.badRequest().body("Thiếu serviceId");
            }

            User user = userService.getUserByUsername(principal.getName());
            Patient patient = this.patientService.getPatientById(user.getId());

            Appointment appt = appointmentService.bookAppointment(patient.getId(), serviceId, slotId);
            return ResponseEntity.status(HttpStatus.CREATED).body(appt);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/secure/appointments/{appointmentId}")
    public ResponseEntity<Appointment> getAppointmentDetails(@PathVariable("appointmentId") int appointmentId) {
        try {
            Appointment appointment = this.appointmentService.getAppointmentById(appointmentId);
            return new ResponseEntity<>(appointment, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

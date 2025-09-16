/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.controllers;

import com.vvmntl.pojo.Appointmentslot;
import com.vvmntl.services.AppointmentSlotService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author BRAVO15
 */
@RestController
@CrossOrigin
@RequestMapping("/api")
public class ApiAppointmentSlotController {
    @Autowired
    private AppointmentSlotService appSlotService;
    
    @GetMapping("/appointmentslots")
    public ResponseEntity<List<Appointmentslot>> list(@RequestParam Map <String, String> params){
        return ResponseEntity.ok(this.appSlotService.getAppointmentSlots(params));
    }
}

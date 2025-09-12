/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.controllers;

import com.vvmntl.pojo.Doctor;
import com.vvmntl.pojo.User;
import com.vvmntl.services.DoctorService;
import com.vvmntl.validator.DoctorValidator;
import com.vvmntl.validator.UserValidator;
import com.vvmntl.validator.WebAppValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author BRAVO15
 */
@RequestMapping("/doctor")
@Controller
public class DoctorController {
    
    @Autowired
    private DoctorService doctorService;
    
    @Autowired
    private DoctorValidator doctorValidator;
    
    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.setValidator(doctorValidator);
    }
    
    @GetMapping("/add-doctor")
    public String addView(Model model){
        Doctor doctor = new Doctor();
        doctor.setUser(new User());
        model.addAttribute("doctor", doctor);
        return "add_doctor";
    }
    
    
    
    
}

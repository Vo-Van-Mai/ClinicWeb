/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.controllers;

import com.vvmntl.pojo.Doctor;
import com.vvmntl.pojo.User;
import com.vvmntl.services.DoctorService;
import com.vvmntl.services.UserService;
import com.vvmntl.validator.DoctorValidator;
import com.vvmntl.validator.UserValidator;
import com.vvmntl.validator.WebAppValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    private UserService userService;
    
    @Autowired
    private DoctorValidator doctorValidator;
    
    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.setValidator(doctorValidator);
    }
    
    @GetMapping("/add-doctor/{userId}")
    public String addView(Model model, @PathVariable(value="userId") int id){
        Doctor doctor = new Doctor();
        User u = this.userService.getUserById(id);
        System.out.println(" Get User info: " + u);        
        System.out.println(" Get User info: " + u.getId());

        doctor.setUser(u);
        
        System.out.println(" Get doctor info: " + doctor.getUser());        

        model.addAttribute("doctor", doctor);
        return "add_doctor";
    }
    
    
    @PostMapping("/add")
    public String add(@ModelAttribute(value="doctor") @Valid Doctor doctor, BindingResult result, Model model){
        if(result.hasErrors()){
            return "add_doctor";
        }
        try {
            User u = this.userService.getUserById(doctor.getUser().getId());
            System.out.println("Try Doctor submit: " + doctor);
            System.out.println(" Try User info: " + u.getUsername());
            this.doctorService.addDoctor(u, doctor);
            
        
            return "redirect:/";
        } catch (Exception e) {
            System.out.println("Doctor submit: " + doctor);
            System.out.println("User info: " + doctor.getUser());
            model.addAttribute("errorMessage", "Có lỗi xảy ra: " + e.getMessage());
            return "add_doctor";
        }
    }
    
    @GetMapping("/{doctorId}")
    public String update(Model model,@PathVariable(value="doctorId") int id ){
        model.addAttribute("doctor", this.doctorService.getDoctorById(id));
        return "add_doctor";
    }
    
}

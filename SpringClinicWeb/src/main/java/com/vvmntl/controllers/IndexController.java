/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.controllers;

import com.vvmntl.pojo.Doctor;
import com.vvmntl.services.CategoryService;
import com.vvmntl.services.DoctorService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author BRAVO15
 */
@Controller
public class IndexController {
    @Autowired
    private CategoryService cateService;
    
    @Autowired
    private DoctorService doctorService;
    
    @GetMapping("/")
    public String index(Model model, @RequestParam Map<String, String> params){
        List<Doctor> doctors = this.doctorService.getDoctor(params);
        model.addAttribute("doctors",doctors);
        int currentPage = Integer.parseInt(params.getOrDefault("page", "1"));
        model.addAttribute("currentPage", currentPage);
        long totalDoctors = this.doctorService.countDoctor(params);
        int totalPages = (int) Math.ceil((double) totalDoctors /10);
        model.addAttribute("totalPages", totalPages);
        return "index";
    }
    
}

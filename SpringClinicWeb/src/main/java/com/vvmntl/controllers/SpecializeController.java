/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.controllers;

import com.vvmntl.services.SpecializeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author BRAVO15
 */
@Controller
@RequestMapping("/specialize")
public class SpecializeController {
    @Autowired
    private SpecializeService SpeService;
    
    @GetMapping
    public String list(Model model){
        model.addAttribute("specialize", this.SpeService.list());
        return "specialize";
    }
}

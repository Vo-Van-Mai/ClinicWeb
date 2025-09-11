/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.controllers;

import com.vvmntl.pojo.Specialize;
import com.vvmntl.services.SpecializeService;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String list(Model model, @RequestParam Map<String, String> params){
        model.addAttribute("specialize", this.SpeService.list(params));
        return "specialize";
    }
    
    @GetMapping("/addview")
    public String addSpecializeView(Model model){
        model.addAttribute("specialize", new Specialize());
        return "addSpecialize";
    }
    
    @PostMapping("/add")
    public String addSpecialize(@ModelAttribute(value="specialize") @Valid Specialize s, BindingResult result){
        if(!result.hasErrors()){
            this.SpeService.addOrUpdateSpecialize(s);
            return "redirect:/specialize";
        }
        return "addSpecialize";
    }
            
}

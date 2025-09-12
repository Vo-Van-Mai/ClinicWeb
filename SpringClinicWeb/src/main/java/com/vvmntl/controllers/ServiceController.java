/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.controllers;

import com.vvmntl.pojo.Service;
import com.vvmntl.services.ServiceService;
import com.vvmntl.validator.ServiceValidator;
import com.vvmntl.validator.WebAppValidator;
import jakarta.validation.Valid;
import java.util.Map;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebAppRootListener;

/**
 *
 * @author BRAVO15
 */
@Controller
@RequestMapping("/service")
public class ServiceController {
    @Autowired
    private ServiceService serService;
    
    
    @Autowired
    private WebAppValidator serviceNameValidator;
    
    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.setValidator(serviceNameValidator);
    }
    
    
    @GetMapping()
    public String list(Model model, @RequestParam Map<String, String> params){
        model.addAttribute("services", this.serService.getService(params));
        return "service";
    }
    
    @GetMapping("/add-service")
    public String addView(Model model){
        model.addAttribute("service", new Service());
        return "add_service";
    }
    
    @PostMapping("/add")
    public String add(@ModelAttribute(value="service") @Valid Service s, BindingResult result){
        if(!result.hasErrors()){
            this.serService.addOrUpdateService(s);
            return "redirect:/service";
        }
        return "add_service";
    }
    
    @GetMapping("/{serviceId}")
    public String update(Model model, @PathVariable(value="serviceId") int id){
        model.addAttribute("service", this.serService.getServiceById(id));
        return "add_service";
    }
    
}

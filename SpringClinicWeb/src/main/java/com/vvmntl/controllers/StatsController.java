/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.controllers;

import com.vvmntl.services.StatsServices;
import java.util.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author locnguyen
 */
@Controller
public class StatsController {
    @Autowired
    private StatsServices statsService;
    
    @GetMapping("/stats")
    public String stats(Model model) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        
        model.addAttribute("medicineRevenues", this.statsService.getRevenueByMedicine());
        model.addAttribute("specializeRevenues", this.statsService.getRevenueBySpecialize());
        model.addAttribute("doctorAppointments", this.statsService.getAppointmentCountByDoctor());
        model.addAttribute("monthlyAppointments", this.statsService.getAppointmentStatsByTime("MONTH", currentYear));
        model.addAttribute("currentYear", currentYear);
        
        return "stats";
    }
}

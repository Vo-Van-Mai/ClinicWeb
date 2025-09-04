/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.controllers;

import com.vvmntl.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author BRAVO15
 */
@Controller
public class IndexController {
    @Autowired
    private CategoryService cateService;
    
    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("name", "VanMai");
        model.addAttribute("category", cateService.getCategoryById(2));
        return "index";
    }
}

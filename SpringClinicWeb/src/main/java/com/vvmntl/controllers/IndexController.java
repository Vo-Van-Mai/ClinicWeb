/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author BRAVO15
 */
@Controller
public class IndexController {
    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("name", "VanMai");
        return "index";
    }
}

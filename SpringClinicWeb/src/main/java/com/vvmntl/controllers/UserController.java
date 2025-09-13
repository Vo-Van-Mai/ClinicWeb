/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.controllers;

import com.vvmntl.pojo.User;
import com.vvmntl.services.UserService;
import com.vvmntl.validator.UserValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author BRAVO15
 */
@Controller

public class UserController {
    @GetMapping("/login")
    public String loginView(){
        return "login";
    }
    
    @Autowired
    private UserService userService;
    @Autowired
    private UserValidator userValidator;
    
    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.setValidator(userValidator);
    }
    
    
    @GetMapping("/user/add-user")
    public String addView(Model model){
        model.addAttribute("user", new User());
        return "add_user";
    }
    
    
    @PostMapping("/user/add")
    public String add(@ModelAttribute(value="user") @Valid User u, BindingResult result){
        if (!result.hasErrors()){
            System.out.println("com.vvmntl.controllers.UserController.add()");
            this.userService.addUser(u);
            return "redirect:/";
        }
            
        else{
            System.out.println("com.vvmntl.controllers.UserController.add()dddd");
            System.out.println("File object: " + u.getFile());
            System.out.println("File empty? " + u.getFile().isEmpty());
            System.out.println("File name: " + u.getFile().getOriginalFilename());
            result.getAllErrors().forEach(e -> System.out.println(e));
            return "add_user";
        }
    }
    
}

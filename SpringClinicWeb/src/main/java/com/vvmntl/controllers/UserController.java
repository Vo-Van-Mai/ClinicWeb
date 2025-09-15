/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.controllers;

import com.vvmntl.pojo.User;
import com.vvmntl.services.UserService;
import com.vvmntl.validator.UserValidator;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
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
    
    //add_doctor
    @PostMapping("/user/add")
    public String add(@ModelAttribute(value="user") @Valid User u, BindingResult result){
        if (!result.hasErrors()){
            Map<String, String> params = new HashMap<>();
            params.put("username", u.getUsername());
            params.put("firstName", u.getFirstName());
            params.put("lastName", u.getLastName());
            params.put("email", u.getEmail());
            params.put("phone", u.getPhone());
            params.put("dateOfBirth",u.getDateOfBirth() != null ? u.getDateOfBirth().toString() : null);
            params.put("gender",u.getGender() != null ? u.getGender().name() : null);
            params.put("role",u.getRole() != null ? u.getRole().name() : null);
            params.put("isAdmin", String.valueOf(u.getIsAdmin()));
            params.put("password", u.getPassword());
            User newUser = this.userService.addUser(params, u.getFile());
            return "redirect:/doctor/add-doctor/" + newUser.getId();
        }
            
        else{
            result.getAllErrors().forEach(e -> System.out.println(e));
            return "add_user";
        }
    }
    
}

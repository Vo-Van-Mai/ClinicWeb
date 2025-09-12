/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.validator;

import com.vvmntl.pojo.User;
import com.vvmntl.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author BRAVO15
 */
@Component
public class UserValidator implements Validator{
    @Autowired
    private UserService userService;
    
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User u = (User) target;
        if (u.getUsername()==null || u.getUsername().isBlank())
            errors.rejectValue("username", "user.username.nullErr");
        if (u.getUsername() != null && userService.getUserByUsername(u.getUsername())!=null)
            errors.rejectValue("username", "user.username.duplicate");
        
        if (u.getEmail() == null || u.getEmail().isBlank()) {
            errors.rejectValue("email", "user.email.nullErr");
        } else {
            // Regex kiểm tra định dạng email
            String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
            if (!u.getEmail().matches(emailRegex)) {
                errors.rejectValue("email", "user.email.invalidFormat");
            } else if (userService.getUserByEmail(u.getEmail()) != null) {
                errors.rejectValue("email", "user.email.duplicate");
            }
        }

        // Validate password
        if (u.getPassword() == null || u.getPassword().isBlank()) {
            errors.rejectValue("password", "user.password.nullErr");
        } else if (u.getPassword().length() < 6) {
            errors.rejectValue("password", "user.password.tooShort");
        }

        // Validate first name
        if (u.getFirstName() == null || u.getFirstName().isBlank()) {
            errors.rejectValue("firstName", "user.firstName.nullErr");
        }

        // Validate last name
        if (u.getLastName() == null || u.getLastName().isBlank()) {
            errors.rejectValue("lastName", "user.lastName.nullErr");
        }
    }
   
}

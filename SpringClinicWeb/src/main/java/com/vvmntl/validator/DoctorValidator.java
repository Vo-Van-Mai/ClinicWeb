/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.validator;

import com.vvmntl.pojo.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author BRAVO15
 */
@Component
public class DoctorValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Doctor.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Doctor doctor = (Doctor) target;


        if (doctor.getLicenseNumber() == null || doctor.getLicenseNumber().isBlank()) {
            errors.rejectValue("licenseNumber", "doctor.licenseNumber.nullErr");
        }
    }

}

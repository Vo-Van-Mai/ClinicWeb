/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.validator;

import com.vvmntl.pojo.Service;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author BRAVO15
 */
@Component
public class ServiceValidator implements Validator{ 
    
    @Override
    public boolean supports(Class<?> clazz) {
        return Service.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Service s = (Service) target;
        if (s.getName().isEmpty())
            errors.rejectValue("name", "service.name.nullErr");
        if (s.getPrice().compareTo(new BigDecimal(50000)) < 0)
            errors.rejectValue("price", "service.price.priceValidatorMsg");
    }
    
}

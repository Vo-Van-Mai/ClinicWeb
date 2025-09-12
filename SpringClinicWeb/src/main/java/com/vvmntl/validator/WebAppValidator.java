/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.validator;

import com.vvmntl.pojo.Service;
import jakarta.validation.ConstraintViolation;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author BRAVO15
 */
@Component
public class WebAppValidator implements Validator{
    
    private Set<Validator> springValidators = new HashSet<>();
   
    
    public boolean supports(Class<?> clazz) {
        return Service.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
//        Set<ConstraintViolation<Object>> beans =  beanValidator.validate(target);
//        for(ConstraintViolation<Object> obj: beans)
//            errors.rejectValue(obj.getPropertyPath().toString(), obj.getMessageTemplate(), obj.getMessage());
        for (Validator v: this.springValidators)
            v.validate(target, errors);
    }

    /**
     * @param springValidators the springValidators to set
     */
    public void setSpringValidators(Set<Validator> springValidators) {
        this.springValidators = springValidators;
    }

}

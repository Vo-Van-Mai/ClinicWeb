/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.formatters;

import com.vvmntl.pojo.Specialize;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author BRAVO15
 */
public class SpecializeFormatters implements Formatter<Specialize>{

    @Override
    public String print(Specialize s, Locale locale) {
        return String.valueOf(s.getId());
    }

    @Override
    public Specialize parse(String cateId, Locale locale) throws ParseException {
        Specialize s = new Specialize();
        s.setId(Integer.valueOf(cateId));
        return s;
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.services.impl;

import com.vvmntl.pojo.Specialize;
import com.vvmntl.repositories.SpecializeRepository;
import com.vvmntl.services.SpecializeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author BRAVO15
 */
@Service
public class SpecializeServiceImpl implements SpecializeService{
    
    @Autowired
    private SpecializeRepository SpeRepo;
    
    
    @Override
    public List<Specialize> list() {
        return this.SpeRepo.list();
    }

    @Override
    public Specialize getSpecializeById(int id) {
        return this.SpeRepo.getSpecializeById(id);
    }
}

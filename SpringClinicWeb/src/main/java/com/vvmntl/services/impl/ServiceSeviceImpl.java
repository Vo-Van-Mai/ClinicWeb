/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.services.impl;

import com.vvmntl.repositories.ServiceRepository;
import com.vvmntl.services.ServiceService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author BRAVO15
 */
@Service
public class ServiceSeviceImpl implements ServiceService{
    
    @Autowired
    private ServiceRepository ServiceRepo;

    @Override
    public List<com.vvmntl.pojo.Service> getService(Map<String, String> params) {
        return this.ServiceRepo.getService(params);
    }
}

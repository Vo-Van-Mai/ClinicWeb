/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.services.impl;

import com.vvmntl.exception.ResourceNotFoundException;
import com.vvmntl.pojo.Appointment;
import com.vvmntl.repositories.ServiceRepository;
import com.vvmntl.services.AppointmentService;
import com.vvmntl.services.ServiceService;
import com.vvmntl.validator.ServiceValidator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 *
 * @author BRAVO15
 */
@Service
public class ServiceSeviceImpl implements ServiceService{
    
    @Autowired
    private ServiceRepository ServiceRepo;
    
    @Autowired
    private AppointmentService appService; 
    
    

    @Override
    public List<com.vvmntl.pojo.Service> getService(Map<String, String> params) {
        return this.ServiceRepo.getService(params);
    }

    @Override
    public com.vvmntl.pojo.Service getServiceById(int id) {
        com.vvmntl.pojo.Service s = this.ServiceRepo.getServiceById(id);
        if (s != null){
            return s;
        }
        else {
            throw new ResourceNotFoundException("Không tìm thấy dịch vụ!");
        }
    }

    @Override
    public com.vvmntl.pojo.Service addOrUpdateService(com.vvmntl.pojo.Service s) {
        return this.ServiceRepo.addOrUpdateService(s);
    }

    @Override
    public boolean deleteService(int id) {
        List<Appointment> apps = this.appService.getAppointmentByServiceId(id);
        if (!apps.isEmpty()){
            throw new RuntimeException("không thể xóa dịch vụ do còn lịch hẹn!");
        }
        else
            return this.ServiceRepo.deleteService(id);
    }
}

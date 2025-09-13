/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.services.impl;

import com.vvmntl.repositories.StatsRepository;
import com.vvmntl.services.StatsServices;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author locnguyen
 */
@Service
public class StatsServicesImpl implements StatsServices {
    @Autowired
    private StatsRepository statsRepo;

    @Override
    public List<Object[]> getRevenueByMedicine() {
        return this.statsRepo.getRevenueByMedicine();
    }

    @Override
    public List<Object[]> getRevenueBySpecialize() {
        return this.statsRepo.getRevenueBySpecialize();
    }

    @Override
    public List<Object[]> getAppointmentCountByDoctor() {
        return this.statsRepo.getAppointmentCountByDoctor();
    }

    @Override
    public List<Object[]> getAppointmentStatsByTime(String time, int year) {
        return this.statsRepo.getAppointmentStatsByTime(time, year);
    }
    
    
}

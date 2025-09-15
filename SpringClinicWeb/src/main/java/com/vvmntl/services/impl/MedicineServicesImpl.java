/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.services.impl;

import com.vvmntl.pojo.Medicine;
import com.vvmntl.repositories.MedicineRepository;
import com.vvmntl.services.MedicineServices;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author locnguyen
 */
@Service
public class MedicineServicesImpl implements MedicineServices {
    @Autowired
    private MedicineRepository mediRepo;

    @Override
    public List<Medicine> getMedicines(Map<String, String> params) {
        return this.mediRepo.getMedicines(params);
    }

    @Override
    public void addOrUpdate(Medicine m) {
        this.mediRepo.addOrUpdate(m);
    }

    @Override
    public void deleteMedicine(int id) {
        this.mediRepo.deleteMedicine(id);
    }

    @Override
    public Medicine getMedicineById(int id) {
        return this.mediRepo.getMedicineById(id);
    }
    
}

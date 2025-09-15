/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.services.impl;

import com.vvmntl.pojo.CategoryMedicine;
import com.vvmntl.repositories.CategoryMedicineRepository;
import com.vvmntl.services.CategoryMedicineServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author locnguyen
 */
@Service
public class CategoryMedicineServicesImpl implements CategoryMedicineServices {
    @Autowired
    private CategoryMedicineRepository cateMediRepo;

    @Override
    public void addOrUpdate(CategoryMedicine cm) {
        this.cateMediRepo.addOrUpdate(cm);
    }

    @Override
    public CategoryMedicine getCateMediById(int id) {
        return this.cateMediRepo.getCateMediById(id);
    }
}

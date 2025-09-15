/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.vvmntl.repositories;

import com.vvmntl.pojo.CategoryMedicine;

/**
 *
 * @author locnguyen
 */
public interface CategoryMedicineRepository {
    void addOrUpdate(CategoryMedicine cm);
    public CategoryMedicine getCateMediById(int id);
}

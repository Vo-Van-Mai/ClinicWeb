/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.vvmntl.services;

import com.vvmntl.pojo.CategoryMedicine;

/**
 *
 * @author locnguyen
 */
public interface CategoryMedicineServices {
    void addOrUpdate(CategoryMedicine cm);
    CategoryMedicine getCateMediById(int id);
}

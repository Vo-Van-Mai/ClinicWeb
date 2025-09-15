/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.vvmntl.repositories;

import com.vvmntl.pojo.Medicine;
import java.util.List;
import java.util.Map;

/**
 *
 * @author locnguyen
 */
public interface MedicineRepository {
    List<Medicine> getMedicines(Map<String, String> params);
    void addOrUpdate(Medicine m);
    void deleteMedicine(int id);
    Medicine getMedicineById(int id);
}

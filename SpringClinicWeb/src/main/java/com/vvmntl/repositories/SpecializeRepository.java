package com.vvmntl.repositories;

import com.vvmntl.pojo.Doctor;
import com.vvmntl.pojo.Specialize;
import java.util.List;
import java.util.Map;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author BRAVO15
 */
public interface SpecializeRepository {
    List<Specialize> list(Map <String, String> params);
    List<Specialize> getAllSpecialize();
    Specialize getSpecializeById(int id);
    Specialize addOrUpdateSpecialize(Specialize s);
    boolean delSpecialize(int id);
    Specialize getSpecializerByName(String name);
}

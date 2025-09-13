/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.vvmntl.services;

import com.vvmntl.pojo.Doctor;
import com.vvmntl.pojo.User;
import java.util.List;
import java.util.Map;

/**
 *
 * @author BRAVO15
 */
public interface DoctorService {
    List<Doctor> list();
    List<Doctor> getDoctor(Map <String, String> params);
    List<Doctor> getDoctorBySpecializeId(int id);
    Doctor addDoctor(User user, Doctor d);
    Doctor updateDoctor(Doctor d);
    boolean deleteDoctor(int id);
    Doctor getDoctorById(int id);
    long countDoctor(Map<String, String> params);

}

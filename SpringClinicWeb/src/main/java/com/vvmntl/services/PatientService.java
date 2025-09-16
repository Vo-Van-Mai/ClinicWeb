/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.vvmntl.services;

import com.vvmntl.pojo.Patient;
import com.vvmntl.pojo.User;

/**
 *
 * @author locnguyen
 */
public interface PatientService {
    public Patient getPatientById(int id);
    void add(int userId, Patient p);
}

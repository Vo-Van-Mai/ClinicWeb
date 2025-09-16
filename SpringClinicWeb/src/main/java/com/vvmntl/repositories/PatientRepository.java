/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.vvmntl.repositories;

import com.vvmntl.pojo.Patient;

/**
 *
 * @author locnguyen
 */
public interface PatientRepository {
    Patient getPatientById(int id);
    Patient getPatientByUserId(int userId);
}

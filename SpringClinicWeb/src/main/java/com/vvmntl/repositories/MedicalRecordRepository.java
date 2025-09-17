/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.vvmntl.repositories;

import com.vvmntl.pojo.Medicalrecord;

/**
 *
 * @author BRAVO15
 */
public interface MedicalRecordRepository {
    Medicalrecord getMedicalRecordById(int id);
    Medicalrecord add(Medicalrecord medicalRecord);
    Medicalrecord update(Medicalrecord medicalRecord);
}

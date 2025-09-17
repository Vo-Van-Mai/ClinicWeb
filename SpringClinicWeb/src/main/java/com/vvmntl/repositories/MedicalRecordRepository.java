/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.vvmntl.repositories;

import com.vvmntl.pojo.Medicalrecord;
import java.util.List;

/**
 *
 * @author BRAVO15
 */
public interface MedicalRecordRepository {
    Medicalrecord getMedicalRecordById(int id);
    Medicalrecord add(Medicalrecord medicalRecord);
    Medicalrecord update(Medicalrecord medicalRecord);
    List<Medicalrecord> getMedicalRecordByUserId(int id);
    List<Medicalrecord> getMedidalRecordBYDoctorId(int id);
}

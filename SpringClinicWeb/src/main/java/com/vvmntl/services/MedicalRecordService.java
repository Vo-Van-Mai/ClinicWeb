/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.vvmntl.services;

import com.vvmntl.pojo.Medicalrecord;
import java.util.List;
import java.util.Map;

/**
 *
 * @author BRAVO15
 */
public interface MedicalRecordService {
    Medicalrecord getMedicalRecordById(int id);
    Medicalrecord add(Medicalrecord medicalRecord);
    Medicalrecord update(Medicalrecord medicalRecord);
    List<Medicalrecord> getMedicalRecordByUserId(int patientId);
    List<Medicalrecord> getMedidalRecordBYDoctorId(int doctorId);
    List<Medicalrecord> loadMedicalRecord(Map<String, String> params);
}

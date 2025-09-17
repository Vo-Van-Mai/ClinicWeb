/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.services.impl;

import com.vvmntl.exception.ResourceNotFoundException;
import com.vvmntl.pojo.Medicalrecord;
import com.vvmntl.repositories.MedicalRecordRepository;
import com.vvmntl.services.MedicalRecordService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author BRAVO15
 */
@Service
public class MedicalRecordServiceImpl implements MedicalRecordService{
    @Autowired
    private MedicalRecordRepository medicalReRecordRepo;
    

    @Override
    public Medicalrecord getMedicalRecordById(int id) {
        Medicalrecord mr = this.medicalReRecordRepo.getMedicalRecordById(id); 
        if (mr == null) {
            throw new ResourceNotFoundException("Không tìm thấy hồ sơ bệnh án!");
        }
        return mr;
    }

    @Override
    public Medicalrecord add(Medicalrecord medicalRecord) {
        
        if (medicalRecord == null) {
            throw new IllegalArgumentException("Medicalrecord không được trống");
        }

        if (medicalRecord.getAppointmentId() == null) {
            throw new IllegalArgumentException("Cần chọn lịch hẹn hợp lệ");
        }

        if (medicalRecord.getDiagnosis() == null || medicalRecord.getDiagnosis().trim().isEmpty()) {
            throw new IllegalArgumentException("Chẩn đoán không được để trống");
        }

        if (medicalRecord.getSymptoms() == null || medicalRecord.getSymptoms().trim().isEmpty()) {
            throw new IllegalArgumentException("Triệu chứng không được để trống");
        }

        if (medicalRecord.getTestResults() == null || medicalRecord.getTestResults().trim().isEmpty()) {
            throw new IllegalArgumentException("Kết quả xét nghiệm không được để trống");
        }

        medicalRecord.setCreatedDate(new Date());
        medicalRecord.setUpdatedDate(new Date());
        
        return this.medicalReRecordRepo.add(medicalRecord);

    }

    @Override
    public Medicalrecord update(Medicalrecord medicalRecord) {
        if(medicalRecord == null || medicalRecord.getId() == null) {
            throw new IllegalArgumentException("Vui lòng cung cấp mã hồ sơ!");
        }

        Medicalrecord existing = this.getMedicalRecordById(medicalRecord.getId());
        if (existing == null) {
            throw new ResourceNotFoundException("Không tìm thấy hồ sơ bệnh án để cập nhật");
        }

        if (medicalRecord.getDiagnosis() == null || medicalRecord.getDiagnosis().trim().isEmpty()) {
            throw new IllegalArgumentException("Chẩn đoán không được để trống");
        }
        if (medicalRecord.getSymptoms() == null || medicalRecord.getSymptoms().trim().isEmpty()) {
            throw new IllegalArgumentException("Triệu chứng không được để trống");
        }
        if (medicalRecord.getTestResults() == null || medicalRecord.getTestResults().trim().isEmpty()) {
            throw new IllegalArgumentException("Kết quả xét nghiệm không được để trống");
        }
        if (medicalRecord.getAppointmentId() == null) {
            throw new IllegalArgumentException("Cần chọn lịch hẹn hợp lệ");
        }

        existing.setDiagnosis(medicalRecord.getDiagnosis());
        existing.setSymptoms(medicalRecord.getSymptoms());
        existing.setTestResults(medicalRecord.getTestResults());
        existing.setAppointmentId(medicalRecord.getAppointmentId());
        existing.setUpdatedDate(new Date());
        return this.medicalReRecordRepo.update(medicalRecord);
    }   
    
}

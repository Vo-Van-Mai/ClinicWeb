/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.controllers;

import com.vvmntl.exception.ResourceNotFoundException;
import com.vvmntl.pojo.Appointment;
import com.vvmntl.pojo.Doctor;
import com.vvmntl.pojo.Medicalrecord;
import com.vvmntl.pojo.Patient;
import com.vvmntl.pojo.User;
import com.vvmntl.repositories.MedicalRecordRepository;
import com.vvmntl.services.AppointmentService;
import com.vvmntl.services.DoctorService;
import com.vvmntl.services.EmailService;
import com.vvmntl.services.MedicalRecordService;
import com.vvmntl.services.PatientService;
import com.vvmntl.services.UserService;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author BRAVO15
 */
@RestController
@CrossOrigin
@RequestMapping("/api/secure")
public class ApiMedicalRecordController {
    @Autowired
    private MedicalRecordService medicalRecordService;
    @Autowired
    private AppointmentService AppointmentService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserService userDetailService;
    @Autowired
    private DoctorService doctorService;
    
    @Autowired
    private PatientService patientService;
    
    @GetMapping("/medicalrecords/{medicalId}")
    public ResponseEntity<?> detail (@PathVariable(value = "medicalId") int id){
        try {
            Medicalrecord mr = this.medicalRecordService.getMedicalRecordById(id);
            return ResponseEntity.ok(mr);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping("/medicalrecords/{appointmentId}")
    public ResponseEntity<?> create (@PathVariable(value="appointmentId")int appointmentId, @RequestBody Medicalrecord medicalRecord){
        try {
            Appointment app = this.AppointmentService.getAppointmentById(appointmentId);
            if(app == null){
                return ResponseEntity.badRequest().body("Lịch hẹn không tồn tại!");
            }
            
            if(medicalRecord.getDiagnosis().isEmpty() || medicalRecord.getDiagnosis().isBlank()){
                return ResponseEntity.badRequest().body("Chưa cung cấp chuẩn đoán bệnh!");
            }
            if(medicalRecord.getSymptoms().isEmpty() || medicalRecord.getSymptoms().isBlank()){
                return ResponseEntity.badRequest().body("Chưa cung cấp triệu chứng của bệnh nhân!!");
            }
            if(medicalRecord.getTestResults().isEmpty() || medicalRecord.getTestResults().isBlank()){
                return ResponseEntity.badRequest().body("Chưa cung cấp kết quả xét nghiệm");
            }
            
            medicalRecord.setAppointmentId(app);
            Medicalrecord medicalRecordSave = this.medicalRecordService.add(medicalRecord);
            String subject = "HỒ SƠ KHÁM BỆNH";
            String content = "Xin chào " + medicalRecordSave.getAppointmentId().getPatientId().getUser().getLastName()+ " " 
                    + medicalRecordSave.getAppointmentId().getPatientId().getUser().getFirstName() + ",\n\n"
                    + "Hồ sơ khám bệnh của bạn đã được lưu trong hệ thống!\n"
                    + "Mã hồ sơ: " + medicalRecordSave.getId() + "\n"
                    + "Ngày khám: " + medicalRecordSave.getAppointmentId().getCreatedDate() + "\n\n"
                    + "Thông tin bệnh: " + "\n\n"
                    + "Chuẩn đoán: " + medicalRecordSave.getDiagnosis() + "\n"
                    + "Triệu chứng: " + medicalRecordSave.getSymptoms() + "\n"
                    + "Kết quả xét nghiệm: " + medicalRecordSave.getTestResults() + "\n"
                    + "Vui lòng đăng nhập hệ thống để xem chi tiết!" + "\n"
                    + "Trân trọng,\n"
                    + "Phòng khám";
            String emailTo = medicalRecordSave.getAppointmentId().getPatientId().getUser().getEmail();
            this.emailService.sendSimpleEmail(emailTo, subject, content);
            return new ResponseEntity<>(medicalRecordSave, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    
    @PatchMapping("/medicalrecords/{medicalId}")
    public ResponseEntity<?> update(@PathVariable(value="medicalId") int id){
        try {
            Medicalrecord medicalRecord = this.medicalRecordService.getMedicalRecordById(id);
            if (medicalRecord.getDiagnosis().isEmpty() || medicalRecord.getDiagnosis().isBlank()) {
                return ResponseEntity.badRequest().body("Chưa cung cấp chuẩn đoán bệnh!");
            }
            if (medicalRecord.getSymptoms().isEmpty() || medicalRecord.getSymptoms().isBlank()) {
                return ResponseEntity.badRequest().body("Chưa cung cấp triệu chứng của bệnh nhân!!");
            }
            if (medicalRecord.getTestResults().isEmpty() || medicalRecord.getTestResults().isBlank()) {
                return ResponseEntity.badRequest().body("Chưa cung cấp kết quả xét nghiệm");
            }
            return ResponseEntity.ok(this.medicalRecordService.update(medicalRecord));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/patients/medicalRecords")
    public ResponseEntity<?> getListByUser(Principal pricipal){
        try {
            String username = pricipal.getName();
            User user = this.userDetailService.getUserByUsername(username);
            List<Medicalrecord> mdrecord = this.medicalRecordService.getMedicalRecordByUserId(user.getId());
            return ResponseEntity.ok(mdrecord);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/doctors/medicalrecords")
    public ResponseEntity<?> getListMedicalRecordByDoctor(Principal principal){
        try {
            User user = this.userDetailService.getUserByUsername(principal.getName());
            return ResponseEntity.ok(this.medicalRecordService.getMedidalRecordBYDoctorId(user.getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Xãy ra lỗi: " + e.getMessage());
        }
    }
}




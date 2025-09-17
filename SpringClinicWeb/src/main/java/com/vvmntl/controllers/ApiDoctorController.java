/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.controllers;

import com.vvmntl.exception.ResourceNotFoundException;
import com.vvmntl.pojo.Doctor;
import com.vvmntl.pojo.DoctorSpecialize;
import com.vvmntl.pojo.Specialize;
import com.vvmntl.pojo.User;
import com.vvmntl.pojo.Workschedule;
import com.vvmntl.services.DoctorService;
import com.vvmntl.services.DoctorSpecializeService;
import com.vvmntl.services.SpecializeService;
import com.vvmntl.services.UserService;
import com.vvmntl.services.WorkScheduleService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author BRAVO15
 */
@RestController
@CrossOrigin
@RequestMapping("/api")
public class ApiDoctorController {
    @Autowired
    private DoctorService docService;
    @Autowired
    private UserService userService;
    @Autowired
    private SpecializeService specService;
    @Autowired
    private WorkScheduleService workScheduleService;
    
    @Autowired
    private DoctorSpecializeService docSpeciService;
        
    @GetMapping("/doctors")
    public ResponseEntity<List<Doctor>> list(@RequestParam Map <String, String> params){
        return new ResponseEntity<>(this.docService.getDoctor(params), HttpStatus.OK);
    }
    
    
    @GetMapping("/doctors/{doctorId}")
    public ResponseEntity<?> retrieve(@PathVariable(value = "doctorId") int id){
        try {
            Doctor doctor = this.docService.getDoctorById(id);

            List<Specialize> specializes = this.docSpeciService.getSpecializesByDoctorId(id);
            Map<String, Object> response = new HashMap<>();
            response.put("doctor", doctor);
            response.put("specializes", specializes);

            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy bác sĩ ");
        }
    }
    
    
    @PostMapping("/doctors/{userId}/add-profile")
    public ResponseEntity<?> create(@RequestBody Doctor doctor, @PathVariable(value="userId") int id){
        User user = this.userService.getUserById(id);
        if(user==null){
            return new ResponseEntity<>("Không tìm thấy người dùng", HttpStatus.BAD_REQUEST);
        }
        if(doctor.getExperience()==null || doctor.getYearOfExperience()==null || doctor.getLicenseNumber() == null){
            return new ResponseEntity<>("Thông tin chưa đủ!", HttpStatus.BAD_REQUEST);
        }
        try{
            Doctor doc = this.docService.addDoctor(user, doctor);
            return new ResponseEntity<>(doc, HttpStatus.CREATED);
            
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        
    }
    
    
    @PostMapping("/doctors/{doctorId}/specializes")
    public ResponseEntity<?> addSpecializes(@PathVariable(value = "doctorId") int doctorId, @RequestBody Map<String, List<Integer>> payload) {
        try {
            Doctor doctor = this.docService.getDoctorById(doctorId);
            if (doctor == null) {
                return new ResponseEntity<>("Không tìm thấy bác sĩ!", HttpStatus.NOT_FOUND);
            }       

            List<Integer> specializeIds = payload.get("specializeIds");
            if (specializeIds == null || specializeIds.isEmpty()) {
                return new ResponseEntity<>("Danh sách chuyên khoa rỗng!", HttpStatus.BAD_REQUEST);
            }

            List<DoctorSpecialize> results = new ArrayList<>();

            for (Integer specId : specializeIds) {
                Specialize specialize = this.specService.getSpecializeById(specId);
                if (specialize != null) {
                    DoctorSpecialize docSpeci = new DoctorSpecialize();
                    docSpeci.setDoctorId(doctor);
                    docSpeci.setSpecializeId(specialize);
                    results.add(this.docSpeciService.add(doctor, specialize, docSpeci));
                }
            }

            return new ResponseEntity<>(results, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>("Lỗi: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    @PostMapping("/admin/{doctorId}/verified")
    public ResponseEntity<?> verifiedDoctor(@PathVariable(value="doctorId") int id){
        try {
            Doctor d = this.docService.getDoctorById(id);
            this.docService.verifiedDoctor(d);
            return ResponseEntity.ok(d);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        
    }
    
    @PostMapping("/admin/{doctorId}/cancel")
    public ResponseEntity<?> cancelDoctor(@PathVariable(value="doctorId") int id){
        try {
            Doctor d = this.docService.getDoctorById(id);
            this.docService.cancelDoctor(d);
            return ResponseEntity.ok(d);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        
    }
    
}

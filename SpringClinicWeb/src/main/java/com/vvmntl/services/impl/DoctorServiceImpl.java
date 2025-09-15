/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.services.impl;

import com.vvmntl.exception.ResourceNotFoundException;
import com.vvmntl.pojo.Doctor;
import com.vvmntl.pojo.Role;
import com.vvmntl.pojo.Specialize;
import com.vvmntl.pojo.User;
import com.vvmntl.repositories.DoctorRepository;
import com.vvmntl.services.DoctorService;
import com.vvmntl.services.SpecializeService;
import com.vvmntl.services.UserService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author BRAVO15
 */
@Service
public class DoctorServiceImpl implements DoctorService{
    @Autowired
    private DoctorRepository doctorRepo;
    
    @Autowired
    private SpecializeService speciService;
    
    @Autowired
    private UserService userService;
    
    @Override
    public List<Doctor> list() {
        return this.doctorRepo.list();
    }

    @Override
    public List<Doctor> getDoctor(Map<String, String> params) {
        return this.doctorRepo.getDoctor(params);
    }

    @Override
    public List<Doctor> getDoctorBySpecializeId(int id) {
        this.speciService.getSpecializeById(id);
        return this.doctorRepo.getDoctorBySpecializeId(id);
    }
    
    @Override
    public Doctor getDoctorById(int id) {
        Doctor d = this.doctorRepo.getDoctorById(id);
        if(d!=null){
            return d;
        }
        else{
            throw new ResourceNotFoundException(String.format("Không tìm thấy bác sĩ với mã %d !", id ));
        }
    }

    @Override
    public boolean deleteDoctor(int id) {
        Doctor d = this.getDoctorById(id);
        if (d != null){
            return this.doctorRepo.deleteDoctor(id);
        }
        else
            return false;
    }

    @Override
    public Doctor addDoctor(User user, Doctor d) {
        if (user!=null){
            d.setUser(user);
            if(d.getLicenseNumber()==null || d.getLicenseNumber().isEmpty())
                throw new ResourceNotFoundException("Không tìm thấy mã bác sĩ!");
            return this.doctorRepo.addDoctor(user, d);
        } else{
            throw new ResourceNotFoundException("Không tìm thấy người dùng! Vui lòng kiểm tra lại hoặc đăng kí người dùng mới!");
        }
    }

    @Override
    public Doctor updateDoctor(Doctor d) {
        User user = this.userService.getUserById(d.getUser().getId());
        
        if (user == null){
            throw new ResourceNotFoundException("không tim thấy người dùng!");
        }
        
        if (!user.getRole().name().equals("DOCTOR")){
            throw new RuntimeException("Bạn không phải là bác sĩ!");
        }
        
        Doctor doctor = this.getDoctorById(user.getId());
        
        if (d.getExperience()!=null) {
            doctor.setExperience(d.getExperience());
        }
        if (d.getLicenseNumber()!= null){
            doctor.setLicenseNumber(d.getLicenseNumber());
        }
        if (d.getYearOfExperience()!=null){
            doctor.setYearOfExperience(d.getYearOfExperience());
        }
        
        if(d.getUser()!=null){
            if (d.getUser().getFirstName()!=null){
                doctor.getUser().setFirstName(d.getUser().getFirstName());
            }
            if (d.getUser().getLastName()!=null){
                doctor.getUser().setLastName(d.getUser().getLastName());
            }
            if (d.getUser().getUsername()!=null){
                doctor.getUser().setUsername(d.getUser().getUsername());
            }
            if (d.getUser().getPhone()!=null){
                doctor.getUser().setPhone(d.getUser().getPhone());
            }
            if (d.getUser().getEmail()!=null){
                doctor.getUser().setEmail(d.getUser().getEmail());
            }
            if (d.getUser().getDateOfBirth()!=null){
                doctor.getUser().setDateOfBirth(d.getUser().getDateOfBirth());
            }
            if (d.getUser().getAvatar()!=null){
                doctor.getUser().setAvatar(d.getUser().getAvatar());
            }
            if (d.getUser().getGender()!=null){
                doctor.getUser().setGender(d.getUser().getGender());
            }
        }
        return this.doctorRepo.updateDoctor(doctor);
    }

    @Override
    public long countDoctor(Map<String, String> params) {
        return this.doctorRepo.countDoctor(params);
    }
}
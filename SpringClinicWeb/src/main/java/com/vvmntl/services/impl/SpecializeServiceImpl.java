/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.services.impl;

import com.vvmntl.exception.ResourceNotFoundException;
import com.vvmntl.pojo.Doctor;
import com.vvmntl.pojo.Specialize;
import com.vvmntl.repositories.SpecializeRepository;
import com.vvmntl.services.DoctorService;
import com.vvmntl.services.SpecializeService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author BRAVO15
 */
@Service
public class SpecializeServiceImpl implements SpecializeService {

    @Autowired
    private SpecializeRepository SpeRepo;

    @Autowired
    private DoctorService doctorService;

    @Override
    public Specialize getSpecializeById(int id) {
        Specialize s = this.SpeRepo.getSpecializeById(id);
        if (s != null) {
            return s;
        }
        throw new ResourceNotFoundException("Không tìm thấy chuyên khoa với id: " + id);
    }

    @Override
    public Specialize addOrUpdateSpecialize(Specialize s) {
        if (s.getId() == null) {
            Specialize speci = this.getSpecializerByName(s.getName());
            if (speci != null) {
                throw new IllegalStateException("Tên khoa đã tồn tại!");
            }
        } else {
            Specialize existing = this.getSpecializerByName(s.getName());
            if (existing != null && !existing.getId().equals(s.getId())) {
                throw new IllegalStateException("Tên khoa đã tồn tại!");
            }
        }
        return this.SpeRepo.addOrUpdateSpecialize(s);
    }

    @Override
    public boolean delSpecialize(int id) {

        this.getSpecializeById(id);
        List<Doctor> doctors = this.doctorService.getDoctorBySpecializeId(id);

        if (!doctors.isEmpty()) {
            throw new IllegalStateException("Không thể xóa khoa do còn danh bác sĩ!");
        }
        return this.SpeRepo.delSpecialize(id);
    }

    @Override
    public List<Specialize> list(Map<String, String> params) {
        return this.SpeRepo.list(params);
    }

    @Override
    public Specialize getSpecializerByName(String name) {
        return this.SpeRepo.getSpecializerByName(name);
    }
}

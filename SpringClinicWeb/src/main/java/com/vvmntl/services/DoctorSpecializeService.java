/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.vvmntl.services;

import com.vvmntl.pojo.Doctor;
import com.vvmntl.pojo.DoctorSpecialize;
import com.vvmntl.pojo.Specialize;
import java.util.List;

/**
 *
 * @author BRAVO15
 */
public interface DoctorSpecializeService {
    DoctorSpecialize add(Doctor doctor, Specialize specialize, DoctorSpecialize docSpeci);
    List<Specialize> getSpecializesByDoctorId(int doctorId);
}

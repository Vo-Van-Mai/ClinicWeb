/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.vvmntl.repositories;

import com.vvmntl.pojo.Workschedule;
import java.util.List;
import java.util.Map;

/**
 *
 * @author BRAVO15
 */
public interface WorkScheduleRepository {
    List<Workschedule> list(Map<String, String> params);
    Workschedule getWorksScheduleById(int id);
}

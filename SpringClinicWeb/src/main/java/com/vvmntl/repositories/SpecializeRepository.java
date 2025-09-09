package com.vvmntl.repositories;

import com.vvmntl.pojo.Specialize;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author BRAVO15
 */
public interface SpecializeRepository {
    List<Specialize> list();
    Specialize getSpecializeById(int id);
}

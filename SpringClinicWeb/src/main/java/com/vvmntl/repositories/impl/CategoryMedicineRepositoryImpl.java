/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.repositories.impl;

import com.vvmntl.pojo.CategoryMedicine;
import com.vvmntl.repositories.CategoryMedicineRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author locnguyen
 */
@Repository
@Transactional
public class CategoryMedicineRepositoryImpl implements CategoryMedicineRepository {
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public void addOrUpdate(CategoryMedicine cm) {
        Session s = this.factory.getObject().getCurrentSession();
        if (cm.getId() != null) {
            s.merge(cm);
        } else {
            s.persist(cm);
        }
    }

    @Override
    public CategoryMedicine getCateMediById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.find(CategoryMedicine.class, id);
    }
}

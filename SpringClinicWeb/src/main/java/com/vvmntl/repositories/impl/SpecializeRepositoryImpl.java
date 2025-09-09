/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.repositories.impl;

import com.vvmntl.pojo.Specialize;
import com.vvmntl.repositories.SpecializeRepository;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author BRAVO15
 */
@Repository
@Transactional
public class SpecializeRepositoryImpl implements SpecializeRepository{
    @Autowired
    private LocalSessionFactoryBean factory;
    
    @Override
    public List<Specialize> list() {
        Session s = this.factory.getObject().getCurrentSession();
        
        Query query = s.createNamedQuery("Specialize.findAll", Specialize.class);
        return  query.getResultList();
    }

    @Override
    public Specialize getSpecializeById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        
        Query query = s.createNamedQuery("Specialize.findById", Specialize.class);
        return (Specialize) query.getSingleResult();
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.repositories.impl;

import com.vvmntl.pojo.Service;
import com.vvmntl.pojo.Specialize;
import com.vvmntl.repositories.ServiceRepository;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
public class ServiceRepositoryImpl implements ServiceRepository{
    @Autowired
    private LocalSessionFactoryBean factory;
    
    @Override
    public List<Service> getService(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Service> query = b.createQuery(Service.class);
        Root<Service> root = query.from(Service.class);
        query.select(root);
        
        if (params != null && !params.isEmpty()){
            List<Predicate> predicates = new ArrayList<>();
            String name = params.get("name");
            
            if (name != null && !name.isEmpty()){
                predicates.add(b.like(root.get("name"), String.format("%%%s%%", name)));
            }
            query.where(predicates);
        }
        Query q = s.createQuery(query);
        return q.getResultList();
    }

    @Override
    public Service getServiceById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Query query = s.createNamedQuery("Service.findById", Service.class);
        query.setParameter("id", id);
        try {
            return (Service) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Service addOrUpdateService(Service s) {
        Session session = this.factory.getObject().getCurrentSession();
        if(s.getId() == null){
            session.persist(s);
            return s;
        }
        else {
            session.merge(s);
            return session.merge(s);
        }
    }

    @Override
    public boolean deleteService(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        Service service = this.getServiceById(id);
        if (service != null){
            session.remove(service);
            return true;
        }
        return false;
    }
}

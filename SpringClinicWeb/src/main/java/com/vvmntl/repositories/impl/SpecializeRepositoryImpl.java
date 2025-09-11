/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.repositories.impl;

import com.vvmntl.pojo.Service;
import com.vvmntl.pojo.Specialize;
import com.vvmntl.repositories.SpecializeRepository;
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
public class SpecializeRepositoryImpl implements SpecializeRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Specialize> list(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Specialize> query = b.createQuery(Specialize.class);
        Root root = query.from(Specialize.class);
        query.select(root);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();
            
            String name = params.get("name");
            
            if (name != null && !name.isEmpty()) {
                predicates.add(b.like(root.get("name"), String.format("%%%s%%", name)));
            }
            query.where(predicates.toArray(new Predicate[0]));
        }
        Query q = s.createQuery(query);
        return q.getResultList();
    }

    @Override
    public Specialize getSpecializeById(int id) {
        Session s = this.factory.getObject().getCurrentSession();

        Query query = s.createNamedQuery("Specialize.findById", Specialize.class);
        query.setParameter("id", id);
        return (Specialize) query.getSingleResult();
    }

    @Override
    public Specialize addOrUpdateSpecialize(Specialize s) {
        Session session = this.factory.getObject().getCurrentSession();
        if (s.getId() == null) {
            session.persist(s);
        } else {
            session.merge(s);
        }
        return s;
    }

    @Override
    public boolean delSpecialize(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        Specialize s = this.getSpecializeById(id);
        if (s == null) {
            return false;
        }
        session.remove(s);
        return true;
    }

    @Override
    public Specialize getSpecializerByName(String name) {
        Session session = this.factory.getObject().getCurrentSession();
        Query q = session.createNamedQuery("Specialize.findByName", Specialize.class);
        q.setParameter("name", name);
        try {
            return (Specialize) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Specialize> getAllSpecialize() {
        Session session = this.factory.getObject().getCurrentSession();
        Query q = session.createNamedQuery("Specialize.findAll", Specialize.class);
        return q.getResultList();
    }

}

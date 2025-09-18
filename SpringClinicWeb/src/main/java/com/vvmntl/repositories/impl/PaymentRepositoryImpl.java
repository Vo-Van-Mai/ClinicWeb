/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.repositories.impl;

import com.vvmntl.pojo.Appointment;
import com.vvmntl.pojo.Payment;
import com.vvmntl.pojo.PaymentMethod;
import com.vvmntl.repositories.PaymentRepository;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.query.Query;
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
public class PaymentRepositoryImpl implements PaymentRepository {
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public String createPaymentUrl(Appointment appointment, PaymentMethod paymentMethod) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void handlePaymentCallback(Map<String, String> params) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Payment save(Payment p) {
        Session s = this.factory.getObject().getCurrentSession();
        if (p.getId() == null) {
            s.persist(p);
        } else {
            s.merge(p);
        }
        return p;
    }

    @Override
    public Payment findByTransactionId(String transactionId) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Payment> q = b.createQuery(Payment.class);
        Root<Payment> root = q.from(Payment.class);
        
        q.select(root).where(b.equal(root.get("transactionId"), transactionId));
        
        Query<Payment> query = s.createQuery(q);
        
        try {
            return query.getSingleResult();
        } catch (NoResultException ex) {
            System.err.println("Không tìm thấy payment với transactionId: " + transactionId);
            return null;
        }
    }
}

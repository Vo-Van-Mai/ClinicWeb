/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.repositories.impl;

import com.vvmntl.exception.ResourceNotFoundException;
import com.vvmntl.pojo.Appointmentslot;
import com.vvmntl.repositories.AppointmentSlotRepository;
import jakarta.persistence.LockModeType;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
 * @author locnguyen
 */
@Repository
@Transactional
public class AppointmentSlotRepositoryImpl implements AppointmentSlotRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public Appointmentslot getSlotById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createNamedQuery("Appointmentslot.findById", Appointmentslot.class);
        q.setParameter("id", id);
        try {
            return (Appointmentslot) q.getSingleResult();
        } catch (Exception e) {
            throw new ResourceNotFoundException("Khong tim lich dat!");
        }
    }

    @Override
    public Appointmentslot updateSlot(Appointmentslot slot) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.merge(slot);
    }

    @Override
    public List<Appointmentslot> getAvailableSlots(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Appointmentslot> query = b.createQuery(Appointmentslot.class);
        Root r = query.from(Appointmentslot.class);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(b.equal(r.get("isBooked"), false));

        String doctorId = params.get("doctorId");
        if (doctorId != null && !doctorId.isEmpty()) {
            predicates.add(b.equal(r.get("scheduleId").get("doctorId").get("id"), Integer.parseInt(doctorId)));
        }

        String dateStr = params.get("date");
        if (dateStr != null && !dateStr.isEmpty()) {
            try {
                LocalDate parsedDate = LocalDate.parse(dateStr);
                predicates.add(b.equal(r.get("scheduleId").get("dateWork"), parsedDate));
            } catch (DateTimeParseException e) {
                System.err.println("Invalid date format provided: " + dateStr);
                return new ArrayList<>();
            }
        }

        query.where(predicates.toArray(new Predicate[0]));
        query.orderBy(b.asc(r.get("startTime")));

        Query q = s.createQuery(query);
        return q.getResultList();
    }

    @Override
    public List<Appointmentslot> add(List<Appointmentslot> slots) {
        Session s = this.factory.getObject().getCurrentSession();
        List<Appointmentslot> saved = new ArrayList<>();
        for (Appointmentslot slot : slots) {
            s.persist(slot);
            saved.add(slot);
        }
        return saved;
    }
    
    public Appointmentslot getSlotByIdForUpdate(int id) {
        Session s = factory.getObject().getCurrentSession();
        return s.find(Appointmentslot.class, id, LockModeType.PESSIMISTIC_WRITE);
    }

//    @Override
//    public List<Appointmentslot> getSlotsByScheduleId(int scheduleId) {
//        Session s = this.factory.getObject().getCurrentSession();
//        CriteriaBuilder b = s.getCriteriaBuilder();
//        CriteriaQuery<Appointmentslot> query = b.createQuery(Appointmentslot.class);
//        Root r = query.from(Appointmentslot.class);
//        
//        query.select(r).where(b.equal(r.get("scheduleId"), scheduleId));
//        
//        query.orderBy(b.asc(r.get("startTime")));
//
//        Query<Appointmentslot> q = s.createQuery(query);
//        return q.getResultList();
//    }

}

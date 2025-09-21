/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.repositories.impl;

import com.vvmntl.exception.ResourceNotFoundException;
import com.vvmntl.pojo.Appointment;
import com.vvmntl.pojo.Appointmentslot;
import com.vvmntl.pojo.Patient;
import com.vvmntl.pojo.Payment;
import com.vvmntl.pojo.PaymentMethod;
import com.vvmntl.pojo.PaymentMethodType;
import com.vvmntl.pojo.PaymentStatus;
import com.vvmntl.pojo.PaymentType;
import com.vvmntl.pojo.Service;
import com.vvmntl.pojo.StatusEnum;
import com.vvmntl.repositories.AppointmentRepository;
import com.vvmntl.repositories.AppointmentSlotRepository;
import com.vvmntl.repositories.PatientRepository;
import com.vvmntl.repositories.ServiceRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
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
public class AppointmentRepositoryImpl implements AppointmentRepository {
    @Autowired
    private LocalSessionFactoryBean factory;
    
    @Override
    public List<Appointment> list() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createNamedQuery("Doctor.findAll", Appointment.class);
        return q.getResultList();
    }

    @Override
    public Appointment getAppointmentById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createNamedQuery("Appointment.findById", Appointment.class);
        q.setParameter("id", id);
        try {
            return (Appointment) q.getSingleResult();
        } catch (Exception e) {
            throw new ResourceNotFoundException("Không tìm thấy cuộc hẹn!");
        }
    }

    @Override
    public boolean deleteAppointment(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Appointment a = this.getAppointmentById(id);
        if (a != null) {
            Appointmentslot slot = a.getAppointmentSlot();
            if (slot != null) {
                slot.setIsBooked(false);
                s.merge(slot);
            }
            s.remove(a);
            return true;
        }
        return false;
    }

    @Override
    public List<Appointment> getAppointmentByServiceId(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Appointment> query = b.createQuery(Appointment.class);
        Root<Appointment> r = query.from(Appointment.class);
        query.select(r).where(b.equal(r.get("serviceId").get("id"), id));
        Query q = s.createQuery(query);

        return q.getResultList();
    }

    @Override
    public Appointment addAppointment(Appointment appointment) {
        Session s = this.factory.getObject().getCurrentSession();
        s.persist(appointment);
        return appointment;
    }
    
    
    @Override
    public List<Appointment> loadAppointmets(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<Appointment> cq = cb.createQuery(Appointment.class);
        Root<Appointment> root = cq.from(Appointment.class);
        Join<Appointment, Appointmentslot> slotJoin = root.join("appointmentSlot", JoinType.LEFT);
        cq.select(root).distinct(true);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();
            
            String slotId = params.get("slotId");
            if (slotId != null && !slotId.isEmpty()) {
                predicates.add(cb.equal(root.get("appointmentSlot").get("id"), slotId));
            }

            String status = params.get("status");
            if (status != null && !status.isEmpty()) {
                predicates.add(cb.equal(root.get("status"), status));
            }
            String patientId = params.get("patientId");
            if (patientId != null && !patientId.isEmpty()) {
                predicates.add(cb.equal(root.get("patientId").get("id"), Integer.valueOf(patientId)));
            }
            
            String slotStart = params.get("slotStart");
            if (slotStart != null && !slotStart.isEmpty()) {
                predicates.add(cb.equal(slotJoin.get("startTime"), LocalTime.parse(slotStart)));
            }
            
            String slotEnd = params.get("slotEnd");
            if (slotEnd != null && !slotEnd.isEmpty()) {
                predicates.add(cb.equal(slotJoin.get("endTime"), LocalTime.parse(slotEnd)));
            }

            String scheduleId = params.get("scheduleId");
            if (scheduleId != null && !scheduleId.isEmpty()) {
                predicates.add(cb.equal(slotJoin.get("scheduleId").get("id"), Integer.valueOf(scheduleId)));
            }

            if (!predicates.isEmpty()) {
                cq.where(cb.and(predicates.toArray(Predicate[]::new)));
            }
        }

        Query<Appointment> q = s.createQuery(cq);
        return q.getResultList();
    }

    @Override
    public Appointment updateAppointment(Appointment appointment) {
        Session s = this.factory.getObject().getCurrentSession();
        s.merge(appointment);
        return appointment;
    }

}

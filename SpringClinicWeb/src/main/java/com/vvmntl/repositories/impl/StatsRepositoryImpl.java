/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.repositories.impl;

import com.vvmntl.pojo.Appointment;
import com.vvmntl.pojo.Appointmentslot;
import com.vvmntl.pojo.Doctor;
import com.vvmntl.pojo.Medicine;
import com.vvmntl.pojo.Prescriptdetail;
import com.vvmntl.pojo.Service;
import com.vvmntl.pojo.Specialize;
import com.vvmntl.pojo.User;
import com.vvmntl.pojo.Workschedule;
import com.vvmntl.repositories.StatsRepository;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.List;
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
public class StatsRepositoryImpl implements StatsRepository {
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Object[]> getRevenueByMedicine() {
        Session s = this.factory.getObject().getCurrentSession();
        
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = b.createQuery(Object[].class);

        Root root = query.from(Prescriptdetail.class);
        Join<Prescriptdetail, Medicine> join = root.join("medicineId");

        query.select(b.array(join.get("id"), join.get("name"), b.sum(b.prod(root.get("priceNow"), root.get("quantiry")))));
        query.groupBy(join.get("id"));

        Query q = s.createQuery(query);
        return q.getResultList();
    }
    
    @Override
    public List<Object[]> getRevenueBySpecialize() {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = b.createQuery(Object[].class);

        Root root = query.from(Appointment.class);
        Join<Appointment, Service> joinService = root.join("serviceId");
        Join<Service, Specialize> joinSpecialize = joinService.join("specializeId");

        query.select(b.array(joinSpecialize.get("id"), joinSpecialize.get("name"), b.sum(joinService.get("price"))));
        query.where(b.equal(root.get("paymentForService"), true));
        query.groupBy(joinSpecialize.get("id"));

        Query q = s.createQuery(query);
        return q.getResultList();
    }
    
    @Override
    public List<Object[]> getAppointmentCountByDoctor() {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = b.createQuery(Object[].class);

        Root root = query.from(Appointment.class);

        Join<Appointment, Appointmentslot> joinSlot = root.join("appointmentSlot");
        Join<Appointmentslot, Workschedule> joinSchedule = joinSlot.join("scheduleId");
        Join<Workschedule, Doctor> joinDoctor = joinSchedule.join("doctorId");
        Join<Doctor, User> joinUser = joinDoctor.join("user"); 

        query.select(b.array(joinDoctor.get("id"), joinUser.get("firstName"), b.count(root.get("id"))));
        query.groupBy(joinDoctor.get("id"), joinUser.get("firstName"));
        query.orderBy(b.desc(b.count(root.get("id"))));

        Query q = s.createQuery(query);
        return q.getResultList();
    }
    
    @Override
    public List<Object[]> getAppointmentStatsByTime(String time, int year) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = b.createQuery(Object[].class);

        Root root = query.from(Appointment.class);

        query.select(b.array(b.function(time, Integer.class, root.get("createdDate")), b.count(root.get("id"))));
        query.where(b.equal(b.function("YEAR", Integer.class, root.get("createdDate")), year));
        query.groupBy(b.function(time, Integer.class, root.get("createdDate")));

        query.orderBy(b.asc(b.function(time, Integer.class, root.get("createdDate"))));

        Query q = s.createQuery(query);
        return q.getResultList();
    }
}

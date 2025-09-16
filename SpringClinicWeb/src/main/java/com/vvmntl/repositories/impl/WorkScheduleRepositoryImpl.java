/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.repositories.impl;

import com.vvmntl.pojo.Workschedule;
import com.vvmntl.repositories.WorkScheduleRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.LocalTime;
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
public class WorkScheduleRepositoryImpl implements WorkScheduleRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Workschedule> list(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<Workschedule> cq = cb.createQuery(Workschedule.class);
        Root<Workschedule> root = cq.from(Workschedule.class);
        cq.select(root);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();
            String doctorId = params.get("doctorId");

            if (doctorId != null && !doctorId.isEmpty()) {
                predicates.add(cb.equal(root.get("doctorId").get("id"), Integer.valueOf(doctorId)));
            }

            String dateWork = params.get("dateWork");
            if (dateWork != null && !dateWork.isEmpty()) {
                LocalDate date = LocalDate.parse(dateWork);
                predicates.add(cb.equal(root.get("dateWork"), date));
            }
            
            String startTime = params.get("startTime");
            if (startTime!=null && !startTime.isEmpty()) {
                LocalTime timeStart = LocalTime.parse(startTime);
                predicates.add(cb.greaterThanOrEqualTo(root.get("startTime"), timeStart));
            }
            
            String endTime = params.get("endTime");
            if (endTime!=null && !endTime.isEmpty()) {
                LocalTime timeEnd = LocalTime.parse(endTime);
                predicates.add(cb.lessThanOrEqualTo(root.get("endTime"), timeEnd));
            }

            if (!predicates.isEmpty()) {
                cq.where(cb.and(predicates.toArray(Predicate[]::new)));
            }
        }

        Query<Workschedule> query = s.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public Workschedule getWorksScheduleById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.find(Workschedule.class, id);
    }

    @Override
    public List<Workschedule> getListWorkScheduleByDoctorId(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Workschedule> query = b.createQuery(Workschedule.class);
        Root<Workschedule> r = query.from(Workschedule.class);
        query.select(r).where(b.equal(r.get("doctorId").get("id"), id));
        Query q = s.createQuery(query);
        return q.getResultList();
    }

    @Override
    public Workschedule add(Workschedule ws) {
         Session s = this.factory.getObject().getCurrentSession();
         s.persist(ws);
         s.flush();
         return ws;
    }

    @Override
    public boolean checkSchedule(int doctorId, LocalDate dateWork, LocalTime startTime, LocalTime endTime) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Workschedule> root = cq.from(Workschedule.class);
        cq.select(cb.count(root));
        
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(root.get("doctorId").get("id"), doctorId));
        predicates.add(cb.equal(root.get("dateWork"), dateWork));
        Predicate overlap = cb.and(
                cb.lessThan(root.get("startTime"), endTime),
                cb.greaterThan(root.get("endTime"), startTime)
        );
        predicates.add(overlap);

        cq.where(predicates.toArray(new Predicate[0]));

        Long count = s.createQuery(cq).getSingleResult();

        return count > 0;
        
    }

}

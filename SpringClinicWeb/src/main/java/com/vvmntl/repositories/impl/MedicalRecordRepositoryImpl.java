/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.repositories.impl;

import com.vvmntl.exception.ResourceNotFoundException;
import com.vvmntl.pojo.Appointment;
import com.vvmntl.pojo.Appointmentslot;
import com.vvmntl.pojo.Doctor;
import com.vvmntl.pojo.Medicalrecord;
import com.vvmntl.pojo.Patient;
import com.vvmntl.pojo.Workschedule;
import com.vvmntl.repositories.MedicalRecordRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
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
public class MedicalRecordRepositoryImpl implements MedicalRecordRepository{

    @Autowired
    private LocalSessionFactoryBean factory;
    
    @Override
    public Medicalrecord getMedicalRecordById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Medicalrecord mr = s.find(Medicalrecord.class, id);
        if (mr == null) {
            throw new ResourceNotFoundException("Không tìm thấy hồ sơ bệnh án!");
        }
        return mr;
    }

    @Override
    public Medicalrecord add(Medicalrecord medicalRecord) {
        Session s = this.factory.getObject().getCurrentSession();
        s.persist(medicalRecord);
        return medicalRecord;
    }

    @Override
    public Medicalrecord update(Medicalrecord medicalRecord) {
        Session s = this.factory.getObject().getCurrentSession();
        try {
            Medicalrecord mr = this.getMedicalRecordById(medicalRecord.getId());
            if (mr!=null)
                return s.merge(medicalRecord);
        } catch (Exception e) {
            throw new ResourceNotFoundException(("Không tìm thấy hồ sơ bệnh án!"));
        }
        return null;
    }

    @Override
    public List<Medicalrecord> getMedicalRecordByUserId(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<Medicalrecord> query = cb.createQuery(Medicalrecord.class);
        Root<Medicalrecord> r = query.from(Medicalrecord.class);
        Join<Medicalrecord, Appointment> appointmentJoin = r.join("appointmentId");
        Join<Appointment, Patient> patientJoin = appointmentJoin.join("patientId");
        query.select(r).where(cb.equal(patientJoin.get("user").get("id"), id));
        Query q = s.createQuery(query);
        return q.getResultList();
        
    }

    @Override
    public List<Medicalrecord> getMedidalRecordBYDoctorId(int doctorId) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<Medicalrecord> query = cb.createQuery(Medicalrecord.class);

        Root<Medicalrecord> mrRoot = query.from(Medicalrecord.class);

        Join<Medicalrecord, Appointment> appointmentJoin = mrRoot.join("appointmentId");
        Join<Appointment, Appointmentslot> slotJoin = appointmentJoin.join("appointmentSlot");
        Join<Appointmentslot, Workschedule> wsJoin = slotJoin.join("scheduleId");
        Join<Workschedule, Doctor> doctorJoin = wsJoin.join("doctorId");

        query.select(mrRoot).where(cb.equal(doctorJoin.get("id"), doctorId));

        return s.createQuery(query).getResultList();
    }

    @Override
    public List<Medicalrecord> loadMedicalRecord(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Medicalrecord> q = b.createQuery(Medicalrecord.class);
        Root<Medicalrecord> r = q.from(Medicalrecord.class);

        List<Predicate> predicates = new ArrayList<>();

        if (params != null) {
            String diagnosis = params.get("diagnosis");
            if (diagnosis != null && !diagnosis.isEmpty()) {
                predicates.add(b.like(b.lower(r.get("diagnosis")), "%" + diagnosis.toLowerCase() + "%"));
            }

            String symptoms = params.get("symptoms");
            if (symptoms != null && !symptoms.isEmpty()) {
                predicates.add(b.like(b.lower(r.get("symptoms")), "%" + symptoms.toLowerCase() + "%"));
            }

            String testResults = params.get("testResults");
            if (testResults != null && !testResults.isEmpty()) {
                predicates.add(b.like(b.lower(r.get("testResults")), "%" + testResults.toLowerCase() + "%"));
            }

            String fromDate = params.get("fromDate");
            if (fromDate != null && !fromDate.isEmpty()) {
                try {
                    Date fDate = java.sql.Date.valueOf(fromDate); // yyyy-MM-dd
                    predicates.add(b.greaterThanOrEqualTo(r.get("createdDate"), fDate));
                } catch (Exception e) {
                    System.err.println("Lỗi parse fromDate: " + e.getMessage());
                }
            }

            String toDate = params.get("toDate");
            if (toDate != null && !toDate.isEmpty()) {
                try {
                    Date tDate = java.sql.Date.valueOf(toDate);
                    predicates.add(b.lessThanOrEqualTo(r.get("createdDate"), tDate));
                } catch (Exception e) {
                    System.err.println("Lỗi parse toDate: " + e.getMessage());
                }
            }

            String appointmentId = params.get("appointmentId");
            if (appointmentId != null && !appointmentId.isEmpty()) {
                predicates.add(b.equal(r.get("appointmentId").get("id"), Integer.valueOf(appointmentId)));
            }

            if (!predicates.isEmpty()) {
                q.where(b.and(predicates.toArray(Predicate[]::new)));
            }
        }

        q.orderBy(b.desc(r.get("createdDate")));
        Query query = s.createQuery(q);
        return query.getResultList();
    }


}

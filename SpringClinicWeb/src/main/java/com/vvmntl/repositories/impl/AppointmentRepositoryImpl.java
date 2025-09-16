/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.repositories.impl;

import com.vvmntl.exception.ResourceNotFoundException;
import com.vvmntl.pojo.Appointment;
import com.vvmntl.pojo.Appointmentslot;
import com.vvmntl.pojo.Patient;
import com.vvmntl.pojo.Service;
import com.vvmntl.repositories.AppointmentRepository;
import com.vvmntl.repositories.AppointmentSlotRepository;
import com.vvmntl.repositories.PatientRepository;
import com.vvmntl.repositories.ServiceRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.Date;
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
public class AppointmentRepositoryImpl implements AppointmentRepository{
    
    @Autowired
    private LocalSessionFactoryBean factory;
    
    @Autowired
    private AppointmentSlotRepository slotRepo;
    @Autowired
    private PatientRepository patientRepo;
    @Autowired
    private ServiceRepository serviceRepo;

    @Override
    public List<Appointment> list() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createNamedQuery("Doctor.findAll", Appointment.class);
        return q.getResultList();
    }

    @Override
    public Appointment getAppointmentById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createNamedQuery( "Appointment.findById", Appointment.class);
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
    public Appointment bookAppointment(int patientId, int serviceId, int slotId) {
        Session s = this.factory.getObject().getCurrentSession();

        Appointmentslot slot = this.slotRepo.getSlotById(slotId);
        if (slot == null || Boolean.TRUE.equals(slot.getIsBooked())) {
            throw new IllegalStateException("Slot không tồn tại hoặc đã được đặt!");
        }

        Patient patient = this.patientRepo.getPatientById(patientId);
        Service service = this.serviceRepo.getServiceById(serviceId);
        if (patient == null || service == null) {
            throw new IllegalArgumentException("Bệnh nhân hoặc dịch vụ không hợp lệ!");
        }

        Appointment appointment = new Appointment();
        appointment.setPatientId(patient);
        appointment.setServiceId(service);
        appointment.setAppointmentSlot(slot);
        appointment.setCreatedDate(LocalDate.now());
        appointment.setStatus("PENDING");
        appointment.setPaymentForService(false);

        s.persist(appointment);

        slot.setIsBooked(true);
        s.merge(slot);

        return appointment;
    }
   
}

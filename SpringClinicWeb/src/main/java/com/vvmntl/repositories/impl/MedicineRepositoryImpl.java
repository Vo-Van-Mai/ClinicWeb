/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.repositories.impl;

import com.vvmntl.pojo.CategoryMedicine;
import com.vvmntl.pojo.Medicine;
import com.vvmntl.repositories.MedicineRepository;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
public class MedicineRepositoryImpl implements MedicineRepository {
    
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Medicine> getMedicines(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Medicine> query = b.createQuery(Medicine.class);
        Root root = query.from(Medicine.class);
        query.select(root);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();

            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                predicates.add(b.like(root.get("name"), String.format("%%%s%%", kw)));
            }

            String fromPrice = params.get("fromPrice");
            if (fromPrice != null && !fromPrice.isEmpty()) {
                predicates.add(b.greaterThanOrEqualTo(root.get("price"), fromPrice));
            }

            String toPrice = params.get("toPrice");
            if (toPrice != null && !toPrice.isEmpty()) {
                predicates.add(b.lessThanOrEqualTo(root.get("price"), toPrice));
            }

            String cateId = params.get("cateId");
            if (cateId != null && !cateId.isEmpty()) {
                Join<Medicine, CategoryMedicine> joinCM = root.join("categoryMedicineSet");
                predicates.add(b.equal(joinCM.get("categoryId").as(Integer.class), cateId));
            }

            query.where(predicates);

            String orderBy = params.get("orderBy");
            if (orderBy != null && !orderBy.isEmpty()) {
                query.orderBy(b.desc(root.get(orderBy)));
            }
        }

        Query q = s.createQuery(query);

        return q.getResultList();
    }

    @Override
    public void addOrUpdate(Medicine m) {
        Session s = this.factory.getObject().getCurrentSession();
        if (m.getId() != null) {
            s.merge(m);
        } else {
            s.persist(m);
        }
    }

    @Override
    public void deleteMedicine(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Medicine m = this.getMedicineById(id);
        s.remove(m);
    }

    @Override
    public Medicine getMedicineById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.find(Medicine.class, id);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.repositories.impl;

import com.vvmntl.exception.ResourceNotFoundException;
import com.vvmntl.pojo.User;
import com.vvmntl.repositories.UserRepository;
import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author BRAVO15
 */
@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User getUserByUsername(String username) {
        Session s = this.factory.getObject().getCurrentSession();
        Query query = s.createNamedQuery("User.findByUsername", User.class);
        query.setParameter("username", username);
        try {
            return (User) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public User getUserByEmail(String email) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createNamedQuery("User.findByEmail", User.class);
        q.setParameter("email", email);
        
        try {
            return (User) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
     

    @Override
    public boolean deleteUser(int id) {
        return false;
    }

    @Override
    public User getUserById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.find(User.class, id);
    }
    

    @Override
    public User updateUser(User user) {
        Session s = this.factory.getObject().getCurrentSession();
        User u = this.getUserById(user.getId());
        if (u != null){
            return (User) s.merge(user);
        } else{
            throw new ResourceNotFoundException("Người dùng không tồn tại");
        }
    }

    @Override
    public User addUser(User u) {
        Session s = this.factory.getObject().getCurrentSession();
        s.persist(u);
        return u;
    }
    
    @Override
    public boolean authenticate(String username, String password){
        User u = this.getUserByUsername(username);
        return this.passwordEncoder.matches(password,u.getPassword());
    }
   
}

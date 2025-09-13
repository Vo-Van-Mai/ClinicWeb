/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.services.impl;

import com.vvmntl.exception.ResourceNotFoundException;
import com.vvmntl.pojo.User;
import com.vvmntl.repositories.UserRepository;
import com.vvmntl.services.UserService;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author BRAVO15
 */
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private  BCryptPasswordEncoder passawordEndcoder;
    
    
    @Override
    public User getUserByUsername(String username) {
        return this.userRepo.getUserByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = this.getUserByUsername(username);
        if (u == null) {
            throw new UsernameNotFoundException("Username isvalid");
        }
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + u.getRole().name()));
        return new org.springframework.security.core.userdetails.User(u.getUsername(), u.getPassword(), authorities);
    }

    @Override
    public User getUserByEmail(String email) {
       return this.userRepo.getUserByEmail(email);
    }

    @Override
    public User getUserById(int id) {
        return this.userRepo.getUserById(id);
    }

    @Override
    public User addUser(User u) {
        User user = this.getUserByUsername(u.getUsername());
        if(user!=null){
            throw new RuntimeException("User đã tồn tại!");
        }
        else{
            System.out.println("com.vvmntl.services.impl.UserServiceImpl.addUser()");
            u.setAvatar("https://res.cloudinary.com/disqxvj3s/image/upload/v1754715964/fsgua3oudxllx9iry1g8.jpg");
            u.setPassword(passawordEndcoder.encode(u.getPassword()));
            u.setCreatedDate(LocalDateTime.now());
            User newUser = this.userRepo.addUser(u);
            return newUser;
        }
    }

    @Override
    public User updateUser(User user) {
        User u = this.getUserById(user.getId());
        if (u != null){
            return this.userRepo.updateUser(u);
        }else{
            throw new ResourceNotFoundException("Không tim thấy người dùng!");
        }
        
    }

    @Override
    public boolean deleteUser(int id) {
        User u = this.getUserById(id);
        if (u != null){
            return this.userRepo.deleteUser(id);
        }else{
            throw new ResourceNotFoundException("Không tim thấy người dùng!");
        }
    }
    
}

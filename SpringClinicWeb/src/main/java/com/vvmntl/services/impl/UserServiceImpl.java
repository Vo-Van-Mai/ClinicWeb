/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.vvmntl.exception.ResourceNotFoundException;
import com.vvmntl.pojo.Gender;
import com.vvmntl.pojo.Role;
import com.vvmntl.pojo.User;
import com.vvmntl.repositories.UserRepository;
import com.vvmntl.services.UserService;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author BRAVO15
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private BCryptPasswordEncoder passawordEncoder;

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
    public User addUser(Map<String, String> params, MultipartFile avatar) {
        User u = new User();
        u.setUsername(params.get("username"));
        u.setFirstName(params.get("firstName"));
        u.setLastName(params.get("lastName"));
        u.setEmail(params.get("email"));
        u.setPhone(params.get("phone"));
        
        if (params.get("dateOfBirth") != null && !params.get("dateOfBirth").isBlank()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                u.setDateOfBirth(sdf.parse(params.get("dateOfBirth")));
            } catch (ParseException e) {
                throw new RuntimeException("Sai định dạng ngày sinh! Phải là yyyy-MM-dd");
            }
        }
        if (params.get("gender") != null) {
            u.setGender(Gender.valueOf(params.get("gender").toUpperCase()));
        }

        u.setIsActive(true);
        u.setIsAdmin(false);

        if (params.get("role") != null) {
            u.setRole(Role.valueOf(params.get("role").toUpperCase()));
        }

        if (!avatar.isEmpty()) {
            try {
                Map res = this.cloudinary.uploader().upload(avatar.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                u.setAvatar((String) res.get("secure_url"));
            } catch (IOException ex) {
                System.err.println(ex.getMessage());

            }
        }
        u.setPassword(passawordEncoder.encode(params.get("password")));
        u.setCreatedDate(LocalDateTime.now());
        User newUser = this.userRepo.addUser(u);
        return newUser;
    }
    
    

    @Override
    public User updateUser(User user) {
        User u = this.getUserById(user.getId());
        if (u == null) {
            throw new ResourceNotFoundException("Không tìm thấy người dùng!");
        }
        u.setUsername(user.getUsername());
        u.setFirstName(user.getFirstName());
        u.setLastName(user.getLastName());
        u.setEmail(user.getEmail());
        u.setPhone(user.getPhone());
        u.setDateOfBirth(user.getDateOfBirth());
        u.setGender(user.getGender());
        u.setIsActive(user.getIsActive());
        u.setIsAdmin(user.getIsAdmin());
        u.setRole(user.getRole());

        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            u.setPassword(passawordEncoder.encode(user.getPassword()));
        }

        if (user.getFile() != null && !user.getFile().isEmpty()) {
            try {
                Map res = this.cloudinary.uploader().upload(u.getAvatar().getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                u.setAvatar((String) res.get("secure_url"));
            } catch (IOException ex) {
                u.setAvatar("https://res.cloudinary.com/disqxvj3s/image/upload/v1754715964/fsgua3oudxllx9iry1g8.jpg");
            }
        }

        return this.userRepo.updateUser(u);
    }

    @Override
    public boolean deleteUser(int id) {
        User u = this.getUserById(id);
        if (u != null) {
            return this.userRepo.deleteUser(id);
        } else {
            throw new ResourceNotFoundException("Không tim thấy người dùng!");
        }
    }

}

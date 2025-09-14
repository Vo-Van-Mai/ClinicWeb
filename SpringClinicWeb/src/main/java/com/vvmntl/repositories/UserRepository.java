/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.vvmntl.repositories;

import com.vvmntl.pojo.User;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 *
 * @author BRAVO15
 */
public interface UserRepository{
    User getUserById(int id);
    User getUserByUsername(String username);
    User getUserByEmail(String email);
    User addUser(User u);
    User updateUser(User user);
    boolean deleteUser(int id);
    boolean authenticate(String username, String password);
}

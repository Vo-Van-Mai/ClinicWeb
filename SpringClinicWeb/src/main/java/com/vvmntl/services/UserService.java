/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.vvmntl.services;

import com.vvmntl.pojo.User;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author BRAVO15
 */
public interface UserService extends UserDetailsService{
    User getUserById(int id);
    User getUserByUsername(String username);
    User getUserByEmail(String email);
    User addUser(Map <String, String> params, MultipartFile avatar);
    User updateUser(User user);
    boolean deleteUser(int id);
    boolean authenticate(String username, String password);
}

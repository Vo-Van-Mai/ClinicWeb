package com.vvmntl.controllers;

import com.vvmntl.exception.ResourceNotFoundException;
import com.vvmntl.pojo.Specialize;
import com.vvmntl.services.SpecializeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
/**
 *
 * @author BRAVO15
 */
@RestController
@CrossOrigin
@RequestMapping("/api")
public class ApiSpecializeController {

    @Autowired
    private SpecializeService speciService;

    @DeleteMapping("/specializes/{id}")
    public ResponseEntity<String> destroy(@PathVariable(value="id") int id) {
        try {
            this.speciService.delSpecialize(id);
            return ResponseEntity.ok("Xóa thành công!");
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi server: " + ex.getMessage());
        }
    }
    
    @GetMapping("/specializes")
    public ResponseEntity<List<Specialize>> list(){
        return new ResponseEntity<>(this.speciService.getAllSpecialize(), HttpStatus.OK);
    }
}

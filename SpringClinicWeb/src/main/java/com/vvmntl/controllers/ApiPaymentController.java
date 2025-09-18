/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vvmntl.controllers;

import com.vvmntl.services.PaymentService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author locnguyen
 */
@RestController
@RequestMapping("/api/payment")
@CrossOrigin
public class ApiPaymentController {
    @Autowired
    private PaymentService paymentService;

    @GetMapping("/notify")
    public ResponseEntity<?> handleVnpayIPN(@RequestParam Map<String, String> params) {
        try {
            paymentService.handlePaymentCallback(params);
            return ResponseEntity.ok("{\"RspCode\":\"00\",\"Message\":\"Confirm Success\"}");
        } catch (Exception e) {
            return ResponseEntity.ok("{\"RspCode\":\"97\",\"Message\":\"Fail\"}");
        }
    }
}
